package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.SDK;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

import java.util.HashMap;

public class ApplicationEntity extends BaseResource {

    @SerializedName("aei")
    private String id;
    @SerializedName("api")
    private String applicationId;
    @SerializedName("rr")
    private boolean requestReachable;

    // TODO Replace RI with fqdn, cseName and aeName?
    public static ApplicationEntity get(Context context, RI ri, String aeId) {
        HashMap<String, String> propertyValues = new HashMap<>();
        propertyValues.put("X-M2M-Origin", aeId);
        ResponseHolder responseHolder = SDK.getInstance().getResource(context, ri, propertyValues);
        return responseHolder.getApplicationEntity();
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

    public static ApplicationEntity create(Context context, RI ri, String aeId, String applicationId) {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setApplicationId(applicationId);
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setApplicationEntity(applicationEntity);
        Gson gson = new Gson();
        String json = gson.toJson(requestHolder);
        HashMap<String, String> propertyValues = new HashMap<>();
        propertyValues.put("X-M2M-Origin", aeId);
        propertyValues.put("Content-Type", "application/json; ty=2");
        propertyValues.put("X-M2M-NM", applicationId);
        ResponseHolder responseHolder = SDK.getInstance().postResource(context, ri, propertyValues, json);
        return responseHolder.getApplicationEntity();

    }

    public static Discovery discoverAll(Context context, String fqdn, String cseName, String aeId) {
        RI ri = new RI(fqdn, cseName + "?fu=1&rty=ae");
        HashMap<String, String> propertyValues = new HashMap<>();
        propertyValues.put("X-M2M-Origin", aeId);
        ResponseHolder responseHolder = SDK.getInstance().getResource(context, ri, propertyValues);
        return responseHolder.getDiscovery();
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