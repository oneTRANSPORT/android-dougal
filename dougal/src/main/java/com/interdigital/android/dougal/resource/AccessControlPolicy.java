package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import retrofit2.Response;

public class AccessControlPolicy extends AnnounceableSubordinateResource {

    private String aeId;
    @Expose
    @SerializedName("pv")
    private AccessControlRule[] privileges;
    @Expose
    @SerializedName("pvs")
    private AccessControlRule[] selfPrivileges;

    public AccessControlPolicy(String aeId, String resourceId, String resourceName,
                               @Types.ResourceType int resourceType, String parentId, String[] labels) {
        super(resourceId, resourceName, resourceType, parentId, labels);
        this.aeId = aeId;
    }

    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        Response<ResponseHolder> response = create(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        AccessControlPolicy accessControlPolicy = response.body().getAccessControlPolicy();
        // Update current object.
        // TODO URL returned?
//        setCreationTime(contentInstance.getCreationTime());
//        setExpiryTime(contentInstance.getExpiryTime());
//        setLastModifiedTime(contentInstance.getLastModifiedTime());
//        setParentId(contentInstance.getParentId());
//        setResourceId(contentInstance.getResourceId());
//        setResourceName(contentInstance.getResourceName());
    }

    public AccessControlRule[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(AccessControlRule[] privileges) {
        this.privileges = privileges;
    }

    public AccessControlRule[] getSelfPrivileges() {
        return selfPrivileges;
    }

    public void setSelfPrivileges(AccessControlRule[] selfPrivileges) {
        this.selfPrivileges = selfPrivileges;
    }
}
