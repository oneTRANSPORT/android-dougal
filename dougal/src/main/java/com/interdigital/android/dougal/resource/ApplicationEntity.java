package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
import com.interdigital.android.dougal.resource.callback.RetrieveCallback;
import com.interdigital.android.dougal.resource.callback.UpdateCallback;
import com.interdigital.android.dougal.shared.FilterCriteria;

import retrofit2.Response;

public class ApplicationEntity extends AnnounceableResource {

    // TODO Check this.
    // The AE id.
    //    @Expose
//    @SerializedName("aei")
//    private String id;  Should be the resourceId.
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
        setResourceId(id);
        this.appName = appName;
        this.applicationId = applicationId;
        this.requestReachable = requestReachable;
        this.pointOfAccessList = pointOfAccessList;
        this.ontologyRef = ontologyRef;
        this.nodeLink = nodeLink;
    }

    // TODO All non-blocking requests.
    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        create(baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public String createNonBlocking(String baseUrl, String path, String userName, String password)
            throws Exception {
        return create(baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getResourceId(), baseUrl, path, userName, password,
                new CreateCallback<>(this, dougalCallback), RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void createNonBlockingAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(getResourceId(), baseUrl, path, userName, password,
                new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public static ApplicationEntity retrieve(
            String aeId, String baseUrl, String path, String userName, String password)
            throws Exception {
        ApplicationEntity applicationEntity = retrieveBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getApplicationEntity();
        applicationEntity.setBaseUrl(baseUrl);
        applicationEntity.setPath(path);
        return applicationEntity;
    }

    public static void retrieveAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<ApplicationEntity>(baseUrl, path, dougalCallback));
    }

    // TODO Add non-blocking retrieval.

    public void update(String userName, String password) throws Exception {
        Response<ResponseHolder> response = update(getResourceId(), userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getApplicationEntity().getLastModifiedTime());
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
        delete(getResourceId(), userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void delete(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                              String userName, String password)
            throws Exception {
        delete(aeId, baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(getResourceId(), userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static Discovery discover(String aeId, String baseUrl, String path,
                                     String userName, String password) throws Exception {
        FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.putResourceType(Types.RESOURCE_TYPE_APPLICATION_ENTITY);
        return discoverBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getDiscovery();
    }

    public static Discovery discover(String aeId, String baseUrl, String path, FilterCriteria filterCriteria,
                                     String userName, String password) throws Exception {
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_APPLICATION_ENTITY);
        }
        return discoverBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getDiscovery();
    }

    public static void discoverAsync(String aeId, String baseUrl, String path,
                                     String userName, String password, DougalCallback dougalCallback) {
        FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.putResourceType(Types.RESOURCE_TYPE_APPLICATION_ENTITY);
        discoverAsyncBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
    }

    public static void discoverAsync(String aeId, String baseUrl, String path, FilterCriteria filterCriteria,
                                     String userName, String password, DougalCallback dougalCallback) {
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_APPLICATION_ENTITY);
        }
        discoverAsyncBase(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Discovery>(baseUrl, path, dougalCallback));
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

    private String create(String baseUrl, String path, String userName, String password,
                          @ResponseType int responseType)
            throws Exception {
        Response<ResponseHolder> response = create(getResourceId(), baseUrl, path, userName, password,
                responseType);
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                ApplicationEntity applicationEntity = response.body().getApplicationEntity();
                // Update current object.
                // TODO URL returned?
                setCreationTime(applicationEntity.getCreationTime());
                setExpiryTime(applicationEntity.getExpiryTime());
                setLastModifiedTime(applicationEntity.getLastModifiedTime());
                setParentId(applicationEntity.getParentId());
                setResourceId(applicationEntity.getResourceId());
                setResourceName(applicationEntity.getResourceName());
                return null;
            default:
                // TODO ResponseHolder needs to have a plain text sub-object.
                return null;
        }
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