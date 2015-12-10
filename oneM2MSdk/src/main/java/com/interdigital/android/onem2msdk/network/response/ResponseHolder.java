package com.interdigital.android.onem2msdk.network.response;


import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;
import com.interdigital.android.onem2msdk.resource.CommonServicesEntity;

import java.util.List;
import java.util.Map;

public class ResponseHolder {

    private int statusCode;
    private Map<String, List<String>> propertyValues; // HTTP response headers.
    @SerializedName("m2m:cb")
    private CommonServicesEntity commonServicesEntity;
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;

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
}
