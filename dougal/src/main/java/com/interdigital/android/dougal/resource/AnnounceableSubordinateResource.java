package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

public class AnnounceableSubordinateResource extends Resource {

    @Expose
    @SerializedName("at")
    private String[] announceTo;
    @Expose
    @SerializedName("aa")
    private String announcedAttribute;
    @Expose
    @SerializedName("et")
    private String expiryTime;

    public AnnounceableSubordinateResource(String resourceId, String resourceName,
                                           @Types.ResourceType int resourceType, String parentId, String[] labels) {
        super(resourceId, resourceName, resourceType, parentId, labels);
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

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}
