package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
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

    // TODO Check NotNull annotations on all.

    public Container(String aeId, String resourceId, String resourceName, String parentId,
                     String expiryTime, Long maxByteSize, Long maxInstanceAge, Integer maxNumberOfInstances) {
        this(aeId, resourceId, resourceName, parentId, expiryTime, null, null, maxByteSize,
                maxInstanceAge, maxNumberOfInstances);
    }

    public Container(String aeId, String resourceId, String resourceName, String parentId,
                     String expiryTime, String[] accessControlPolicyIds, String[] labels, Long maxByteSize,
                     Long maxInstanceAge, Integer maxNumberOfInstances) {
        super(resourceId, resourceName, Types.RESOURCE_TYPE_CONTAINER, parentId, expiryTime,
                accessControlPolicyIds, labels);
        setAeId(aeId);
        this.maxByteSize = maxByteSize;
        this.maxInstanceAge = maxInstanceAge;
        this.maxNumberOfInstances = maxNumberOfInstances;
    }

    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        Response<ResponseHolder> response = create(getAeId(), baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        Container container = response.body().getContainer();
        // Update current object.
        // TODO URL returned?
        setCreationTime(container.getCreationTime());
        setExpiryTime(container.getExpiryTime());
        setLastModifiedTime(container.getLastModifiedTime());
        setParentId(container.getParentId());
        setResourceId(container.getResourceId());
        setResourceName(container.getResourceName());
    }

    // TODO Decide order of parameters.
    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getAeId(), baseUrl, path, userName, password,
                new CreateCallback<>(this, dougalCallback), RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    // TODO Test.
    public Resource createNonBlocking(String baseUrl, String path, String userName, String password)
            throws Exception {
        return create(getAeId(), baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH).body().getResource();
    }

    // TODO Test.
    public void createNonBlockingAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getAeId(), baseUrl, path, userName, password,
                new NonBlockingIdCallback<>(dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public static Container retrieve(String aeId, String baseUrl, String path, String userName,
                                     String password, FilterCriteria filterCriteria) throws Exception {
        Container container = retrieveBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body()
                .getContainer();
        if (container != null) {
            container.setAeId(aeId);
            container.setBaseUrl(baseUrl);
            container.setPath(path);
        }
        return container;
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Container>(baseUrl, path, dougalCallback));
    }

    public static Container retrievePayloadNonBlocking(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        ResponseHolder responseHolder = retrievePayloadNonBlockingBase(aeId, baseUrl, path,
                userName, password);
        if (responseHolder != null) {
            return responseHolder.getContainer();
        }
        return null;
    }

    public static void retrievePayloadNonBlockingAsync(String aeId, String baseUrl, String path,
                                                       String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<Container>(baseUrl, path, dougalCallback));
    }

    public static ContentInstance retrieveLatest(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        return ContentInstance.retrieve(aeId, baseUrl, path + LATEST_SUFFIX, userName, password);
    }

    public static void retrieveLatestAsync(String aeId, String baseUrl, String path,
                                           String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<ContentInstance>(baseUrl, path + LATEST_SUFFIX, dougalCallback));
    }

    // TODO Test.
    public static Resource retrieveLatestIdNonBlocking(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        return Resource.retrieveIdNonBlocking(aeId, baseUrl, path + LATEST_SUFFIX, userName,
                password, null);
    }

    // TODO Test.
    public static void retrieveLatestIdNonBlockingAsync(String aeId, String baseUrl, String path,
                                                        String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, null,
                new RetrieveCallback<ContentInstance>(baseUrl, path + LATEST_SUFFIX, dougalCallback));
    }

    public static ContentInstance retrieveOldest(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        return ContentInstance.retrieve(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password);
    }

    public static void retrieveOldestAsync(String aeId, String baseUrl, String path,
                                           String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<ContentInstance>(baseUrl, path + OLDEST_SUFFIX, dougalCallback));
    }

    // TODO Test.
    public static Resource retrieveOldestIdNonBlocking(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        return Resource.retrieveIdNonBlocking(aeId, baseUrl, path + OLDEST_SUFFIX, userName,
                password, null);
    }

    // TODO Test.
    public static void retrieveOldestIdNonBlockingAsync(String aeId, String baseUrl, String path,
                                                        String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, null,
                new RetrieveCallback<ContentInstance>(baseUrl, path + OLDEST_SUFFIX, dougalCallback));
    }

    public void update(String userName, String password) throws Exception {
        Response<ResponseHolder> response = update(getResourceId(), userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getContainer().getLastModifiedTime());
    }

    public void updateAsync(String userName, String password, DougalCallback dougalCallback) {
        updateAsync(getResourceId(), userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
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

    // TODO Test.
    public void deleteNonBlocking(String userName, String password) throws Exception {
        delete(getAeId(), userName, password, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public static void deleteNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String path, String userName, String password)
            throws Exception {
        delete(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test, callback.
    public void deleteNonBlockingAsync(String userName, String password,
                                       DougalCallback dougalCallback) {
        deleteAsync(getAeId(), userName, password, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public static void deleteNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                              @NonNull String path, String userName, String password,
                                              DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    public void deleteLatest(String userName, String password) throws Exception {
        delete(getAeId(), getBaseUrl(), getPath() + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void deleteLatest(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                                    String userName, String password) throws Exception {
        delete(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteLatestAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getPath() + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteLatestAsync(String aeId, String baseUrl, String path,
                                         String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.  Note only last argument differs.  Merge with above?
    public void deleteLatestNonBlocking(String userName, String password) throws Exception {
        delete(getAeId(), getBaseUrl(), getPath() + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public static void deleteLatestNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                               @NonNull String path, String userName, String password)
            throws Exception {
        delete(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public void deleteLatestNonBlockingAsync(String userName, String password,
                                             DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getPath() + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public static void deleteLatestNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                    @NonNull String path, String userName, String password,
                                                    DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    public void deleteOldest(String userName, String password) throws Exception {
        delete(getAeId(), getBaseUrl(), getPath() + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void deleteOldest(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                                    String userName, String password) throws Exception {
        delete(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteOldestAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getPath() + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteOldestAsync(String aeId, String baseUrl, String path,
                                         String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public void deleteOldestNonBlocking(String userName, String password) throws Exception {
        delete(getAeId(), getBaseUrl(), getPath() + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public static void deleteOldestNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                               @NonNull String path, String userName, String password)
            throws Exception {
        delete(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    // TODO Test.
    public void deleteOldestNonBlockingAsync(String userName, String password,
                                             DougalCallback dougalCallback) {
        deleteAsync(getAeId(), getBaseUrl(), getPath() + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    // TODO Test.
    public static void deleteOldestNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                    @NonNull String path, String userName, String password,
                                                    DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                new DeleteCallback(dougalCallback));
    }

    public static Discovery discover(String aeId, String baseUrl, String path,
                                     String userName, String password, @Nullable FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        return discoverBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getDiscovery();
    }

    public static void discoverAsync(String aeId, String baseUrl, String path,
                                     String userName, String password,@Nullable FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        discoverAsyncBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
    }

    // TODO Test.  Should this be in the base class?
    public static Resource discoverNonBlocking(String aeId, String baseUrl, String path,
                                               String userName, String password, FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        return discoverBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, filterCriteria)
                .body().getResource();
    }

    // TODO Test.  Base class?
    public static void discoverNonBlockingAsync(String aeId, String baseUrl, String path,
                                                String userName, String password, FilterCriteria filterCriteria,
                                                DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTAINER);
        }
        discoverAsyncBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, filterCriteria,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
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
}

//{"m2m:cnt":{
//        "cbs":3275,
//        "cni":5,
//        "ct":"20151118T171858",
//        "et":"20151122T043858",
//        "lbl":["myContainerInCCURL"],
//        "lt":"20151118T171858",
//        "mbs":60000000,
//        "mia":1600,
//        "mni":4,
//        "pi":"C-BUCKS-IMPORT",
//        "ri":"cnt_20151118T171858_7",
//        "rn":"carpark",
//        "st":284,
//        "ty":3}
//}
