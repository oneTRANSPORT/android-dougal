package com.interdigital.android.dougal.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Group;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.Subscription;

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
            // Creates and updates need to have several CSE-generated attributes removed
            // or a 4000 Bad Request will be the result.
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
                } else if (resource instanceof Group) {
                    Group group = (Group) resource;
                    group.setMemberTypeValidated(null);
                    group.setCurrentNumberOfMembers(null);
                }
            } else if (originalRequest.method().equalsIgnoreCase("put")) {
                resource.setResourceName(null);
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
                    container.setCreator(null);
                    container.setStateTag(null);
                    container.setCurrentNumberOfInstances(null);
                    container.setCurrentByteSize(null);
                } else if (resource instanceof Group) {
                    Group group = (Group) resource;
                    group.setCreator(null);
                    group.setMemberType(null);
                    group.setMemberTypeValidated(null);
                    group.setCurrentNumberOfMembers(null);
                    group.setConsistencyStrategy(null);
                } else if (resource instanceof Subscription) {
                    Subscription subscription = (Subscription) resource;
                    subscription.setPreSubscriptionNotify(null);
                    subscription.setCreator(null);
                    subscription.setSubscriberUri(null);
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
            // I believe this has now been fixed.
//            content = maybeConvertEmptyLists(content);
            // In the event that a primitive content field is returned in a non-blocking request
            // response, we have to add the missing "m2m:" prefix to the type.
            content = maybeAddM2MPrefix(content);
            // In the event of a URI list being returned, change the array parameter to an object
            // as follows:
            // {"m2m:uril":["..."]} -> {"m2m:uril":{"list":["..."}}
            content = maybeWrapArray(content);
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

    // TODO    Remove this in a future release.
//    private String maybeConvertEmptyLists(String content) {
//        String newContent = content.replace("[\"\"]", "[]");
//        return newContent;
//    }

    private String maybeAddM2MPrefix(String content) {
        if (content.startsWith("{\"m2m:req\":")) {
            // This is a non-blocking request response.
            // Rewrite the pc content.
            for (String type : new String[]{"acp", "ae", "cin", "cnt", "discovery", "grp"}) {
                content = content.replace(",\"pc\":\"{\\\"" + type + "\\\":",
                        ",\"pc\":\"{\\\"m2m:" + type + "\\\":");
            }
        }
        return content;
    }

    private String maybeWrapArray(String content) {
        if (content.contains("uril")) {
            Log.i("RWCI", "JSON wrap: " + content.replaceFirst("\\[", "{\"list\":[").trim() + "}");
            return content.replaceFirst("\\[", "{\"list\":[").trim() + "}";
        }
        return content;
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
