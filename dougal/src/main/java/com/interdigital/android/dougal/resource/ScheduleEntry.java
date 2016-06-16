package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleEntry {

    @Expose
    @SerializedName("sce")
    private String scheduleEntry;

    public String getScheduleEntry() {
        return scheduleEntry;
    }

    public void setScheduleEntry(String scheduleEntry) {
        this.scheduleEntry = scheduleEntry;
    }
}
