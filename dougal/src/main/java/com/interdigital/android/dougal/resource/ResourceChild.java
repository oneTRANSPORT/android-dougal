package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceChild {

    @Expose
    @SerializedName("#text")
    private String path;
    @Expose
    @SerializedName("-nm")
    private String name;
    @Expose
    @SerializedName("-typ")
    private Integer type;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
