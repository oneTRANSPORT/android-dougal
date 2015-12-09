package com.interdigital.android.onem2msdk;

import android.content.Context;

import com.google.gson.Gson;
import com.interdigital.android.onem2msdk.network.BaseRequest;

import java.util.Map;

public class SDK {

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

    public synchronized long getUniqueRequestId() {
        return requestId++;
    }

    public Response getResource(Context context, RI ri, Map<String, String> propertyValues) {
        propertyValues.put("X-M2M-RI", String.valueOf(SDK.getInstance().getUniqueRequestId()));
        BaseRequest baseRequest = new BaseRequest(context, 0, "https:" + ri.getRiString(),
                BaseRequest.METHOD_GET, propertyValues);
        int statusCode = baseRequest.connect();
        String text = baseRequest.getResponseText();
        Gson gson = new Gson();
        Response response = gson.fromJson(text, Response.class);
        if (response == null) {
            return null;
        }
        response.setStatusCode(statusCode);
        response.setPropertyValues(baseRequest.getHeaderMap());
        return response;
    }

}
