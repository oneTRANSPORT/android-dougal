package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

public class Discovery extends Resource {

    @Expose
    @SerializedName("discoveredURI")
    private String[] discoveredUri;
    @Expose
    @SerializedName("truncated")
    private boolean truncated;

    public Discovery(String resourceId, String resourceName, @Types.ResourceType int resourceType,
                     String parentId, String expiryTime, String[] accessControlPolicyIds, String[] labels) {
        super(resourceId, resourceName, resourceType, parentId, expiryTime, accessControlPolicyIds, labels);
    }

    public String[] getDiscoveredUri() {
        return discoveredUri;
    }

    public void setDiscoveredUri(String[] discoveredUri) {
        this.discoveredUri = discoveredUri;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }
}

//{"m2m:discovery":{
//        "discoveredURI":[
//        "/ONETCSE01/ANDROID",
//        "/ONETCSE01/BUCKS-IMPORT",
//        "/ONETCSE01/CPAULANDROID1",
//        "/ONETCSE01/C_ANDROID",
//        "/ONETCSE01/Feed_Import"],
//        "truncated":"false"}
//}
