package com.interdigital.android.onem2msdk.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.Types;
import com.interdigital.android.onem2msdk.resource.ApplicationEntity;
import com.interdigital.android.onem2msdk.resource.ContentInstance;
import com.interdigital.android.onem2msdk.resource.Resource;

public class RequestHolder {

    @Expose
    @SerializedName("ae")
    private ApplicationEntity applicationEntity;
    @Expose
    @SerializedName("cin")
    private ContentInstance contentInstance;
//    @Expose
//    @SerializedName("cnt")
//    private DataContainer dataContainer;

    public RequestHolder() {
    }

    public RequestHolder(Resource resource) {
        setResource(resource);
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

}
