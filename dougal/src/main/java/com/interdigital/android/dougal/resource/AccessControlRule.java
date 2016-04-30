package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

public class AccessControlRule {

    @Expose
    @SerializedName("acor")
    private String[] accessControlOriginators;
    @Types.AccessControlOperation
    @Expose
    @SerializedName("acop")
    private Integer accessControlOperations;
    @Expose
    @SerializedName("acco")
    private AccessControlContext[] accessControlContexts;
    @Expose
    @SerializedName("aclr")
    private LocationRegion[] accessControlLocationRegions;

    public String[] getAccessControlOriginators() {
        return accessControlOriginators;
    }

    public void setAccessControlOriginators(String[] accessControlOriginators) {
        this.accessControlOriginators = accessControlOriginators;
    }

    public Integer getAccessControlOperations() {
        return accessControlOperations;
    }

    public void setAccessControlOperations(Integer accessControlOperations) {
        this.accessControlOperations = accessControlOperations;
    }

    public AccessControlContext[] getAccessControlContexts() {
        return accessControlContexts;
    }

    public void setAccessControlContexts(AccessControlContext[] accessControlContexts) {
        this.accessControlContexts = accessControlContexts;
    }

    public LocationRegion[] getAccessControlLocationRegions() {
        return accessControlLocationRegions;
    }

    public void setAccessControlLocationRegions(LocationRegion[] accessControlLocationRegions) {
        this.accessControlLocationRegions = accessControlLocationRegions;
    }
}
