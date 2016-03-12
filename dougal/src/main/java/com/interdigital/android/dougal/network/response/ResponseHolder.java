package com.interdigital.android.dougal.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Discovery;
import com.interdigital.android.dougal.resource.Resource;

import okhttp3.Headers;

public class ResponseHolder {

    private int statusCode;
    private Headers headers;
    private String body;

    // This field is returned by the CSE as plain text, but we rewrite the response and
    // wrap it in a JSON container so it can be dealt with by Gson.
    @Expose
    @SerializedName("rewrite:id")
    private Resource plainTextId;
    @Expose
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;
    @Expose
    @SerializedName("m2m:cnt")
    private Container container;
    @Expose
    @SerializedName("m2m:cin")
    private ContentInstance contentInstance;
    @Expose
    @SerializedName("m2m:discovery")
    private Discovery discovery;

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

    public Resource getResource() {
        if (plainTextId != null) {
            return plainTextId;
        }
        if (applicationEntity != null) {
            return applicationEntity;
        }
        if (container != null) {
            return container;
        }
        if (contentInstance != null) {
            return contentInstance;
        }
        if (discovery != null) {
            return discovery;
        }
        return null;
    }

    public Resource getPlainTextId() {
        return plainTextId;
    }

    public void setPlainTextId(Resource plainTextId) {
        this.plainTextId = plainTextId;
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

    public Discovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }
}
