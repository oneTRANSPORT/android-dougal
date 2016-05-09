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

    public Discovery() {
        super(null, null, null, Types.RESOURCE_TYPE_DISCOVERY, null, null);
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
