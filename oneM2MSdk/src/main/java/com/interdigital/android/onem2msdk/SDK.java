package com.interdigital.android.onem2msdk;

import android.content.Context;

import com.google.gson.Gson;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.BaseRequest;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

import java.util.List;
import java.util.Map;

public class SDK {

    private static final String PREFIX_HTTPS = "https:";
    private static final String PREFIX_HTTP = "http:";
    private static SDK instance;
    private static long requestId = 0L;

    private SDK() {

    }

    public static synchronized SDK getInstance() {
        if (instance == null) {
            instance = new SDK();
        }
        return instance;
    }

    public synchronized String getUniqueRequestId() {
        return String.valueOf(requestId++);
    }

    public ResponseHolder getResource(Context context, RI ri, boolean useHttps,
                                      Map<String, List<String>> propertyValues) {
        String url = createUrl(ri, useHttps);
        BaseRequest baseRequest = new BaseRequest(context, 0, url, BaseRequest.METHOD_GET,
                propertyValues, null);
        int statusCode = baseRequest.connect();
        String text = baseRequest.getResponseText();
        Gson gson = new Gson();
        ResponseHolder responseHolder = gson.fromJson(text, ResponseHolder.class);
        if (responseHolder == null) {
            return null;
        }
        responseHolder.setStatusCode(statusCode);
        responseHolder.setPropertyValues(baseRequest.getHeaderMap());
        return responseHolder;
    }

    public ResponseHolder postResource(
            Context context, RI ri, boolean useHttps, Map<String, List<String>> propertyValues, String body) {
        String url = createUrl(ri, useHttps);
        BaseRequest baseRequest = new BaseRequest(context, 0, url, BaseRequest.METHOD_POST,
                propertyValues, body);
        int statusCode = baseRequest.connect();
        String text = baseRequest.getResponseText();
        Gson gson = new Gson();
        ResponseHolder responseHolder = gson.fromJson(text, ResponseHolder.class);
        if (responseHolder == null) {
            return null;
        }
        responseHolder.setStatusCode(statusCode);
        responseHolder.setPropertyValues(baseRequest.getHeaderMap());
        return responseHolder;
    }

    public ResponseHolder deleteResource(
            Context context, RI ri, boolean useHttps, Map<String, List<String>> propertyValues) {
        String url = createUrl(ri, useHttps);
        BaseRequest baseRequest = new BaseRequest(context, 0, url, BaseRequest.METHOD_DELETE,
                propertyValues, null);
        int statusCode = baseRequest.connect();
        ResponseHolder responseHolder = new ResponseHolder();
        responseHolder.setStatusCode(statusCode);
        responseHolder.setPropertyValues(baseRequest.getHeaderMap());
        return responseHolder;
    }

    private String createUrl(RI ri, boolean useHttps) {
        if (useHttps) {
            return PREFIX_HTTPS + ri.getRiString();
        }
        return PREFIX_HTTP + ri.getRiString();
    }
}
