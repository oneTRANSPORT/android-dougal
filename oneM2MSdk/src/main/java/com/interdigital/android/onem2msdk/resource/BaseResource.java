package com.interdigital.android.onem2msdk.resource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.Types;
import com.interdigital.android.onem2msdk.network.Ri;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class BaseResource {

    // TODO Remove this.
    // Seems that this should contain no new line characters.
//    private static final String CIN_HEADER =
//            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
//                    + "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\""
//                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
//                    + " xsi:schemaLocation=\"http://www.onem2m.org/xml/protocols CDT-cin-v1_0_0.xsd\""
//                    + " rn=\"cin\">"
//                    + "<cnf>text/plain:0</cnf>"
//                    + "<con>";
//    private static final String CIN_FOOTER = "</con></m2m:cin>";
    private static final String AUTHORISATION_HEADER = "Authorization";

    // Gson is supposed to be thread-safe.  Keep only one instance.
    protected static Gson gson;

    // In case we ever need caching, there should only ever be one OkHttpClient instance.
    private static OkHttpClient okHttpClient;
    // The request id must be unique to this session.
    private static long requestId = 1L;

    @Expose
    @SerializedName("ri")
    private String resourceId;
    @Expose
    @SerializedName("rn")
    private String resourceName;
    @Expose
    @SerializedName("ty")
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
    private int stateTag; // Strictly an unsigned int.
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
    public BaseResource(String resourceId, String resourceName, @Types.ResourceType int resourceType,
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

    // Synchronous HTTP PUT always?
    public ResponseHolder create(String userName, String password) {
        Request request = makeCreateRequest(userName, password);
        return execute(request);
    }

    // Synchronous HTTP GET.
    public static ResponseHolder retrieve(Ri ri, String origin, String userName, String password) {
        String url = ri.createUrl();
        Request request = makeRetrieveRequest(origin, userName, password, url);
        return execute(request);
    }

    public ResponseHolder update() {
        return null;
    }

    public ResponseHolder delete() {
        return null;
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

    protected Request makeCreateRequest(String userName, String password) {
        RequestHolder requestHolder = new RequestHolder();
        String origin = null;
        switch (resourceType) {
            case Types.RESOURCE_TYPE_APPLICATION_ENTITY:
                requestHolder.setApplicationEntity((ApplicationEntity) this);
                origin = ((ApplicationEntity) this).getId();
                break;
        }
        initialiseGson();
        String json = gson.toJson(requestHolder);
        String contentType = "application/json;ty=" + resourceType;
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        Request.Builder builder = new Request.Builder().url(ri.createUrl()).post(body);
        addCommonHeaders(origin, userName, password, builder);
        builder.addHeader("Content-Type", contentType);
        return builder.build();
    }

    protected static void initialiseGson() {
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
    }

//    protected Request makeCreateRequest() {
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/vnd.onem2m-res+json"), str);
//        Request.Builder builder = new Request.Builder().url(url).put(body);
//        addCommonHeaders(origin, userName, password, builder);
//        return builder.build();
//    }

    private static Request makeRetrieveRequest(
            String origin, String userName, String password, String url) {
        Request.Builder builder = new Request.Builder().url(url);
        addCommonHeaders(origin, userName, password, builder);
        return builder.build();
    }

    private Request makeDeleteRequest(String origin, String userName, String password, String url) {
        Request.Builder builder = new Request.Builder().url(url).delete();
        addCommonHeaders(origin, userName, password, builder);
        return builder.build();
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
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    @NonNull
    private static synchronized String getUniqueRequestId() {
        return String.valueOf(requestId++);
    }
}

