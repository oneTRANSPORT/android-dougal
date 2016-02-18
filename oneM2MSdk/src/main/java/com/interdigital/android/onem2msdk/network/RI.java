package com.interdigital.android.onem2msdk.network;

import android.support.annotation.NonNull;

import java.io.File;

public class Ri {

// Resource identifier.
// Same allowed chars as Uri.

    private static final String PREFIX_HTTPS = "https:";
    private static final String PREFIX_HTTP = "http:";

    private String ri = null;
    private boolean useHttps = false;

    // Unstructured or structured relative RI.
    // May not begin with '/'.
    public Ri(String fqdn, int port, @NonNull String ri, boolean useHttps) {
        String slash = File.separator;
        if (fqdn != null) {
            if (ri.startsWith(slash)) {
                ri = slash + slash + fqdn + ":" + port + ri;
            } else {
                ri = slash + slash + fqdn + ":" + port + slash + ri;
            }
        }
        this.ri = ri;
        this.useHttps = useHttps;
    }

    public String getRiString() { // TODO Url?
        return ri;
    }

    public String createUrl() {
        if (useHttps) {
            return PREFIX_HTTPS + getRiString();
        }
        return PREFIX_HTTP + getRiString();
    }
}
