package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
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
    // Never gets send.  TODO Move to interceptor?
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
    @Expose
    @SerializedName("ch")
    private ResourceChild[] resourceChildren; // The result of appending ?rcn=6.

    public ApplicationEntity(@NonNull String id, String appName,
                             @NonNull String applicationId, @NonNull String baseUrl, @NonNull String createPath,
                             @NonNull boolean requestReachable) {
        super(id, id, appName, Types.RESOURCE_TYPE_APPLICATION_ENTITY, baseUrl, createPath);
        this.appName = appName;
        this.applicationId = applicationId;
        this.requestReachable = requestReachable;
    }

    // TODO All non-blocking requests.
    public void create(String token) throws Exception {
        create(token, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public String createNonBlocking(String token) throws Exception {
        return create(token, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public void createAsync(String token, DougalCallback dougalCallback) {
        createAsync(token, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void createNonBlockingAsync(String token, DougalCallback dougalCallback) {
        createAsync(token, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public static ApplicationEntity retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                             @NonNull String retrievePath, String token) throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getApplicationEntity();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token, RESPONSE_TYPE_BLOCKING_REQUEST,
                null,
                new RetrieveCallback<ApplicationEntity>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    // TODO Add non-blocking retrieval.

    public static ApplicationEntity retrieveChildren(@NonNull String aeId, @NonNull String baseUrl,
                                                     @NonNull String retrievePath, String token)
            throws Exception {
        return retrieveChildren(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST).body().getApplicationEntity();
    }

    public void update(String token) throws Exception {
        Response<ResponseHolder> response = update(token, RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getApplicationEntity().getLastModifiedTime());
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

    public static UriList discover(@NonNull String aeId, @NonNull String baseUrl,
                                   @NonNull String retrievePath, String token, FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_APPLICATION_ENTITY);
        }
        return discoverBase(aeId, baseUrl, retrievePath, token, RESPONSE_TYPE_BLOCKING_REQUEST,
                filterCriteria).body().getUriList();
    }

    public static void discoverAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token, FilterCriteria filterCriteria,
                                     DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_APPLICATION_ENTITY);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, token, RESPONSE_TYPE_BLOCKING_REQUEST,
                filterCriteria, new RetrieveCallback<UriList>(aeId, baseUrl, retrievePath, dougalCallback));
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

    public ResourceChild[] getResourceChildren() {
        return resourceChildren;
    }

    public void setResourceChildren(ResourceChild[] resourceChildren) {
        this.resourceChildren = resourceChildren;
    }

    private String create(String token, @ResponseType int responseType)
            throws Exception {
        Response<ResponseHolder> response = create(token, responseType, this);
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                return null;
            default:
                return response.body().getResource().getResourceId();
        }
    }

}
