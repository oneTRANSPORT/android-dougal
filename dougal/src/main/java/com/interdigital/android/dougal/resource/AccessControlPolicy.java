package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.RetrieveCallback;
import com.interdigital.android.dougal.resource.callback.UpdateCallback;
import com.interdigital.android.dougal.shared.FilterCriteria;

import retrofit2.Response;

public class AccessControlPolicy extends AnnounceableSubordinateResource {

    @Expose
    @SerializedName("pv")
    private SetOfAcrs privileges;
    @Expose
    @SerializedName("pvs")
    private SetOfAcrs selfPrivileges;

    public AccessControlPolicy(String aeId, String resourceId, String resourceName) {
        super(resourceId, resourceName, Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY, null);
        setAeId(aeId);
    }

    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        Response<ResponseHolder> response = create(getAeId(), baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        AccessControlPolicy accessControlPolicy = response.body().getAccessControlPolicy();
        // Update current object.
        // TODO Move these to super method?
        setCreationTime(accessControlPolicy.getCreationTime());
        setExpiryTime(accessControlPolicy.getExpiryTime());
        setLastModifiedTime(accessControlPolicy.getLastModifiedTime());
        setParentId(accessControlPolicy.getParentId());
        setResourceId(accessControlPolicy.getResourceId());
    }

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getAeId(), baseUrl, path, userName, password, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static AccessControlPolicy retrieve(String aeId, String baseUrl, String path,
                                               String userName, String password, FilterCriteria filterCriteria)
            throws Exception {
        AccessControlPolicy accessControlPolicy = retrieveBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getAccessControlPolicy();
        accessControlPolicy.setAeId(aeId);
        accessControlPolicy.setBaseUrl(baseUrl);
        accessControlPolicy.setPath(path);
        return accessControlPolicy;
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<AccessControlPolicy>(baseUrl, path, dougalCallback));
    }

    public void update(String userName, String password) throws Exception {
        Response<ResponseHolder> response = update(getResourceId(), userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getAccessControlPolicy().getLastModifiedTime());
    }

    public void updateAsync(String userName, String password, DougalCallback dougalCallback) {
        updateAsync(getResourceId(), userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new UpdateCallback<>(this, dougalCallback));
    }

    public SetOfAcrs getPrivileges() {
        return privileges;
    }

    public void setPrivileges(SetOfAcrs privileges) {
        this.privileges = privileges;
    }

    public SetOfAcrs getSelfPrivileges() {
        return selfPrivileges;
    }

    public void setSelfPrivileges(SetOfAcrs selfPrivileges) {
        this.selfPrivileges = selfPrivileges;
    }
}
