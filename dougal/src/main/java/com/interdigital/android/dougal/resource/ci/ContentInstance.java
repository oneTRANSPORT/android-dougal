package com.interdigital.android.dougal.resource.ci;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.co.ContainerDeleteCallback;

import retrofit2.Response;

public class ContentInstance extends Resource {

    private static final String OLDEST_CI = "ol";
    private static final String LAST_CI = "la";

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

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(aeId, baseUrl, path, userName, password,
                new ContentInstanceCreateCallback(this, dougalCallback));
    }

    public static ContentInstance retrieve(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        ContentInstance contentInstance = retrieveBase(aeId, baseUrl, path, userName, password).body()
                .getContentInstance();
        contentInstance.setAeId(aeId);
        contentInstance.setBaseUrl(baseUrl);
        contentInstance.setPath(path);
        return contentInstance;
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveAsyncBase(aeId, baseUrl, path, userName, password,
                new ContentInstanceRetrieveCallback(baseUrl, path, dougalCallback));
    }

    public void update(String userName, String password) throws Exception {
        // TODO Decide what to do here.
        Response<ResponseHolder> response = update(aeId, userName, password);
    }

    public void delete(String userName, String password) throws Exception {
        delete(aeId, userName, password);
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password,
                new ContentInstanceDeleteCallback(dougalCallback));
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, userName, password,
                new ContainerDeleteCallback(dougalCallback));
    }

//    public static ContentInstance retrieve(String fqdn, int port, boolean useHttps,
//                                           String cseName, String aeName, String dcName, String ciName, String aeId,
//                                           String userName, String password) {
//        return Resource.retrieve(
//                new Ri(fqdn, port, cseName + "/" + aeName + "/" + dcName + "/" + ciName, useHttps),
//                aeId, userName, password).getContentInstance();
//    }

    // Needs a filter parameter for oldest and latest.
//    public static ContentInstance retrieve(String fqdn, int port, boolean useHttps,
//                                           String cseName, String aeName, String dcName, String aeId,
//                                           String userName, String password) {
//        return Resource.retrieve(
//                new Ri(fqdn, port, cseName + "/" + aeName + "/" + dcName + "/" + LAST_CI, useHttps),
//                aeId, userName, password).getContentInstance();
//    }

//    public static ContentInstance create(Context context, String fqdn, int port, boolean useHttps,
//                                         String cseName, String aeName, String dcName, String aeId, String content,
//                                         String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "/" + aeName + "/" + dcName);
//        ContentInstance contentInstance = new ContentInstance();
//        // TODO Is this needed?
////        contentInstance.setContentInfo("application/json:0");
//        contentInstance.setContent(content);
//        RequestHolder requestHolder = new RequestHolder();
//        requestHolder.setContentInstance(contentInstance);
//        requestHolder.putOriginProperty(aeId);
////        requestHolder.putContentTypeProperty("application/json; ty=4");
//        requestHolder.putContentTypeProperty("application/vnd.onem2m-res+xml; ty=4");
//        // TODO Assume an anonymous CI name for now.
//        // The server will create a new CI name and add it to the data container.
////        requestHolder.putNameProperty(ciName);
//        ResponseHolder responseHolder = postCin(context, ri, useHttps, requestHolder, userName, password);
//        if (responseHolder == null) {
//            return null;
//        }
//        return responseHolder.getContentInstance();
//    }

//    public static Discovery discoverByDc(Context context, String fqdn, int port, boolean useHttps,
//                                         String cseName, String aeName, String dcName, String aeId,
//                                         String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "/" + aeName + "/" + dcName + "?fu=1&rty=4");
//        return discover(context, ri, useHttps, aeId, userName, password);
//    }
//
//    public static Discovery discoverByAe(Context context, String fqdn, int port, boolean useHttps,
//                                         String cseName, String aeName, String aeId, String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "/" + aeName + "?fu=1&rty=4");
//        return discover(context, ri, useHttps, aeId, userName, password);
//    }
//
//    public static Discovery discoverAll(Context context, String fqdn, int port, boolean useHttps,
//                                        String cseName, String aeId, String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "?fu=1&rty=4");
//        return discover(context, ri, useHttps, aeId, userName, password);
//    }

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
