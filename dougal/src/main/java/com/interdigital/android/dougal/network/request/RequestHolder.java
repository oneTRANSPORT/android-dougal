package com.interdigital.android.dougal.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.resource.AccessControlPolicy;
import com.interdigital.android.dougal.resource.ApplicationEntity;
import com.interdigital.android.dougal.resource.Container;
import com.interdigital.android.dougal.resource.ContentInstance;
import com.interdigital.android.dougal.resource.Group;
import com.interdigital.android.dougal.resource.LocationPolicy;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.Schedule;
import com.interdigital.android.dougal.resource.Subscription;

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
    @Expose
    @SerializedName("grp")
    private Group group;
    @Expose
    @SerializedName("acp")
    private AccessControlPolicy accessControlPolicy;
    @Expose
    @SerializedName("sub")
    private Subscription subscription;
    @Expose
    @SerializedName("sch")
    private Schedule schedule;
    @Expose
    @SerializedName("lcp")
    private LocationPolicy locationPolicy;

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
        if (group != null) {
            return group;
        }
        if (accessControlPolicy != null) {
            return accessControlPolicy;
        }
        if (subscription != null) {
            return subscription;
        }
        if (schedule != null) {
            return schedule;
        }
        if (locationPolicy != null) {
            return locationPolicy;
        }
        return null;
    }

    public void setResource(Resource resource) {
        // TODO Add all the rest of the objects.
        switch (resource.getResourceType()) {
            case Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY:
                setAccessControlPolicy((AccessControlPolicy) resource);
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
                setGroup((Group) resource);
                break;
            case Types.RESOURCE_TYPE_LOCATION_POLICY:
                setLocationPolicy((LocationPolicy) resource);
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
                setSchedule((Schedule) resource);
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
                setSubscription((Subscription) resource);
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

    public void setGroup(Group group) {
        this.group = group;
    }

    public AccessControlPolicy getAccessControlPolicy() {
        return accessControlPolicy;
    }

    public void setAccessControlPolicy(AccessControlPolicy accessControlPolicy) {
        this.accessControlPolicy = accessControlPolicy;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setLocationPolicy(LocationPolicy locationPolicy) {
        this.locationPolicy = locationPolicy;
    }
}
