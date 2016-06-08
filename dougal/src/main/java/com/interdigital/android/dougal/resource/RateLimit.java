package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateLimit {

    @Expose
    @SerializedName("mnn")
    private Integer maxNrOfNotify; // Unsigned int.
    @Expose
    @SerializedName("tww")
    private String timeWindow;

    public Integer getMaxNrOfNotify() {
        return maxNrOfNotify;
    }

    public void setMaxNrOfNotify(Integer maxNrOfNotify) {
        this.maxNrOfNotify = maxNrOfNotify;
    }

    public String getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }
}
