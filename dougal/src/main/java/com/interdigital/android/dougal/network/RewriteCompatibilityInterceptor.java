package com.interdigital.android.dougal.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Resource;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class RewriteCompatibilityInterceptor implements Interceptor {

    // Rewriting requests and responses where the spec uses edge cases that
    // would otherwise be hard to deal with using Retrofit and Gson.

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = processRequest(chain);
        String content = processResponse(originalResponse);
        ResponseBody newResponseBody = ResponseBody.create(
                MediaType.parse("application/json"), content);
        return originalResponse.newBuilder()
                .body(newResponseBody)
                .header("Content-type", "application/json; charset=utf-8")
                .header("Content-length", String.valueOf(content.length()))
                .build();
    }

    private Response processRequest(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        RequestBody originalBody = originalRequest.body();
        Response originalResponse;
        if (originalBody != null) {
            String requestContent = readContent(originalBody);
            RequestHolder requestHolder = Resource.gson.fromJson(requestContent, RequestHolder.class);
            Resource resource = requestHolder.getResource();
            // We remove the ri attribute from JSON in POST requests.
            // CSE doesn't like it.
            // Also remove ty as this is sent in the request line.
            if (originalRequest.method().equalsIgnoreCase("post")) {
                resource.setResourceId(null);
                resource.setResourceType(null);
                resource.setCreationTime(null);
                resource.setLastModifiedTime(null);
                resource.setParentId(null);
                if (resource instanceof ApplicationEntity) {
                    ((ApplicationEntity) resource).setNodeLink(null);
                } else if (resource instanceof Container) {
                    Container container = (Container) resource;
                    container.setStateTag(null);
                    container.setCurrentNumberOfInstances(null);
                    container.setCurrentByteSize(null);
                } else if (resource instanceof ContentInstance) {
                    ContentInstance contentInstance = (ContentInstance) resource;
                    contentInstance.setStateTag(null);
                    contentInstance.setContentSize(null);
                }
            } else if (originalRequest.method().equalsIgnoreCase("put")) {
                // These fields cause a Bad Request.
                resource.setResourceId(null);
                resource.setResourceType(null);
                resource.setCreationTime(null);
                resource.setLastModifiedTime(null);
                resource.setParentId(null);
                if (resource instanceof ApplicationEntity) {
                    ((ApplicationEntity) resource).setApplicationId(null);
                    ((ApplicationEntity) resource).setNodeLink(null);
                } else if (resource instanceof Container) {
                    Container container = (Container) resource;
                    container.setStateTag(null);
                    container.setCurrentNumberOfInstances(null);
                    container.setCurrentByteSize(null);
                }
            }

            requestContent = Resource.gson.toJson(requestHolder);
            originalResponse = sendNewRequest(chain, originalRequest, originalBody, requestContent);
        } else {
            originalResponse = chain.proceed(originalRequest);
        }
        return originalResponse;
    }

    private String processResponse(Response originalResponse) throws IOException {
        ResponseBody responseBody = originalResponse.body();
        String content = responseBody.string();
        if (!TextUtils.isEmpty(content)) {
            // Sometimes an identifier is returned as plain text, with no JSON wrapper,
            // eg. from a non-blocking request.  We need to turn this into a resource.
            content = maybeWrapPlainText(content);
            // Rewrite [""] to [].  The CSE wrongly sends back empty lists with a single
            // string element.
            content = maybeConvertEmptyLists(content);
        }
        return content;
    }

    @NonNull
    private String readContent(RequestBody originalBody) throws IOException {
        Buffer buffer = new Buffer();
        originalBody.writeTo(buffer);
        return buffer.readUtf8();
    }

    private Response sendNewRequest(Chain chain, Request originalRequest, RequestBody originalBody,
                                    String requestContent) throws IOException {
        Response originalResponse;
        RequestBody newBody = RequestBody.create(originalBody.contentType(),
                requestContent.getBytes());
        Request newRequest = originalRequest
                .newBuilder()
                .method(originalRequest.method(), newBody)
                .build();
        originalResponse = chain.proceed(newRequest);
        return originalResponse;
    }

    @Nullable
    private String maybeWrapPlainText(String content) {
        if (!isJson(content) && !isHtml(content)) {
            // Assume a plain text identifier has been returned.
            content = "{\"rewrite:id\":{\"ri\":\"" + content + "\"}}";
        }
        return content;
    }

    private String maybeConvertEmptyLists(String content) {
        String newContent = content.replace("[\"\"]", "[]");
        return newContent;
    }

    private boolean isJson(String s) {
        if (s != null && (s.startsWith("{") || s.startsWith("["))) {
            return true;
        }
        return false;
    }

    private boolean isHtml(String s) {
        if (s != null && s.startsWith("<")) {
            return true;
        }
        return false;
    }
}
