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

public class Container extends AnnounceableResource {

    private static final String LATEST_SUFFIX = "/la";
    private static final String OLDEST_SUFFIX = "/ol";

    @Expose
    @SerializedName("cr")
    private String creator;
    @Expose
    @SerializedName("cbs")
    private Long currentByteSize;
    @Expose
    @SerializedName("cni")
    private Integer currentNumberOfInstances;
    @Expose
    @SerializedName("mbs")
    private Long maxByteSize;
    @Expose
    @SerializedName("mia")
    private Long maxInstanceAge;
    @Expose
    @SerializedName("mni")
    private Integer maxNumberOfInstances;
    @Expose
    @SerializedName("st")
    private Integer stateTag = null; // Strictly an unsigned int.
    @Expose
    @SerializedName("ch")
    private ResourceChild[] resourceChildren; // The result of appending ?rcn=6.

    public Container(@NonNull String aeId, @NonNull String resourceId, @NonNull String resourceName,
                     @NonNull String baseUrl, @NonNull String createPath) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_CONTAINER, baseUrl,
                createPath);
    }

    public void create(String token)
            throws Exception {
        Response<ResponseHolder> response = create(token,
                RESPONSE_TYPE_BLOCKING_REQUEST, this);
        // TODO Non-blocking requests.
    }

    public void createAsync(String token, DougalCallback dougalCallback) {
        createAsync(token, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    // TODO Test.
    public Resource createNonBlocking(String token)
            throws Exception {
        return create(token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, this).body().getResource();
    }

    // TODO Test.
    public void createNonBlockingAsync(String token,
                                       DougalCallback dougalCallback) {
        createAsync(token, new NonBlockingIdCallback<>(dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public static Container retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token,
                                     FilterCriteria filterCriteria) throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body()
                .getContainer();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token,
                                     FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Container>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static Container retrievePayloadNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                                       @NonNull String retrievePath, String token)
            throws Exception {
        ResponseHolder responseHolder = retrievePayloadNonBlockingBase(aeId, baseUrl, retrievePath,
                token);
        if (responseHolder != null) {
            return responseHolder.getContainer();
        }
        return null;
    }

    public static void retrievePayloadNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                       @NonNull String retrievePath, String token,
                                                       DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<Container>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static ContentInstance retrieveLatest(@NonNull String aeId, @NonNull String baseUrl,
                                                 @NonNull String retrievePath, String token)
            throws Exception {
        return ContentInstance.retrieve(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token);
    }

    public static void retrieveLatestAsync(@NonNull String aeId, @NonNull String baseUrl,
                                           @NonNull String retrievePath, String token,
                                           DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<ContentInstance>(aeId, baseUrl, retrievePath + LATEST_SUFFIX,
                        dougalCallback));
    }

    // TODO Test.
    public static Resource retrieveLatestIdNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                                       @NonNull String retrievePath, String token)
            throws Exception {
        return Resource.retrieveIdNonBlocking(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                null);
    }

    // TODO Test.
    public static void retrieveLatestIdNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                        @NonNull String retrievePath, String token,
                                                        DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, null,
                new RetrieveCallback<ContentInstance>(aeId, baseUrl, retrievePath + LATEST_SUFFIX,
                        dougalCallback));
    }

    public static ContentInstance retrieveOldest(@NonNull String aeId, @NonNull String baseUrl,
                                                 @NonNull String retrievePath, String token) throws Exception {
        return ContentInstance.retrieve(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token);
    }

    public static void retrieveOldestAsync(@NonNull String aeId, @NonNull String baseUrl,
                                           @NonNull String retrievePath, String token,
                                           DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<ContentInstance>(aeId, baseUrl, retrievePath + OLDEST_SUFFIX,
                        dougalCallback));
    }

    // TODO Test.
    public static Resource retrieveOldestIdNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                                       @NonNull String retrievePath, String token)
            throws Exception {
        return Resource.retrieveIdNonBlocking(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token,
                null);
    }

    // TODO Test.
    public static void retrieveOldestIdNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                        @NonNull String retrievePath, String token,
                                                        DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, null,
                new RetrieveCallback<ContentInstance>(aeId, baseUrl, retrievePath + OLDEST_SUFFIX,
                        dougalCallback));
    }

    public static Container retrieveChildren(@NonNull String aeId, @NonNull String baseUrl,
                                             @NonNull String retrievePath, String token)
            throws Exception {
        return retrieveChildren(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST).body().getContainer();
    }

    public void update(String token) throws Exception {
        Response<ResponseHolder> response = update(token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getContainer().getLastModifiedTime());
    }

    public void updateAsync(String token, DougalCallback dougalCallback) {
        updateAsync(token, RESPONSE_TYPE_BLOCKING_REQUEST,
                new UpdateCallback<>(this, dougalCallback));
    }

    public Resource updateNonBlocking() {
        // TODO
        return null;
    }

    public Resource updateNonBlockingAsync() {
        // TODO
        return null;
    }

    // TODO Test.
    public void deleteNonBlocking(String token) throws Exception {
        delete(token, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public static void deleteNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String retrievePath, String token)
            throws Exception {
        delete(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test, callback.
    public void deleteNonBlockingAsync(String token,
                                       DougalCallback dougalCallback) {
        deleteAsync(token, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public static void deleteNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                              @NonNull String retrievePath, String token,
                                              DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    public void deleteLatest(String token) throws Exception {
        delete(getAeId(), getBaseUrl(), getRetrievePath() + LATEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void deleteLatest(@NonNull String aeId, @NonNull String baseUrl,
                                    @NonNull String retrievePath, String token)
            throws Exception {
        delete(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteLatestAsync(String token, DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getRetrievePath() + LATEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteLatestAsync(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String retrievePath, String token,
                                         DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.  Note only last argument differs.  Merge with above?
    public void deleteLatestNonBlocking(String token) throws Exception {
        delete(getAeId(), getBaseUrl(), getRetrievePath() + LATEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public static void deleteLatestNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                               @NonNull String retrievePath, String token)
            throws Exception {
        delete(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public void deleteLatestNonBlockingAsync(String token,
                                             DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getRetrievePath() + LATEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public static void deleteLatestNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                    @NonNull String retrievePath, String token,
                                                    DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrievePath + LATEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    public void deleteOldest(String token) throws Exception {
        delete(getAeId(), getBaseUrl(), getRetrievePath() + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void deleteOldest(@NonNull String aeId, @NonNull String baseUrl,
                                    @NonNull String retrievePath, String token)
            throws Exception {
        delete(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteOldestAsync(String token, DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getRetrievePath() + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteOldestAsync(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String retrivePath, String token,
                                         DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrivePath + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public void deleteOldestNonBlocking(String token) throws Exception {
        delete(getAeId(), getBaseUrl(), getRetrievePath() + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public static void deleteOldestNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                               @NonNull String retrievePath, String token)
            throws Exception {
        delete(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public void deleteOldestNonBlockingAsync(String token,
                                             DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getRetrievePath() + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public static void deleteOldestNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                    @NonNull String retrievePath, String token,
                                                    DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrievePath + OLDEST_SUFFIX, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO    Move this to base class with type parameter.
    public static UriList discover(@NonNull String aeId, @NonNull String baseUrl,
                                   @NonNull String retrievePath, String token,
                                   FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        return discoverBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getUriList();
    }

    public static void discoverAsync(String aeId, String baseUrl, String retrievePath,
                                     String token, FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<UriList>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static Resource discoverNonBlocking(String aeId, String baseUrl, String retrievePath,
                                               String token, FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        return discoverBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, filterCriteria)
                .body().getResource();
    }

    // TODO Test.  Base class?
    public static void discoverNonBlockingAsync(String aeId, String baseUrl, String retrievePath,
                                                String token, FilterCriteria filterCriteria,
                                                DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, filterCriteria,
                new RetrieveCallback<UriList>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCurrentByteSize() {
        return currentByteSize;
    }

    public void setCurrentByteSize(Long currentByteSize) {
        this.currentByteSize = currentByteSize;
    }

    public Integer getCurrentNumberOfInstances() {
        return currentNumberOfInstances;
    }

    public void setCurrentNumberOfInstances(Integer currentNumberOfInstances) {
        this.currentNumberOfInstances = currentNumberOfInstances;
    }

    public Long getMaxByteSize() {
        return maxByteSize;
    }

    public void setMaxByteSize(Long maxByteSize) {
        this.maxByteSize = maxByteSize;
    }

    public Long getMaxInstanceAge() {
        return maxInstanceAge;
    }

    public void setMaxInstanceAge(Long maxInstanceAge) {
        this.maxInstanceAge = maxInstanceAge;
    }

    public Integer getMaxNumberOfInstances() {
        return maxNumberOfInstances;
    }

    public void setMaxNumberOfInstances(Integer maxNumberOfInstances) {
        this.maxNumberOfInstances = maxNumberOfInstances;
    }

    public Integer getStateTag() {
        return stateTag;
    }

    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    public ResourceChild[] getResourceChildren() {
        return resourceChildren;
    }

    public void setResourceChildren(ResourceChild[] resourceChildren) {
        this.resourceChildren = resourceChildren;
    }
}
