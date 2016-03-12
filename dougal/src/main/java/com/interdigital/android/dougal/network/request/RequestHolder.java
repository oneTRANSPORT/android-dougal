package com.interdigital.android.dougal.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Container;

public class RequestHolder {

    @Expose
    @SerializedName("ae")
    private ApplicationEntity applicationEntity;
    @Expose
    @SerializedName("cin")
    private ContentInstance contentInstance;
    @Expose
    @SerializedName("cnt")
    private Container container;

    public RequestHolder() {
    }

    public RequestHolder(Resource resource) {
        setResource(resource);
    }

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

    public void setResource(Resource resource) {
        // TODO Add all the rest of the objects.
        switch (resource.getResourceType()) {
            case Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY:
                break;
            case Types.RESOURCE_TYPE_APPLICATION_ENTITY:
                setApplicationEntity((ApplicationEntity) resource);
                break;
            case Types.RESOURCE_TYPE_CONTAINER:
                setContainer((Container) resource);
                break;
            case Types.RESOURCE_TYPE_CONTENT_INSTANCE:
                setContentInstance((ContentInstance) resource);
                break;
            case Types.RESOURCE_TYPE_CSE_BASE:
                break;
            case Types.RESOURCE_TYPE_DELIVERY:
                break;
            case Types.RESOURCE_TYPE_EVENT_CONFIG:
                break;
            case Types.RESOURCE_TYPE_EXEC_INSTANCE:
                break;
            case Types.RESOURCE_TYPE_GROUP:
                break;
            case Types.RESOURCE_TYPE_LOCATION_POLICY:
                break;
            case Types.RESOURCE_TYPE_M2M_SERVICE_SUBSCRIPTION_PROFILE:
                break;
            case Types.RESOURCE_TYPE_MGMT_CMD:
                break;
            case Types.RESOURCE_TYPE_MGMT_OBJ:
                break;
            case Types.RESOURCE_TYPE_NODE:
                break;
            case Types.RESOURCE_TYPE_POLLING_CHANNEL:
                break;
            case Types.RESOURCE_TYPE_REMOTE_CSE:
                break;
            case Types.RESOURCE_TYPE_REQUEST:
                break;
            case Types.RESOURCE_TYPE_SCHEDULE:
                break;
            case Types.RESOURCE_TYPE_SERVICE_SUBSCRIBED_APP_RULE:
                break;
            case Types.RESOURCE_TYPE_SERVICE_SUBSCRIBED_NODE:
                break;
            case Types.RESOURCE_TYPE_STATS_COLLECT:
                break;
            case Types.RESOURCE_TYPE_STATS_CONFIG:
                break;
            case Types.RESOURCE_TYPE_SUBSCRIPTION:
                break;
            case Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY_ANNC:
                break;
            case Types.RESOURCE_TYPE_AE_ANNC:
                break;
            case Types.RESOURCE_TYPE_CONTAINER_ANNC:
                break;
            case Types.RESOURCE_TYPE_CONTENT_INSTANCE_ANNC:
                break;
            case Types.RESOURCE_TYPE_GROUP_ANNC:
                break;
            case Types.RESOURCE_TYPE_LOCATION_POLICY_ANNC:
                break;
            case Types.RESOURCE_TYPE_MGMT_OBJ_ANNC:
                break;
            case Types.RESOURCE_TYPE_NODE_ANNC:
                break;
            case Types.RESOURCE_TYPE_REMOTE_CSE_ANNC:
                break;
            case Types.RESOURCE_TYPE_SCHEDULE_ANNC:
                break;
        }
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public void setContentInstance(ContentInstance contentInstance) {
        this.contentInstance = contentInstance;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

}
