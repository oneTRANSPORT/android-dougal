package com.interdigital.android.dougal.network;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

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
        Request originalRequest = chain.request();
        RequestBody originalBody = originalRequest.body();
        Response originalResponse;
        if (originalBody != null) {
            Buffer buffer = new Buffer();
            originalBody.writeTo(buffer);
            String requestContent = buffer.readUtf8();
            RequestBody newBody = RequestBody.create(originalBody.contentType(),
                    requestContent.getBytes());
            Request newRequest = originalRequest
                    .newBuilder()
                    .method(originalRequest.method(), newBody)
                    .build();
            originalResponse = chain.proceed(newRequest);
        } else {
            originalResponse = chain.proceed(originalRequest);
        }

        ResponseBody responseBody = originalResponse.body();
        String content = responseBody.string();

        // Sometimes an identifier is returned as plain text, with no JSON wrapper,
        // eg. from a non-blocking request.  We need to turn this into a resource.
        content = maybeWrapPlainText(content);

        ResponseBody newResponseBody =
                ResponseBody.create(MediaType.parse("application/json"), content);
        return originalResponse.newBuilder().body(newResponseBody).build();
    }

    @Nullable
    private String maybeWrapPlainText(String content) {
        if (!TextUtils.isEmpty(content) && !isJson(content) && !isHtml(content)) {
            // Assume a plain text identifier has been returned.
            content = "{\"rewrite:id\":\"" + content + "\"}";
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
