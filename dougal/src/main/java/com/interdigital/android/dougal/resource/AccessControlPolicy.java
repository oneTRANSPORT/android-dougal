package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.dougal.resource.callback.NonBlockingIdCallback;
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
                RESPONSE_TYPE_BLOCKING_REQUEST, this);
    }

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getAeId(), baseUrl, path, userName, password, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    // TODO Non-blocking requests don't need "this".
    public Resource createNonBlocking(String baseUrl, String path, String userName, String password)
            throws Exception {
        return create(getAeId(), baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, this).body().getResource();
    }

    public void createNonBlockingAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getAeId(), baseUrl, path, userName, password,
                new NonBlockingIdCallback<>(dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public static AccessControlPolicy retrieve(String aeId, String baseUrl, String path,
                                               String userName, String password, FilterCriteria filterCriteria)
            throws Exception {
        return retrieveBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getAccessControlPolicy();
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<AccessControlPolicy>(baseUrl, path, dougalCallback));
    }

    public static AccessControlPolicy retrievePayloadNonBlocking(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        ResponseHolder responseHolder = retrievePayloadNonBlockingBase(aeId, baseUrl, path,
                userName, password);
        if (responseHolder != null) {
            return responseHolder.getAccessControlPolicy();
        }
        return null;
    }

    public static void retrievePayloadNonBlockingAsync(String aeId, String baseUrl, String path,
                                                       String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
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

    public Resource updateNonBlocking(String userName, String password) throws Exception {
        return update(getResourceId(), userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH).body().getResource();
    }

    public void updateNonBlockingAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        updateAsync(getAeId(), userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new UpdateCallback<>(this, dougalCallback));
    }

    public void delete(String userName, String password) throws Exception {
        delete(getAeId(), userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void delete(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                              String userName, String password) throws Exception {
        delete(aeId, baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(getAeId(), userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static Discovery discover(String aeId, String baseUrl, String path,
                                     String userName, String password, @Nullable FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY);
        }
        return discoverBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getDiscovery();
    }

    public static void discoverAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, @Nullable FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY);
        }
        discoverAsyncBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
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
