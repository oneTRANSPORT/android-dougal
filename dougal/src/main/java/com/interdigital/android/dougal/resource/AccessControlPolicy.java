package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

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

    public AccessControlPolicy(@NonNull String aeId, @NonNull String resourceId,
                               @NonNull String resourceName, @NonNull String baseUrl,
                               @NonNull String createPath, @NonNull SetOfAcrs privileges,
                               @NonNull SetOfAcrs selfPrivileges) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY,
                baseUrl, createPath);
        this.privileges = privileges;
        this.selfPrivileges = selfPrivileges;
    }

    public void create(String token) throws Exception {
        Response<ResponseHolder> response = create(token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, this);
    }

    public void createAsync(String token,  DougalCallback dougalCallback) {
        createAsync(token,  new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    // TODO Non-blocking requests don't need "this".
    public Resource createNonBlocking(String token) throws Exception {
        return create(token,  RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                this).body().getResource();
    }

    public void createNonBlockingAsync(String token, 
                                       DougalCallback dougalCallback) {
        createAsync(token,  new NonBlockingIdCallback<>(dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public static AccessControlPolicy retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                               @NonNull String retrievePath, String token, 
                                               FilterCriteria filterCriteria)
            throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getAccessControlPolicy();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token, 
                                     FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<AccessControlPolicy>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static AccessControlPolicy retrievePayloadNonBlocking(@NonNull String aeId,
                                                                 @NonNull String baseUrl, @NonNull String retrievePath,
                                                                 String token) throws Exception {
        ResponseHolder responseHolder = retrievePayloadNonBlockingBase(aeId, baseUrl, retrievePath,
                token);
        if (responseHolder != null) {
            return responseHolder.getAccessControlPolicy();
        }
        return null;
    }

    public static void retrievePayloadNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                       @NonNull String retrievePath, String token, 
                                                       DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<AccessControlPolicy>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public void update(String token) throws Exception {
        Response<ResponseHolder> response = update(token, 
                RESPONSE_TYPE_BLOCKING_REQUEST);
        // TODO Should go in resource base class?
        setLastModifiedTime(response.body().getAccessControlPolicy().getLastModifiedTime());
    }

    public void updateAsync(String token,  DougalCallback dougalCallback) {
        updateAsync(token,  RESPONSE_TYPE_BLOCKING_REQUEST,
                new UpdateCallback<>(this, dougalCallback));
    }

    public Resource updateNonBlocking(String token) throws Exception {
        return update(token, 
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH).body().getResource();
    }

    public void updateNonBlockingAsync(String token, 
                                       DougalCallback dougalCallback) {
        updateAsync(token,  RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new UpdateCallback<>(this, dougalCallback));
    }

    public void delete(String token) throws Exception {
        delete(token,  RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void delete(@NonNull String aeId, @NonNull String baseUrl,
                              @NonNull String retrievePath, String token) throws Exception {
        delete(aeId, baseUrl, retrievePath, token,  RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteAsync(String token,  DougalCallback dougalCallback) {
        deleteAsync(token,  RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteAsync(@NonNull String aeId, @NonNull String baseUrl,
                                   @NonNull String retrievePath, String token, 
                                   DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrievePath, token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, new DeleteCallback(dougalCallback));
    }

    public static UriList discover(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token, 
                                     FilterCriteria filterCriteria) throws Exception {
        // TODO Move this to resource base class.
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY);
        }
        return discoverBase(aeId, baseUrl, retrievePath, token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getUriList();
    }

    public static void discoverAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token, 
                                     FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_ACCESS_CONTROL_POLICY);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, token, 
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<UriList>(aeId, baseUrl, retrievePath, dougalCallback));
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
