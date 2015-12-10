package com.interdigital.android.onem2msdk.resource;

import com.google.gson.annotations.SerializedName;

public class BaseResource {

    @SerializedName("ri")
    private String resourceId;
    @SerializedName("rn")
    private String resourceName;
    @SerializedName("ty")
    private String resourceType;
    @SerializedName("pi")
    private String parentId;
    @SerializedName("ct")
    private String creationTime;
    @SerializedName("lt")
    private String lastModifiedTime;
    @SerializedName("et")
    private String expiryTime;
    @SerializedName("acpi")
    private String[] accessControlPolicyIds;
    @SerializedName("lbl")
    private String[] labels;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String[] getAccessControlPolicyIds() {
        return accessControlPolicyIds;
    }

    public void setAccessControlPolicyIds(String[] accessControlPolicyIds) {
        this.accessControlPolicyIds = accessControlPolicyIds;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }
}

