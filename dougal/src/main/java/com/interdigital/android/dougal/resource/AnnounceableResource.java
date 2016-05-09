package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

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

    public AnnounceableResource(@NonNull String aeId, @NonNull String resourceId,
                                @NonNull String resourceName, @Types.ResourceType int resourceType,
                                @NonNull String baseUrl, @NonNull String path) {
        super(aeId, resourceId, resourceName, resourceType, baseUrl, path);
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
