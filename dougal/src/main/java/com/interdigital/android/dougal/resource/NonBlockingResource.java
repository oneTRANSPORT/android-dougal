package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.shared.MetaInformation;
import com.interdigital.android.dougal.shared.OperationResult;

public class NonBlockingResource extends RegularResource {

    @Expose
    @SerializedName("mi")
    private MetaInformation metaInformation;
    @Expose
    @SerializedName("og")
    private String originator;
    @Expose
    @SerializedName("oprl") // Was ol.  TODO Check.
    private OperationResult operationResult;
    @Expose
    @SerializedName("op")
    private Integer operation;
    @Expose
    @SerializedName("rid")
    private String requestId;
    @Expose
    @SerializedName("rs")
    private Integer requestStatus;
    @Expose
    @SerializedName("st")
    private Integer stateTag;
    @Expose
    @SerializedName("tg")
    private String target;

    public NonBlockingResource(String resourceId, String resourceName,
                               @Types.ResourceType int resourceType, String parentId, String expiryTime,
                               String[] accessControlPolicyIds, String[] labels) {
        super(resourceId, resourceName, resourceType, expiryTime, accessControlPolicyIds, labels);
    }

    public MetaInformation getMetaInformation() {
        return metaInformation;
    }

    public void setMetaInformation(MetaInformation metaInformation) {
        this.metaInformation = metaInformation;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    @NonNull
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getStateTag() {
        return stateTag;
    }

    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
