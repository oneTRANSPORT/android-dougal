package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.Types;
import com.interdigital.android.onem2msdk.network.Ri;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;

public class ApplicationEntity extends BaseResource {

//    @Expose
    @SerializedName("aei")
    private String id;
//    @Expose
    @SerializedName("apn")
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

    public ApplicationEntity(Ri ri, String expiryTime, String[] accessControlPolicyIds, String[] labels,
                             String id, String appName, String applicationId, boolean requestReachable,
                             String[] pointOfAccessList, String ontologyRef, String nodeLink) {
        super(null, null, Types.RESOURCE_TYPE_APPLICATION_ENTITY, null, null, null, expiryTime,
                accessControlPolicyIds, labels);
        setRi(ri);
        this.id = id;
        this.appName = appName;
        this.applicationId = applicationId;
        this.requestReachable = requestReachable;
        this.pointOfAccessList = pointOfAccessList;
        this.ontologyRef = ontologyRef;
        this.nodeLink = nodeLink;
    }

    public void create(String cseName, String aeName, String aeId,
                       String userName, String password) {
        ApplicationEntity applicationEntity = create(userName, password)
                .getApplicationEntity();
        // Update current object.
        id = applicationEntity.getId(); // TODO Already set?
        setCreationTime(applicationEntity.getCreationTime());
        setExpiryTime(applicationEntity.getExpiryTime());
        setLastModifiedTime(applicationEntity.getLastModifiedTime());
        setParentId(applicationEntity.getParentId());
        setResourceId(applicationEntity.getResourceId());
        setResourceName(applicationEntity.getResourceName());
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