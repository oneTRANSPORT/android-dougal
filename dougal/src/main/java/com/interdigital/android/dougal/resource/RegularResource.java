package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

public class RegularResource extends Resource {

    @Expose
    @SerializedName("et")
    private String expiryTime;
    @Expose
    @SerializedName("acpi")
    private String[] accessControlPolicyIds;

    public RegularResource(String resourceId, String resourceName,
                           @Types.ResourceType int resourceType, String parentId, String expiryTime,
                           String[] accessControlPolicyIds, String[] labels) {
        super(resourceId, resourceName, resourceType, parentId, labels);
        this.expiryTime = expiryTime;
        this.accessControlPolicyIds = accessControlPolicyIds;
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
}
