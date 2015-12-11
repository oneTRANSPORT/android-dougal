package com.interdigital.android.onem2msdk.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.SDK;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestHolder {

    private static final String PROPERTY_REQUEST_ID = "X-M2M-RI";
    private static final String PROPERTY_ORIGIN = "X-M2M-Origin";
    private static final String PROPERTY_NAME = "X-M2M-NM";
    private static final String PROPERTY_CONTENT_TYPE = "Content-Type";

    @Expose
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;

    private HashMap<String, List<String>> propertyValues =
            new HashMap<>();

    public RequestHolder() {
        putRequestIdProperty();
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public void putOriginProperty(String origin) {
        putPropertyValue(PROPERTY_ORIGIN, origin);
    }

    public void putNameProperty(String name) {
        putPropertyValue(PROPERTY_NAME, name);
    }

    public void putContentTypeProperty(String contentType) {
        putPropertyValue(PROPERTY_CONTENT_TYPE, contentType);
    }

    public HashMap<String, List<String>> getPropertyValues() {
        return propertyValues;
    }

    // TODO Should we really return a list here?  Also consider above.
    public List<String> getRequestIdProperty() {
        return propertyValues.get(PROPERTY_REQUEST_ID);
    }

    protected void putRequestIdProperty() {
        putPropertyValue(PROPERTY_REQUEST_ID, SDK.getInstance().getUniqueRequestId());
    }

    private void putPropertyValue(String name, String value) {
        if (propertyValues.get(name) != null) {
            propertyValues.get(name).add(value);
        } else {
            List<String> values = new ArrayList<>();
            values.add(value);
            propertyValues.put(name, values);
        }
    }

}
