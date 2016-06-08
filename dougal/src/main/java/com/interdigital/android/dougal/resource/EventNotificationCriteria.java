package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;

public class EventNotificationCriteria {

    @Expose
    @SerializedName("crb")
    private String createdBefore;
    @Expose
    @SerializedName("cra")
    private String createdAfter;
    @Expose
    @SerializedName("ms")
    private String modifiedSince;
    @Expose
    @SerializedName("us")
    private String unmodifiedSince;
    @Expose
    @SerializedName("sts")
    private Integer stateTagSmaller; // Unsigned int.
    @Expose
    @SerializedName("stb")
    private Integer stateTagBigger; // Unsigned int.
    @Expose
    @SerializedName("exb")
    private String expireBefore;
    @Expose
    @SerializedName("exa")
    private String expireAfter;
    @Expose
    @SerializedName("sza")
    private Integer sizeAbove; // Unsigned int.
    @Expose
    @SerializedName("szb")
    private Integer sizeBelow; // Unsigned int.
    @Expose
    @SerializedName("om")
    @Types.Operation
    private Integer operationMonitor;
    @Expose
    @SerializedName("atr")
    private Attribute attribute;
    @Expose
    @SerializedName("net")
    @Types.NotificationEventType
    private Integer notificationEventType;

    public String getCreatedBefore() {
        return createdBefore;
    }

    public void setCreatedBefore(String createdBefore) {
        this.createdBefore = createdBefore;
    }

    public String getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(String createdAfter) {
        this.createdAfter = createdAfter;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public String getUnmodifiedSince() {
        return unmodifiedSince;
    }

    public void setUnmodifiedSince(String unmodifiedSince) {
        this.unmodifiedSince = unmodifiedSince;
    }

    public Integer getStateTagBigger() {
        return stateTagBigger;
    }

    public void setStateTagBigger(Integer stateTagBigger) {
        this.stateTagBigger = stateTagBigger;
    }

    public Integer getStateTagSmaller() {
        return stateTagSmaller;
    }

    public void setStateTagSmaller(Integer stateTagSmaller) {
        this.stateTagSmaller = stateTagSmaller;
    }

    public String getExpireBefore() {
        return expireBefore;
    }

    public void setExpireBefore(String expireBefore) {
        this.expireBefore = expireBefore;
    }

    public String getExpireAfter() {
        return expireAfter;
    }

    public void setExpireAfter(String expireAfter) {
        this.expireAfter = expireAfter;
    }

    public Integer getSizeAbove() {
        return sizeAbove;
    }

    public void setSizeAbove(Integer sizeAbove) {
        this.sizeAbove = sizeAbove;
    }

    public Integer getSizeBelow() {
        return sizeBelow;
    }

    public void setSizeBelow(Integer sizeBelow) {
        this.sizeBelow = sizeBelow;
    }

    public Integer getOperationMonitor() {
        return operationMonitor;
    }

    public void setOperationMonitor(Integer operationMonitor) {
        this.operationMonitor = operationMonitor;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Integer getNotificationEventType() {
        return notificationEventType;
    }

    public void setNotificationEventType(Integer notificationEventType) {
        this.notificationEventType = notificationEventType;
    }
}
