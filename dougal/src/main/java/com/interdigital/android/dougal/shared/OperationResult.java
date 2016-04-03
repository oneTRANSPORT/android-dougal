package com.interdigital.android.dougal.shared;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.Resource;

public class OperationResult {

    @Expose
    @SerializedName("rsc")
    @Types.StatusCode
    private int responseStatusCode;
    @Expose
    @SerializedName("rqi")
    private String requestIdentifier;
    @Expose
    @SerializedName("pc")
    private String primitiveContent; // JSON string.
    @Expose
    @SerializedName("to")
    private String to;
    @Expose
    @SerializedName("fr")
    private String from;
    @Expose
    @SerializedName("ot")
    private String originatingTimestamp;
    @Expose
    @SerializedName("rset")
    private String resultExpirationTimestamp;
    @Expose
    @SerializedName("ec")
    private String eventCategory;

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getRequestIdentifier() {
        return requestIdentifier;
    }

    public void setRequestIdentifier(String requestIdentifier) {
        this.requestIdentifier = requestIdentifier;
    }

    public ResponseHolder getPrimitiveContent() {
        return Resource.gson.fromJson(primitiveContent, ResponseHolder.class);
    }

    public void setPrimitiveContent(String primitiveContent) {
        this.primitiveContent = primitiveContent;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOriginatingTimestamp() {
        return originatingTimestamp;
    }

    public void setOriginatingTimestamp(String originatingTimestamp) {
        this.originatingTimestamp = originatingTimestamp;
    }

    public String getResultExpirationTimestamp() {
        return resultExpirationTimestamp;
    }

    public void setResultExpirationTimestamp(String resultExpirationTimestamp) {
        this.resultExpirationTimestamp = resultExpirationTimestamp;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }
}
