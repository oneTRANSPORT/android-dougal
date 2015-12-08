package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.interdigital.android.onem2msdk.RI;
import com.interdigital.android.onem2msdk.network.BaseRequest;

import java.util.List;
import java.util.Map;

public class BaseResource {

    private Context context;
    private RI ri;
    private Map<String, String> requestPropertyValues;
    private int responseCode;
    private String responseText;
    private Map<String, List<String>> responsePropertyValues;

    public BaseResource(Context context, RI ri, Map<String, String> propertyValues) {
        BaseRequest baseRequest = new BaseRequest(context, 0, "https:" + ri.getRiString(),
                BaseRequest.METHOD_GET, propertyValues);
        responseCode = baseRequest.connect();
        responseText = baseRequest.getResponseText();
        responsePropertyValues = baseRequest.getHeaderMap();
    }

    // TODO Merge baseRequest routines into here?  Method inflateFromServer()?
}
