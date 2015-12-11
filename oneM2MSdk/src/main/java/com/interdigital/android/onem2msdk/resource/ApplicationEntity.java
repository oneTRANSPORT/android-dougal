package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;

public class ApplicationEntity extends BaseResource {

    @Expose
    @SerializedName("aei")
    private String id;
    @Expose
    @SerializedName("api")
    private String applicationId;
    @Expose
    @SerializedName("rr")
    private boolean requestReachable;

    public static ApplicationEntity get(Context context,
                                        String fqdn, String cseName, String aeName, String aeId) {
        RI ri = new RI(fqdn, "/" + cseName + "/" + aeName);
        return get(context, ri, aeId).getApplicationEntity();
    }

    public static ApplicationEntity create(Context context,
                                           String fqdn, String cseName, String aeName, String aeId) {
        RI ri = new RI(fqdn, "/" + cseName);
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setApplicationId(aeName);
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setApplicationEntity(applicationEntity);
        requestHolder.putOriginProperty(aeId);
        requestHolder.putContentTypeProperty("application/json; ty=2");
        requestHolder.putNameProperty(aeName);
        return post(context, ri, requestHolder).getApplicationEntity();
    }

    public static Discovery discoverAll(Context context, String fqdn, String cseName, String aeId) {
        RI ri = new RI(fqdn, cseName + "?fu=1&rty=ae");
        return discover(context, ri, aeId);
    }

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