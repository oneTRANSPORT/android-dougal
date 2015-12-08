package com.interdigital.android.onem2msdk;

import android.support.annotation.NonNull;

import java.io.File;

public class RI {

// Resource identifier.
// Same allowed chars as Uri.

    private String ri = null;

    // Unstructured or structured relative RI.
    // May not begin with '/'.
    public RI(String fqdn, @NonNull String ri) {
        String slash = File.separator;
        if (fqdn != null) {
            if (ri.startsWith(slash)) {
                ri = slash + slash + fqdn + ri;
            } else {
                ri = slash + slash + fqdn + slash + ri;
            }
        }
        this.ri = ri;
    }


    public String getRiString() { // TODO Url?
        return ri;
    }
}
