package com.interdigital.android.onem2msdk.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;
import com.interdigital.android.onem2msdk.resource.ContentInstance;

import java.util.List;
import java.util.Map;

import okhttp3.Headers;

public class ResponseHolder {

    private int statusCode;
    private Map<String, List<String>> propertyValues; // HTTP response headers.
    private Headers headers;
    private String body;

    // TODO Should these have @Expose?
//    @SerializedName("m2m:cb")
//    private CommonServicesEntity commonServicesEntity;
    @Expose
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;
    //    @SerializedName("m2m:cnt")
//    private DataContainer dataContainer;
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

    public ApplicationEntity getApplicationEntity() {
        return applicationEntity;
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

//    public DataContainer getDataContainer() {
//        return dataContainer;
//    }
//
//    public void setDataContainer(DataContainer dataContainer) {
//        this.dataContainer = dataContainer;
//    }

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
