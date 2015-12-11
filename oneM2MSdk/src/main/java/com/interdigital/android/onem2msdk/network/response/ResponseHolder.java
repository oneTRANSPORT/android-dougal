package com.interdigital.android.onem2msdk.network.response;


import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;
import com.interdigital.android.onem2msdk.resource.CommonServicesEntity;
import com.interdigital.android.onem2msdk.resource.ContentInstance;
import com.interdigital.android.onem2msdk.resource.DataContainer;
import com.interdigital.android.onem2msdk.resource.Discovery;

import java.util.List;
import java.util.Map;

public class ResponseHolder {

    private int statusCode;
    private Map<String, List<String>> propertyValues; // HTTP response headers.
    @SerializedName("m2m:cb")
    private CommonServicesEntity commonServicesEntity;
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;
    @SerializedName("m2m:cnt")
    private DataContainer dataContainer;
    @SerializedName("m2m:cin")
    private ContentInstance contentInstance;
    @SerializedName("m2m:discovery")
    private Discovery discovery;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<String>> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(Map<String, List<String>> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public CommonServicesEntity getCommonServicesEntity() {
        return commonServicesEntity;
    }

    public void setCommonServicesEntity(CommonServicesEntity commonServicesEntity) {
        this.commonServicesEntity = commonServicesEntity;
    }

    public ApplicationEntity getApplicationEntity() {
        return applicationEntity;
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public DataContainer getDataContainer() {
        return dataContainer;
    }

    public void setDataContainer(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    public ContentInstance getContentInstance() {
        return contentInstance;
    }

    public void setContentInstance(ContentInstance contentInstance) {
        this.contentInstance = contentInstance;
    }

    public Discovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }
}
