package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
import com.interdigital.android.dougal.resource.callback.RetrieveCallback;
import com.interdigital.android.dougal.shared.FilterCriteria;

import retrofit2.Callback;
import retrofit2.Response;

public class ContentInstance extends AnnounceableResource {

    private String aeId;
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

    public ContentInstance(String aeId, String resourceId, String resourceName, String parentId,
                           String expiryTime, String contentInfo, String content, Long contentSize) {
        this(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_CONTENT_INSTANCE, parentId,
                expiryTime, null, null, contentInfo, content, contentSize);
    }

    public ContentInstance(String aeId, String resourceId, String resourceName,
                           @Types.ResourceType int resourceType, String parentId, String expiryTime,
                           String[] accessControlPolicyIds, String[] labels, String contentInfo, String content,
                           Long contentSize) {
        super(resourceId, resourceName, resourceType, parentId, expiryTime, accessControlPolicyIds, labels);
        this.aeId = aeId;
        this.contentInfo = contentInfo;
        this.content = content;
        this.contentSize = contentSize;
    }

    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        create(baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void createNonBlocking(String baseUrl, String path, String userName, String password)
            throws Exception {
        create(baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(aeId, baseUrl, path, userName, password,
                new CreateCallback<ContentInstance>(this, dougalCallback));
    }

    public static ContentInstance retrieve(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        ContentInstance contentInstance = retrieveBase(aeId, baseUrl, path, userName, password,
                null).body().getContentInstance();
        contentInstance.setAeId(aeId);
        contentInstance.setBaseUrl(baseUrl);
        contentInstance.setPath(path);
        return contentInstance;
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveAsyncBase(aeId, baseUrl, path, userName, password,
                new RetrieveCallback<ContentInstance>(baseUrl, path, dougalCallback));
    }

    @Override
    public Response<ResponseHolder> update(@NonNull String aeId, String userName, String password) {
        throw new UnsupportedOperationException("Content instances may not be updated");
    }

    @Override
    public void updateAsync(@NonNull String aeId, String userName, String password, Callback<ResponseHolder> callback) {
        throw new UnsupportedOperationException("Content instances may not be updated");
    }

    public void delete(String userName, String password) throws Exception {
        delete(aeId, userName, password);
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password, new DeleteCallback(dougalCallback));
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, userName, password, new DeleteCallback(dougalCallback));
    }

    public static Discovery discover(String aeId, String baseUrl, String path,
                                     String userName, String password) throws Exception {
        FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTENT_INSTANCE);
        return discoverBase(aeId, baseUrl, path, filterCriteria, userName, password).body().getDiscovery();
    }

    public static Discovery discover(String aeId, String baseUrl, String path, FilterCriteria filterCriteria,
                                     String userName, String password) throws Exception {
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTENT_INSTANCE);
        }
        return discoverBase(aeId, baseUrl, path, filterCriteria, userName, password).body().getDiscovery();
    }

    public static void discoverAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTENT_INSTANCE);
        discoverAsyncBase(aeId, baseUrl, path, filterCriteria, userName, password,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
    }

    public static void discoverAsync(String aeId, String baseUrl, String path, FilterCriteria filterCriteria,
                                     String userName, String password, DougalCallback dougalCallback) {
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_CONTENT_INSTANCE);
        }
        discoverAsyncBase(aeId, baseUrl, path, filterCriteria, userName, password,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
    }

    public String getAeId() {
        return aeId;
    }

    public void setAeId(String aeId) {
        this.aeId = aeId;
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

    public long getContentSize() {
        return contentSize;
    }

    public void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }

    private void create(String baseUrl, String path, String userName, String password,
                        @ResponseType int responseType)
            throws Exception { // TODO Add response type.
        Response<ResponseHolder> response = create(aeId, baseUrl, path, userName, password);
        ContentInstance contentInstance = response.body().getContentInstance();
        // Update current object.
        // TODO URL returned?
        setCreationTime(contentInstance.getCreationTime());
        setExpiryTime(contentInstance.getExpiryTime());
        setLastModifiedTime(contentInstance.getLastModifiedTime());
        setParentId(contentInstance.getParentId());
        setResourceId(contentInstance.getResourceId());
        setResourceName(contentInstance.getResourceName());
    }

    public Integer getStateTag() {
        return stateTag;
    }

    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }
}

//{"m2m:cin":{
//        "cnf":"text/plain:0",
//        "con":"0",
//        "cs":1,
//        "ct":"20151211T115813",
//        "et":"20151214T231813",
//        "lt":"20151211T115813",
//        "pi":"cnt_20151127T101353_104207",
//        "ri":"cin_20151211T115813_194",
//        "rn":"cin_20151211T115813_195201512111158_1975461145_nm",
//        "st":2508,
//        "ty":4}
//}
