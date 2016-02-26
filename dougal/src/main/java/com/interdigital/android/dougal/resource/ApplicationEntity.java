package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import java.io.IOException;

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
    private boolean requestReachable;
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
                             String[] labels, String id, String appName, String applicationId, boolean requestReachable,
                             String[] pointOfAccessList, String ontologyRef, String nodeLink) {
        super(null, null, Types.RESOURCE_TYPE_APPLICATION_ENTITY, null, null, null, expiryTime,
                accessControlPolicyIds, labels);
        this.id = id;
        // The resource id shall be the same as the AE id.
        setResourceId(id);
        this.appName = appName;
        // We will set the resource name to be the same as the app name.
        setResourceName(appName);
        this.applicationId = applicationId;
        this.requestReachable = requestReachable;
        this.pointOfAccessList = pointOfAccessList;
        this.ontologyRef = ontologyRef;
        this.nodeLink = nodeLink;
    }

    // Since we don't know the resource Uri, uriCreate is temporary (collection-level) only.
    public int create(String baseUrl, String path, String userName, String password) throws IOException {
        Response<ResponseHolder> response = create(baseUrl, path, id, userName, password);
        setLastStatusCode(response);
        int code = getLastStatusCode();
        if (code == Types.STATUS_CODE_CREATED) {
            ApplicationEntity applicationEntity = response.body().getApplicationEntity();
            // Update current object.
            setCreationTime(applicationEntity.getCreationTime());
            setExpiryTime(applicationEntity.getExpiryTime());
            setLastModifiedTime(applicationEntity.getLastModifiedTime());
            setParentId(applicationEntity.getParentId());
            setResourceId(applicationEntity.getResourceId());
            setResourceName(applicationEntity.getResourceName());
        }
        return code;
    }

    // TODO How to return status code if not ACCEPTED?
    public static ApplicationEntity retrieveAe(
            String baseUrl, String path, String aeId, String userName, String password) throws IOException {
        Response<ResponseHolder> response = retrieve(baseUrl, path, aeId, userName, password);
        if (getCodeFromResponse(response) == Types.STATUS_CODE_ACCEPTED) {
            ApplicationEntity applicationEntity = retrieve(baseUrl, path, aeId, userName, password).body()
                    .getApplicationEntity();
            applicationEntity.setBaseUrl(baseUrl);
            applicationEntity.setPath(path);
            applicationEntity.setLastStatusCode(response);
            return applicationEntity;
        }
        return null;
    }

//    public static ApplicationEntity create(Context context, String fqdn, int port, boolean useHttps,
//                                           String cseName, String aeName, String aeId, String userName, String password) {
//        Ri ri = new Ri(fqdn, port, "/" + cseName);
//        ApplicationEntity applicationEntity = new ApplicationEntity();
//        applicationEntity.setApplicationId(aeName);
//        RequestHolder requestHolder = new RequestHolder();
//        requestHolder.setApplicationEntity(applicationEntity);
//        requestHolder.putOriginProperty(aeId);
//        requestHolder.putContentTypeProperty("application/json; ty=2");
//        requestHolder.putNameProperty(aeName);
//        return post(context, ri, useHttps, requestHolder, userName, password).getApplicationEntity();
//    }

//    public static int delete(Context context,
//                             String fqdn, int port, boolean useHttps, String cseName, String aeName, String aeId,
//                             String userName, String password) {
//        Ri ri = new Ri(fqdn, port, "/" + cseName + "/" + aeName);
//        return delete(context, ri, useHttps, aeId, userName, password).getStatusCode();
//    }

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

    public boolean isRequestReachable() {
        return requestReachable;
    }

    public void setRequestReachable(boolean requestReachable) {
        this.requestReachable = requestReachable;
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