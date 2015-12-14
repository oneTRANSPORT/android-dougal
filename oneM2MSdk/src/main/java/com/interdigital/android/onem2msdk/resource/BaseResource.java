package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.SDK;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseResource {

    @SerializedName("ri")
    private String resourceId;
    @SerializedName("rn")
    private String resourceName;
    @SerializedName("ty")
    private String resourceType;
    @SerializedName("pi")
    private String parentId;
    @SerializedName("ct")
    private String creationTime;
    @SerializedName("lt")
    private String lastModifiedTime;
    @SerializedName("et")
    private String expiryTime;
    @SerializedName("acpi")
    private String[] accessControlPolicyIds;
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
    protected static ResponseHolder get(Context context, RI ri, String aeId) {
        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
        return SDK.getInstance().getResource(context, ri, propertyValues);
    }

    public static ResponseHolder post(Context context, RI ri, RequestHolder requestHolder) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(requestHolder);
        return SDK.getInstance().postResource(context, ri, requestHolder.getPropertyValues(), json);
    }

    public static ResponseHolder delete(Context context, RI ri, String aeId) {
        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
        return SDK.getInstance().deleteResource(context, ri, propertyValues);
    }

    protected static Discovery discover(Context context, RI ri, String aeId) {
        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
        ResponseHolder responseHolder = SDK.getInstance().getResource(context, ri, propertyValues);
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

