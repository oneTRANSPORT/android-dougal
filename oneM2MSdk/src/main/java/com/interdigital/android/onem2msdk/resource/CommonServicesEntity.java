package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.network.RI;

public class CommonServicesEntity extends BaseResource {

    @SerializedName("csi")
    private String id;
    @SerializedName("cst")
    private String type;
    @SerializedName("poa")
    private String[] pointsOfAccess;
    @SerializedName("srt")
    private int[] supportedResourceTypes;

    public static CommonServicesEntity get(Context context, String fqdn, int port,
                                           String cseName, String aeId) {
        RI ri = new RI(fqdn, port, "/" + cseName);
        return get(context, ri, aeId).getCommonServicesEntity();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getPointsOfAccess() {
        return pointsOfAccess;
    }

    public void setPointsOfAccess(String[] pointsOfAccess) {
        this.pointsOfAccess = pointsOfAccess;
    }

    public int[] getSupportedResourceTypes() {
        return supportedResourceTypes;
    }

    public void setSupportedResourceTypes(int[] supportedResourceTypes) {
        this.supportedResourceTypes = supportedResourceTypes;
    }
}

//{"m2m:cb":                                                CSE_BASE
//        {"acpi":["/ONET-CSE-01Acp"],     ACCESS_CONTROL_POLICY_IDS
//        "csi":"ONET-CSE-01",                     CSE_ID
//        "cst":1,                                                CSE_TYPE
//        "ct":"20151118T094448",                CREATION_TIME
//        "lbl":["ONET-CSE-01"],                   ??? LABELS (from members)
//        "lt":"20151118T094448",                 LAST_MODIFIED_TIME
//        "poa":["http://127.0.0.1:9011"],     POINT_OF_ACCESS
//        "ri":"ONET-CSE-01",                        RESOURCE_ID
//        "rn":"ONETCSE01",                          RESOURCE_NAME
//        "srt":[1,2,3,4,9,11,12,13,14,15,16,17,18,19,20,23,10001,
// 10002,10003,10004,10009,10013,10014,10016,10018],  SUPPORTED_RESOURCE_TYPE
//        "ty":5}                                                 RESOURCE_TYPE (from parameters)
//        }
