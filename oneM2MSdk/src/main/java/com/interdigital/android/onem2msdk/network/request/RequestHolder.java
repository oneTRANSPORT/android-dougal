package com.interdigital.android.onem2msdk.network.request;

import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;

public class RequestHolder {

    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }
}
