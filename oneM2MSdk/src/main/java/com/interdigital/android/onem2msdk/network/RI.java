package com.interdigital.android.onem2msdk.network;

import android.support.annotation.NonNull;

import java.io.File;

public class RI {

// Resource identifier.
// Same allowed chars as Uri.

    private String ri = null;

    // Unstructured or structured relative RI.
    // May not begin with '/'.
    public RI(String fqdn, int port, @NonNull String ri) {
        String slash = File.separator;
        if (fqdn != null) {
            if (ri.startsWith(slash)) {
                ri = slash + slash + fqdn + ":" + port + ri;
            } else {
                ri = slash + slash + fqdn + ":" + port + slash + ri;
            }
        }
        this.ri = ri;
    }

    public String getRiString() { // TODO Url?
        return ri;
    }
}
