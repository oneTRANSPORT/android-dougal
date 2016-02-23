package com.interdigital.android.onem2msdk.resource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.Types;
import com.interdigital.android.onem2msdk.network.Ri;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;
import com.interdigital.android.onem2msdk.network2.LoggingInterceptor;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class Resource {

    private static final String AUTHORISATION_HEADER = "Authorization";

    // Gson is supposed to be thread-safe.  Keep only one instance.
    protected static Gson gson;

    // In case we ever need caching, there should only ever be one OkHttpClient instance.
    private static OkHttpClient okHttpClient;
    // The request id must be unique to this session.
    private static long requestId = 1L;

    //    @Expose
//    @SerializedName("ri")
    private String resourceId;
    //    @Expose
//    @SerializedName("rn")
    private String resourceName;
    //    @Expose // This is sent in the Content-Type header.
//    @SerializedName("ty")
    @Types.ResourceType
    private int resourceType;
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
    @Expose
    @SerializedName("st")
    private Integer stateTag = null; // Strictly an unsigned int.
    //    @Expose  We don't know what the link attribute is.
    //    @SerializedName("")
    //    Private link
    @Expose
    @SerializedName("at")
    private String announceTo;
    @Expose
    @SerializedName("aa")
    private String announcedAttribute;

    private Ri ri;

    // No network request.
    // Builder pattern would be better here?
    public Resource(String resourceId, String resourceName, @Types.ResourceType int resourceType,
                    String parentId, String creationTime, String lastModifiedTime, String expiryTime,
                    String[] accessControlPolicyIds, String[] labels) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.parentId = parentId;
        this.creationTime = creationTime;
        this.lastModifiedTime = lastModifiedTime;
        this.expiryTime = expiryTime;
        this.accessControlPolicyIds = accessControlPolicyIds;
        this.labels = labels;
    }

    // CREATE
    // If we have the full Uri of the resource to be created on the server, the request is:
    //
    //     PUT /resources/<newResourceId> HTTP/1.1
    //
    // If we want to add a new item to a collection represented by a Uri, and the server will
    // return the full Uri of the new resource, the request is:
    //
    //     POST /resources HTTP/1.1
    //
    // RETRIEVE --- Always a GET request, full Uri.
    // UPDATE --- Always a PUT request, full Uri.
    // DELETE --- Always a DELETE request, full Uri.
    //
    //
    // Unfortunately, oneM2M is not very restful:
    //
    // Create POST
    // Retrieve GET
    // Update PUT (full update) or POST (partial update)
    // Delete DELETE
    // Notify POST

    // Synchronous HTTP POST always.
    public ResponseHolder create(Ri riCreate, String aeId, String userName, String password) {
        Request request = makeCreateRequest(riCreate, aeId, userName, password);
        return execute(request);
    }

    // Synchronous HTTP GET.
    public static ResponseHolder retrieve(Ri riRetrieve, String aeId, String userName, String password) {
        Request request = makeRetrieveRequest(aeId, userName, password, riRetrieve);
        return execute(request);
    }

    public ResponseHolder update(String aeId, String userName, String password) {
        return null;
    }

    public static ResponseHolder delete(Ri ri, String aeId, String userName, String password) {
        Request request = makeDeleteRequest(aeId, userName, password, ri);
        return execute(request);
    }

    public ResponseHolder delete(String aeId, String userName, String password) {
        Request request = makeDeleteRequest(aeId, userName, password, ri);
        return execute(request);
    }

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

    @Types.ResourceType
    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(@Types.ResourceType int resourceType) {
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

    public void setStateTag(int stateTag) {
        this.stateTag = stateTag;
    }

    public int getStateTag() {
        return stateTag;
    }

    public void setAnnounceTo(String announceTo) {
        this.announceTo = announceTo;
    }

    public String getAnnounceTo() {
        return announceTo;
    }

    public void setAnnouncedAttribute(String announcedAttribute) {
        this.announcedAttribute = announcedAttribute;
    }

    public String getAnnouncedAttribute() {
        return announcedAttribute;
    }

    public void setRi(Ri ri) {
        this.ri = ri;
    }

    public Ri getRi() {
        return ri;
    }

    // TODO Inject request holder here?
//    protected static ResponseHolder create(Context context, Ri ri, boolean useHttps, String aeId,
//                                        String userName, String password) {
//        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
//        return retrieveResource(context, ri, useHttps, propertyValues, userName, password);
//    }

//    public static ResponseHolder post(Context context, Ri ri, boolean useHttps,
//                                      RequestHolder requestHolder, String userName, String password) {
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        String json = gson.toJson(requestHolder);
//        return SDK.getInstance().postResource(context, ri, useHttps, requestHolder.getPropertyValues(), json,
//                userName, password);
//    }

//    public static ResponseHolder postCin(Context context, Ri ri, boolean useHttps,
//                                         RequestHolder requestHolder, String userName, String password) {
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        String json = gson.toJson(requestHolder);
//         We must wrap the content instance JSON in an XML envelope.
//        return SDK.getInstance().postResource(context, ri, useHttps, requestHolder.getPropertyValues(),
//                CIN_HEADER + json + CIN_FOOTER, userName, password);
//    }

//    public static ResponseHolder delete(Context context, Ri ri, boolean useHttps, String aeId,
//                                        String userName, String password) {
//        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
//        return SDK.getInstance().deleteResource(context, ri, useHttps, propertyValues, userName, password);
//    }

//    protected static Discovery discover(Context context, Ri ri, boolean useHttps, String aeId,
//                                        String userName, String password) {
//        HashMap<String, List<String>> propertyValues = createOriginProperty(aeId);
//        ResponseHolder responseHolder = SDK.getInstance().retrieveResource(context, ri, useHttps,
//                propertyValues, userName, password);
//        return responseHolder.getDiscovery();
//    }

    protected static void addCommonHeaders(
            String origin, String userName, String password, Request.Builder builder) {
        if (userName != null && password != null) {
            builder.addHeader(AUTHORISATION_HEADER, Credentials.basic(userName, password));
        }
        builder.addHeader("X-M2M-Origin", origin);
        builder.addHeader("X-M2M-RI", getUniqueRequestId());
        builder.addHeader("Accept", "application/json");
    }

    protected static void initialiseGson() {
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
    }

    protected Request makeCreateRequest(Ri riCreate, String aeId, String userName, String password) {
        RequestHolder requestHolder = populateRequestHolder();
        initialiseGson();
        String json = gson.toJson(requestHolder);
        // TODO The oneM2M CSE does not like charset=utf-8.
//        String contentType = "application/json; charset=utf-8; ty=" + resourceType;
        String contentType = "application/json; ty=" + resourceType;
//        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        // Using bytes prevents OkHttp adding charset=utf-8.
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json.getBytes());
        Request.Builder builder = new Request.Builder().url(riCreate.createUrl()).post(body);
        addCommonHeaders(aeId, userName, password, builder);
        builder.addHeader("Content-Type", contentType);
        if (!TextUtils.isEmpty(resourceName)) {
            builder.addHeader("X-M2M-NM", resourceName);
        }
        return builder.build();
    }

    private static Request makeRetrieveRequest(
            String aeId, String userName, String password, Ri ri) {
        Request.Builder builder = new Request.Builder().url(ri.createUrl());
        addCommonHeaders(aeId, userName, password, builder);
        return builder.build();
    }

    private static Request makeDeleteRequest(String aeId, String userName, String password, Ri ri) {
        Request.Builder builder = new Request.Builder().url(ri.createUrl()).delete();
        addCommonHeaders(aeId, userName, password, builder);
        return builder.build();
    }

    @NonNull
    private RequestHolder populateRequestHolder() {
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setResource(this);
        return requestHolder;
    }

    @Nullable
    private static ResponseHolder execute(Request request) {
        try {
            Response response = getOkHttpClient().newCall(request).execute();
            int statusCode = response.code();
            String text = response.body().string();
            initialiseGson();
            ResponseHolder responseHolder = gson.fromJson(text, ResponseHolder.class);
            if (responseHolder == null) {
                responseHolder = new ResponseHolder();
                responseHolder.setBody(text);
            }
            responseHolder.setStatusCode(statusCode);
            responseHolder.setHeaders(request.headers());
            return responseHolder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
//            okHttpClient = new OkHttpClient.Builder()
//                    .addNetworkInterceptor(new LoggingInterceptor()).build();
//            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    @NonNull
    private static synchronized String getUniqueRequestId() {
//        return "PaulsOwnRequest4";
        return String.valueOf(requestId++);
    }
}

