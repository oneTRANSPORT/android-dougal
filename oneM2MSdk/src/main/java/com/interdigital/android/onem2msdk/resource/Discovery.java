package com.interdigital.android.onem2msdk.resource;

import com.google.gson.annotations.SerializedName;

public class Discovery extends BaseResource {

    @SerializedName("discoveredURI")
    private String[] discoveredUri;
    @SerializedName("truncated")
    private boolean truncated;

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
