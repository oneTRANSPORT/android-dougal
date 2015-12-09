package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.RI;
import com.interdigital.android.onem2msdk.Response;
import com.interdigital.android.onem2msdk.SDK;

import java.util.HashMap;

public class ApplicationEntity extends BaseResource {

    @SerializedName("aei")
    private String id;
    @SerializedName("api")
    private String applicationId;
    @SerializedName("rr")
    private boolean requestReachable;

    public static ApplicationEntity get(Context context, RI ri, String aeId) {
        HashMap<String, String> propertyValues = new HashMap<>();
        propertyValues.put("X-M2M-Origin", aeId);
        Response response = SDK.getInstance().getResource(context, ri, propertyValues);
        return response.getApplicationEntity();
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