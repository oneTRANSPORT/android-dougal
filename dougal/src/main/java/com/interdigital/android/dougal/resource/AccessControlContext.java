package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessControlContext {

    @Expose
    @SerializedName("actw")
    private String[] accessControlWindows;
    @Expose
    @SerializedName("acip")
    private AccessControlIpAddress[] accessControlIpAddresses;

    public String[] getAccessControlWindows() {
        return accessControlWindows;
    }

    public void setAccessControlWindows(String[] accessControlWindows) {
        this.accessControlWindows = accessControlWindows;
    }

    public AccessControlIpAddress[] getAccessControlIpAddresses() {
        return accessControlIpAddresses;
    }

    public void setAccessControlIpAddresses(AccessControlIpAddress[] accessControlIpAddresses) {
        this.accessControlIpAddresses = accessControlIpAddresses;
    }
}
