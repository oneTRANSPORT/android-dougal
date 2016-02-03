package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.SDK;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseResource {

    // Seems that this should contain no new line characters.
    private static final String CIN_HEADER =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                    + "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"http://www.onem2m.org/xml/protocols CDT-cin-v1_0_0.xsd\""
                    + " rn=\"cin\">"
                    + "<cnf>text/plain:0</cnf>"
                    + "<con>";
    private static final String CIN_FOOTER = "</con></m2m:cin>";

    @Expose
    @SerializedName("ri")
    private String resourceId;
    @Expose
    @SerializedName("rn")
    private String resourceName;
    @Expose
    @SerializedName("ty")
    private String resourceType;
    @Expose
    @SerializedName("pi")
    private String parentId;
    @Expose
    @SerializedName("ct")
    private String creationTime;
    @Expose
    @SerializedName("lt")
    private String lastModifiedTime;
    @Expose
    @SerializedName("et")
    private String expiryTime;
    @Expose
    @SerializedName("acpi")
    private String[] accessControlPolicyIds;
    @Expose
    @SerializedName("lbl")
    private String[] labels;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String[] getAccessControlPolicyIds() {
        return accessControlPolicyIds;
    }

    public void setAccessControlPolicyIds(String[] accessControlPolicyIds) {
        this.accessControlPolicyIds = accessControlPolicyIds;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    // TODO Inject request holder here?
    protected static ResponseHolder get(Context context, RI ri, boolean useHttps, String aeId) {
        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
        return SDK.getInstance().getResource(context, ri, useHttps, propertyValues);
    }

    public static ResponseHolder post(Context context, RI ri, boolean useHttps,
                                      RequestHolder requestHolder) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(requestHolder);
        return SDK.getInstance().postResource(context, ri, useHttps, requestHolder.getPropertyValues(), json);
    }

    public static ResponseHolder postCin(Context context, RI ri, boolean useHttps,
                                         RequestHolder requestHolder) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(requestHolder);
        // We must wrap the content instance JSON in an XML envelope.
        return SDK.getInstance().postResource(context, ri, useHttps, requestHolder.getPropertyValues(),
                CIN_HEADER + json + CIN_FOOTER);
    }

    public static ResponseHolder delete(Context context, RI ri, boolean useHttps, String aeId) {
        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
        return SDK.getInstance().deleteResource(context, ri, useHttps, propertyValues);
    }

    protected static Discovery discover(Context context, RI ri, boolean useHttps, String aeId) {
        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
        ResponseHolder responseHolder = SDK.getInstance().getResource(context, ri, useHttps,
                propertyValues);
        return responseHolder.getDiscovery();
    }

    protected static HashMap<String, List<String>> createOriginProperty(String origin) {
        HashMap<String, List<String>> propertyValues = new HashMap<>();
        ArrayList<String> originList = new ArrayList<>();
        originList.add(origin);
        ArrayList<String> requestIdList = new ArrayList<>();
        requestIdList.add(SDK.getInstance().getUniqueRequestId());
        propertyValues.put("X-M2M-Origin", originList);
        propertyValues.put("X-M2M-RI", requestIdList);
        return propertyValues;
    }
}

