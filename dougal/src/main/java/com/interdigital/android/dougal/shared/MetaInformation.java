package com.interdigital.android.dougal.shared;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.resource.Resource;

public class MetaInformation {

    // TODO Implement this object.
    //    @Expose
//    @SerializedName("cpn")
//    private CmdhPolicy cmdhPolicyName;
    @Expose
    @SerializedName("da")
    private Boolean deliveryAggregation;
    @Expose
    @SerializedName("drt")
    private Integer discoveryResultType;
    @Expose
    @SerializedName("fc")
    private FilterCriteria filterCriteria;
    @Expose
    @SerializedName("gid")
    private String groupRequestIdentifier;
    @Expose
    @SerializedName("oet")
    private String operationExecutionTime;
    @Expose
    @SerializedName("ot")
    private String originatingTimestamp;
    //    @Expose
//    @SerializedName("rctn")
//    private TODO Don't know what this is.
    @Expose
    @SerializedName("rp")
    private String resultPersistance;
    @Expose
    @SerializedName("rqet")
    private String requestExpirationTimestamp;
    @Expose
    @SerializedName("rset")
    private String resultExpirationTimestamp;
    @Expose
    @SerializedName("rt")
    @Resource.ResponseType
    private Integer responseType;
    @Expose
    @SerializedName("ty")
    @Types.ResourceType
    private Integer resourceType;

    public Boolean getDeliveryAggregation() {
        return deliveryAggregation;
    }

    public void setDeliveryAggregation(Boolean deliveryAggregation) {
        this.deliveryAggregation = deliveryAggregation;
    }

    public Integer getDiscoveryResultType() {
        return discoveryResultType;
    }

    public void setDiscoveryResultType(Integer discoveryResultType) {
        this.discoveryResultType = discoveryResultType;
    }

    public FilterCriteria getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(FilterCriteria filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    public String getGroupRequestIdentifier() {
        return groupRequestIdentifier;
    }

    public void setGroupRequestIdentifier(String groupRequestIdentifier) {
        this.groupRequestIdentifier = groupRequestIdentifier;
    }

    public String getOperationExecutionTime() {
        return operationExecutionTime;
    }

    public void setOperationExecutionTime(String operationExecutionTime) {
        this.operationExecutionTime = operationExecutionTime;
    }

    public String getOriginatingTimestamp() {
        return originatingTimestamp;
    }

    public void setOriginatingTimestamp(String originatingTimestamp) {
        this.originatingTimestamp = originatingTimestamp;
    }

    public String getResultPersistance() {
        return resultPersistance;
    }

    public void setResultPersistance(String resultPersistance) {
        this.resultPersistance = resultPersistance;
    }

    public String getRequestExpirationTimestamp() {
        return requestExpirationTimestamp;
    }

    public void setRequestExpirationTimestamp(String requestExpirationTimestamp) {
        this.requestExpirationTimestamp = requestExpirationTimestamp;
    }

    public String getResultExpirationTimestamp() {
        return resultExpirationTimestamp;
    }

    public void setResultExpirationTimestamp(String resultExpirationTimestamp) {
        this.resultExpirationTimestamp = resultExpirationTimestamp;
    }

    @Resource.ResponseType
    public Integer getResponseType() {
        return responseType;
    }

    public void setResponseType(@Resource.ResponseType Integer responseType) {
        this.responseType = responseType;
    }

    @Types.ResourceType
    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(@Types.ResourceType Integer resourceType) {
        this.resourceType = resourceType;
    }
}
