package com.interdigital.android.onem2msdk;

import android.content.Context;

import com.interdigital.android.onem2msdk.network.request.BaseRequest;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class BaseResource {

    private Context context;
    private int responseCode;
    private String responseText;
    private Map<String, List<String>> responsePropertyValues;

    private String resourceId;
    private String resourceName;
    private String resourceType;
    private String parentId;
    private String creationTime;
    private String lastModifiedTime;
    private String expiryTime;

    public BaseResource(Context context) {
        this.context = context;
    }

    public void getFromServer(RI ri, Map<String, String> propertyValues) throws JSONException {
        BaseRequest baseRequest = new BaseRequest(context, 0, "https:" + ri.getRiString(),
                BaseRequest.METHOD_GET, propertyValues, null);
        responseCode = baseRequest.connect();
        responseText = baseRequest.getResponseText();
        responsePropertyValues = baseRequest.getHeaderMap();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public Map<String, List<String>> getResponsePropertyValues() {
        return responsePropertyValues;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }


}
