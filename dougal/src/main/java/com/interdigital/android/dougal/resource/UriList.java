package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

// Doesn't really extend resource, but the CSE returns a resource at the top
// level, so we have to make this look like one.  Ideally the spec would change.
public class UriList extends Resource {

    @Expose
    @SerializedName("list")
    private String[] list;

    // This constructor will never be used.  The UriList resource is a little scruffy, but probably the
    // best way to go until somebody tidies up the oneM2M specification.
    public UriList() {
        super(null, null, null, Types.RESOURCE_TYPE_NONE, null, null);
    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }
}
