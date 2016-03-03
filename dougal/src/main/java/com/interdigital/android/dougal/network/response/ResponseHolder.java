package com.interdigital.android.dougal.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Container;

import okhttp3.Headers;

public class ResponseHolder {

    private int statusCode;
    private Headers headers;
    private String body;

    //    @SerializedName("m2m:cb")
//    private CommonServicesEntity commonServicesEntity;
    @Expose
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;
    @Expose
    @SerializedName("m2m:cnt")
    private Container container;
    @Expose
    @SerializedName("m2m:cin")
    private ContentInstance contentInstance;
//    @SerializedName("m2m:discovery")
//    private Discovery discovery;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    //    public CommonServicesEntity getCommonServicesEntity() {
//        return commonServicesEntity;
//    }

//    public void setCommonServicesEntity(CommonServicesEntity commonServicesEntity) {
//        this.commonServicesEntity = commonServicesEntity;
//    }

    public Resource getResource() {
        if (applicationEntity != null) {
            return applicationEntity;
        }
        if (container != null) {
            return container;
        }
        if (contentInstance != null) {
            return contentInstance;
        }
        return null;
    }

    public ApplicationEntity getApplicationEntity() {
        return applicationEntity;
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public ContentInstance getContentInstance() {
        return contentInstance;
    }

    public void setContentInstance(ContentInstance contentInstance) {
        this.contentInstance = contentInstance;
    }

//    public Discovery getDiscovery() {
//        return discovery;
//    }
//
//    public void setDiscovery(Discovery discovery) {
//        this.discovery = discovery;
//    }
}
