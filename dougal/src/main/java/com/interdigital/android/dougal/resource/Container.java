package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import retrofit2.Response;

public class Container extends Resource {

    private static final String LATEST_SUFFIX = "/la";
    private static final String OLDEST_SUFFIX = "/ol";

    // We need to send the ae-id on every request, so may as well keep a copy.
    private String aeId;
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

    public Container(String aeId, String resourceId, String resourceName, String parentId,
                     String expiryTime, Long maxByteSize, Long maxInstanceAge, Integer maxNumberOfInstances) {
        this(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_CONTAINER, parentId,
                expiryTime, null, null, maxByteSize, maxInstanceAge, maxNumberOfInstances);
    }

    public Container(String aeId, String resourceId, String resourceName,
                     @Types.ResourceType int resourceType, String parentId, String expiryTime,
                     String[] accessControlPolicyIds, String[] labels, Long maxByteSize, Long maxInstanceAge,
                     Integer maxNumberOfInstances) {
        super(resourceId, resourceName, resourceType, parentId, expiryTime, accessControlPolicyIds,
                labels);
        this.aeId = aeId;
        this.maxByteSize = maxByteSize;
        this.maxInstanceAge = maxInstanceAge;
        this.maxNumberOfInstances = maxNumberOfInstances;
    }

    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        Response<ResponseHolder> response = create(aeId, baseUrl, path, userName, password);
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

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(aeId, baseUrl, path, userName, password,
                new CreateCallback<Container>(this, dougalCallback));
    }

    public static Container retrieve(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        Container container = retrieveBase(aeId, baseUrl, path, userName, password).body()
                .getContainer();
        container.setAeId(aeId);
        container.setBaseUrl(baseUrl);
        container.setPath(path);
        return container;
    }

    public static ContentInstance retrieveLatest(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        return ContentInstance.retrieve(aeId, baseUrl, path + LATEST_SUFFIX, userName, password);
    }

    public static ContentInstance retrieveOldest(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        return ContentInstance.retrieve(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password);
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveAsyncBase(aeId, baseUrl, path, userName, password,
                new RetrieveCallback<Container>(baseUrl, path, dougalCallback));
    }

    public static void retrieveLatestAsync(String aeId, String baseUrl, String path,
                                           String userName, String password, DougalCallback dougalCallback) {
        retrieveAsyncBase(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                new RetrieveCallback<ContentInstance>(baseUrl, path + LATEST_SUFFIX, dougalCallback));
    }

    public static void retrieveOldestAsync(String aeId, String baseUrl, String path,
                                           String userName, String password, DougalCallback dougalCallback) {
        retrieveAsyncBase(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                new RetrieveCallback<ContentInstance>(baseUrl, path + OLDEST_SUFFIX, dougalCallback));
    }

    public void update(String userName, String password) throws Exception {
        // TODO Decide what to do here.
        Response<ResponseHolder> response = update(aeId, userName, password);
    }

    public void delete(String userName, String password) throws Exception {
        delete(aeId, userName, password);
    }

    public static void deleteLatest(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                                    String userName, String password) throws Exception {
        delete(aeId, baseUrl, path + LATEST_SUFFIX, userName, password);
    }

    public static void deleteOldest(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                                    String userName, String password) throws Exception {
        delete(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password);
    }

    public void deleteLatest(String userName, String password) throws Exception {
        delete(aeId, getBaseUrl(), getPath() + LATEST_SUFFIX, userName, password);
    }

    public void deleteOldest(String userName, String password) throws Exception {
        delete(aeId, getBaseUrl(), getPath() + OLDEST_SUFFIX, userName, password);
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password,
                new DeleteCallback(dougalCallback));
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, userName, password,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteLatestAsync(String aeId, String baseUrl, String path,
                                         String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path + LATEST_SUFFIX, userName, password,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteOldestAsync(String aeId, String baseUrl, String path,
                                         String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path + OLDEST_SUFFIX, userName, password,
                new DeleteCallback(dougalCallback));
    }

    public void deleteLatestAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, getBaseUrl(), getPath() + LATEST_SUFFIX, userName, password,
                new DeleteCallback(dougalCallback));
    }

    public void deleteOldestAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, getBaseUrl(), getPath() + OLDEST_SUFFIX, userName, password,
                new DeleteCallback(dougalCallback));
    }

//    public static Container getByName(Context context, String fqdn, int port, boolean useHttps,
//                                          String cseName, String aeName, String dcName, String aeId,
//                                          String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "/" + aeName + "/" + dcName);
//        return create(context, ri, useHttps, aeId, userName, password).getDataContainer();
//    }

//    public static Discovery discoverByAe(Context context, String fqdn, int port, boolean useHttps,
//                                         String cseName, String aeName, String aeId, String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "/" + aeName + "?fu=1&rty=3");
//        return discover(context, ri, useHttps, aeId, userName, password);
//    }

//    public static Discovery discoverAll(Context context, String fqdn, int port, boolean useHttps,
//                                        String cseName, String aeId, String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "?fu=1&rty=3");
//        return discover(context, ri, useHttps, aeId, userName, password);
//    }

    public String getAeId() {
        return aeId;
    }

    public void setAeId(String aeId) {
        this.aeId = aeId;
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
