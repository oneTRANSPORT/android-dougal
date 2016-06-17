package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.dougal.resource.callback.RetrieveCallback;
import com.interdigital.android.dougal.shared.FilterCriteria;

import retrofit2.Callback;
import retrofit2.Response;

public class ContentInstance extends AnnounceableResource {

    @Expose
    @SerializedName("cr")
    private String creator;
    @Expose
    @SerializedName("cnf")
    private String contentInfo;
    @Expose
    @SerializedName("con")
    private String content;
    @Expose
    @SerializedName("cs")
    private Long contentSize;
    @Expose
    @SerializedName("st")
    private Integer stateTag = null; // Strictly an unsigned int.

    public ContentInstance(@NonNull String aeId, @NonNull String resourceId,
                           @NonNull String resourceName, @NonNull String baseUrl, @NonNull String createPath,
                           @NonNull String content) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_CONTENT_INSTANCE, baseUrl,
                createPath);
        this.content = content;
    }

    public void create(String userName, String password) throws Exception {
        create(userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void createNonBlocking(String userName, String password) throws Exception {
        create(userName, password, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public void createAsync(String userName, String password, DougalCallback dougalCallback) {
        createAsync(userName, password, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static ContentInstance retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                           @NonNull String retrievePath, String userName, String password) throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getContentInstance();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<ContentInstance>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static ContentInstance retrieveNonBlockingPayload(@NonNull String aeId,
                                                             @NonNull String baseUrl, @NonNull String retrievePath,
                                                             String userName, String password) throws Exception {
        return ((NonBlockingRequest) retrieveBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getResource())
                .getPrimitiveContent().getContentInstance();
    }

    @Override
    public Response<ResponseHolder> update(String userName, String password,
                                           @ResponseType int responseType) {
        throw new UnsupportedOperationException("Content instances may not be updated");
    }

    @Override
    public void updateAsync(String userName, String password, @ResponseType int responseType,
                            Callback<ResponseHolder> callback) {
        throw new UnsupportedOperationException("Content instances may not be updated");
    }

    public static Discovery discover(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String userName, String password,
                                     FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTENT_INSTANCE);
        }
        return discoverBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getDiscovery();
    }

    public static void discoverAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String userName, String password,
                                     FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTENT_INSTANCE);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Discovery>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getContentSize() {
        return contentSize;
    }

    public void setContentSize(Long contentSize) {
        this.contentSize = contentSize;
    }

    public Integer getStateTag() {
        return stateTag;
    }

    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    private String create(String userName, String password, @ResponseType int responseType)
            throws Exception {
        Response<ResponseHolder> response = create(userName, password, responseType, this);
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                return null;
            default:
                return response.body().getResource().getResourceId();
        }
    }
}