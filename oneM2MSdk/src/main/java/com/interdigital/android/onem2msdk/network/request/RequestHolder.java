package com.interdigital.android.onem2msdk.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.Types;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;
import com.interdigital.android.onem2msdk.resource.ContentInstance;
import com.interdigital.android.onem2msdk.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestHolder {

    private static final String PROPERTY_REQUEST_ID = "X-M2M-RI";
    private static final String PROPERTY_ORIGIN = "X-M2M-Origin";
    private static final String PROPERTY_NAME = "X-M2M-NM";
    private static final String PROPERTY_CONTENT_TYPE = "Content-Type";

    @Expose
    @SerializedName("ae")
    private ApplicationEntity applicationEntity;
    @Expose
    @SerializedName("cin")
    private ContentInstance contentInstance;
//    @Expose
//    @SerializedName("m2m:cnt")
//    private DataContainer dataContainer;

    private HashMap<String, List<String>> propertyValues =
            new HashMap<>();

    public RequestHolder() {
        putRequestIdProperty();
    }

    public void setResource(Resource resource) {
        // TODO Add all the rest of the objects.
        switch (resource.getResourceType()) {
            case Types.RESOURCE_TYPE_APPLICATION_ENTITY:
                setApplicationEntity((ApplicationEntity) resource);
                break;
        }
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public void setContentInstance(ContentInstance contentInstance) {
        this.contentInstance = contentInstance;
    }

//    public void setDataContainer(DataContainer dataContainer) {
//        this.dataContainer = dataContainer;
//    }

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
//        putPropertyValue(PROPERTY_REQUEST_ID, SDK.getInstance().getUniqueRequestId());
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
