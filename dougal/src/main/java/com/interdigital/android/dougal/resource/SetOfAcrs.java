package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetOfAcrs {

    @Expose
    @SerializedName("acr")
    private AccessControlRule[] accessControlRules;

    public AccessControlRule[] getAccessControlRules() {
        return accessControlRules;
    }

    public void setAccessControlRules(AccessControlRule[] accessControlRules) {
        this.accessControlRules = accessControlRules;
    }
}
