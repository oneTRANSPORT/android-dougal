package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

public class AnnounceableResource extends RegularResource {

    @Expose
    @SerializedName("at")
    private String[] announceTo;
    @Expose
    @SerializedName("aa")
    private String announcedAttribute;

    public AnnounceableResource(String resourceId, String resourceName,
                                @Types.ResourceType int resourceType, String parentId, String expiryTime,
                                String[] accessControlPolicyIds, String[] labels) {
        super(resourceId, resourceName, resourceType, expiryTime, accessControlPolicyIds, labels);
    }

    public String[] getAnnounceTo() {
        return announceTo;
    }

    public void setAnnounceTo(String[] announceTo) {
        this.announceTo = announceTo;
    }

    public String getAnnouncedAttribute() {
        return announcedAttribute;
    }

    public void setAnnouncedAttribute(String announcedAttribute) {
        this.announcedAttribute = announcedAttribute;
    }
}
