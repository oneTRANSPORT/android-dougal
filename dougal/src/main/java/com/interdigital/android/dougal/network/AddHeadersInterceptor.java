package com.interdigital.android.dougal.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddHeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "OneM2M-Android")
                .build();
        Response response = chain.proceed(newRequest);
        return response;
    }

}
