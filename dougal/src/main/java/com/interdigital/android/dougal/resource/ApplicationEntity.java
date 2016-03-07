package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
import com.interdigital.android.dougal.resource.callback.RetrieveCallback;
import com.interdigital.android.dougal.resource.callback.UpdateCallback;

import retrofit2.Response;

public class ApplicationEntity extends Resource {

    // The AE id.
    //    @Expose
//    @SerializedName("aei")
    private String id;
    //    @Expose
//    @SerializedName("apn")
    private String appName;
    @Expose
    @SerializedName("api")
    private String applicationId;
    @Expose
    @SerializedName("rr")
    private Boolean requestReachable;
    @Expose
    @SerializedName("poa")
    private String[] pointOfAccessList;
    @Expose
    @SerializedName("or")
    private String ontologyRef;
    @Expose
    @SerializedName("nl")
    private String nodeLink;

    public ApplicationEntity(String id, String appName, String applicationId) {
        this(null, null, null, id, appName, applicationId, false, null, null, null);
    }

    public ApplicationEntity(String expiryTime, String[] accessControlPolicyIds,
                             String[] labels, String id, String appName, String applicationId, Boolean requestReachable,
                             String[] pointOfAccessList, String ontologyRef, String nodeLink) {
        super(id, appName, Types.RESOURCE_TYPE_APPLICATION_ENTITY, null, expiryTime,
                accessControlPolicyIds, labels);
        this.id = id;
        this.appName = appName;
        this.applicationId = applicationId;
        this.requestReachable = requestReachable;
        this.pointOfAccessList = pointOfAccessList;
        this.ontologyRef = ontologyRef;
        this.nodeLink = nodeLink;
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
        createAsync(id, baseUrl, path, userName, password,
                new CreateCallback<ApplicationEntity>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static ApplicationEntity retrieve(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        ApplicationEntity applicationEntity = retrieveBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST).body()
                .getApplicationEntity();
        applicationEntity.setBaseUrl(baseUrl);
        applicationEntity.setPath(path);
        return applicationEntity;
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveAsyncBase(aeId, baseUrl, path, userName, password,
                new RetrieveCallback<ApplicationEntity>(baseUrl, path, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void update(String userName, String password) throws Exception {
        // TODO Decide what to do here.
        Response<ResponseHolder> response = update(id, userName, password);
    }

    public void updateAsync(String userName, String password, DougalCallback dougalCallback) {
        updateAsync(id, userName, password, new UpdateCallback<>(this, dougalCallback));
    }

    public void delete(String userName, String password) throws Exception {
        delete(id, userName, password);
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password,
                new DeleteCallback(dougalCallback), RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(id, userName, password,
                new DeleteCallback(dougalCallback), RESPONSE_TYPE_BLOCKING_REQUEST);
    }

//    public static Discovery discoverAll(Context context, String fqdn, int port, boolean useHttps,
//                                        String cseName, String aeId, String userName, String password) {
//        Ri ri = new Ri(fqdn, port, cseName + "?fu=1&rty=ae");
//        return discover(context, ri, useHttps, aeId, userName, password);
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Boolean isRequestReachable() {
        return requestReachable;
    }

    public void setRequestReachable(Boolean requestReachable) {
        this.requestReachable = requestReachable;
    }

    public String[] getPointOfAccessList() {
        return pointOfAccessList;
    }

    public void setPointOfAccessList(String[] pointOfAccessList) {
        this.pointOfAccessList = pointOfAccessList;
    }

    public String getOntologyRef() {
        return ontologyRef;
    }

    public void setOntologyRef(String ontologyRef) {
        this.ontologyRef = ontologyRef;
    }

    public String getNodeLink() {
        return nodeLink;
    }

    public void setNodeLink(String nodeLink) {
        this.nodeLink = nodeLink;
    }

    private void create(String baseUrl, String path, String userName, String password,
                        @ResponseType int responseType)
            throws Exception {
        Response<ResponseHolder> response = create(id, baseUrl, path, userName, password,
                responseType);
        ApplicationEntity applicationEntity = response.body().getApplicationEntity();
        // Update current object.
        // TODO URL returned?
        setCreationTime(applicationEntity.getCreationTime());
        setExpiryTime(applicationEntity.getExpiryTime());
        setLastModifiedTime(applicationEntity.getLastModifiedTime());
        setParentId(applicationEntity.getParentId());
        setResourceId(applicationEntity.getResourceId());
        setResourceName(applicationEntity.getResourceName());
    }

}

//    {"m2m:ae":
//         {"aei":"C-ANDROID",
//           "api":"C_ANDROID",
//           "ct":"20151203T173717",
//           "et":"20151207T045717",
//           "lt":"20151203T173717",
//           "pi":"ONET-CSE-01",
//           "ri":"C-ANDROID",
//           "rn":"ANDROID",
//           "rr":true,
//           "ty":2
//         }
//    }