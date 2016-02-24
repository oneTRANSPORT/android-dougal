package com.interdigital.android.onem2msdk.resource;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.OneM2MService;
import com.interdigital.android.onem2msdk.Types;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;
import com.interdigital.android.onem2msdk.network2.AddHeadersInterceptor;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Resource {

    private static final String AUTHORISATION_HEADER = "Authorization";

    // We should be able to use one Gson instance for everything.  Should be thread-safe.
    private static Gson gson;
    // On the off-chance that we need to connect to multiple oneM2M servers with different
    // base URLs.
    private static HashMap<String, OneM2MService> oneM2MServiceMap = new HashMap<>();

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

    private String baseUrl;
    private String path;

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
    public Response<ResponseHolder> create(
            String baseUrl, String path, String aeId, String userName, String password) throws IOException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> responseHolder = oneM2MServiceMap.get(baseUrl).createAe(
                path, auth, aeId, resourceName, requestHolder);
        Response<ResponseHolder> response = responseHolder.execute();
        return response;
    }

    // Synchronous HTTP GET.
    public static Response<ResponseHolder> retrieve(
            String baseUrl, String path, String aeId, String userName, String password) throws IOException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<ResponseHolder> responseHolder = oneM2MServiceMap.get(baseUrl).retrieveAe(
                path, auth, aeId);
        Response<ResponseHolder> response = responseHolder.execute();
        return response;
    }

    public ResponseHolder update(String aeId, String userName, String password) {
        return null;
    }

    public static ResponseHolder delete(
            String baseUrl, String path, String aeId, String userName, String password) {
//        Request request = makeDeleteRequest(aeId, userName, password, uri);
        return null;//  return execute(request);
    }

    public ResponseHolder delete(String aeId, String userName, String password) {
//        Request request = makeDeleteRequest(aeId, userName, password, uri);
        return null;//  return execute(request);
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

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    // Most applications should not need to call this.
    public void free() {
        gson = null;
        oneM2MServiceMap.clear();
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

//    protected Request makeCreateRequest(Uri uriCreate, String aeId, String userName, String password) {
//        RequestHolder requestHolder = populateRequestHolder();
//        initialiseGson();
//        String json = gson.toJson(requestHolder);
//        // TODO The oneM2M CSE does not like charset=utf-8.
////        String contentType = "application/json; charset=utf-8; ty=" + resourceType;
//        String contentType = "application/json; ty=" + resourceType;
////        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
//        // Using bytes prevents OkHttp adding charset=utf-8.
//        RequestBody body = RequestBody.create(MediaType.parse(contentType), json.getBytes());
//        Request.Builder builder = new Request.Builder().url(uriCreate.toString()).post(body);
//        addCommonHeaders(aeId, userName, password, builder);
//        builder.addHeader("Content-Type", contentType);
//        if (!TextUtils.isEmpty(resourceName)) {
//            builder.addHeader("X-M2M-NM", resourceName);
//        }
//        return builder.build();
//    }

    private static void maybeMakeOneM2MService(String baseUrl) {
        if (!oneM2MServiceMap.containsKey(baseUrl)) {
            if (gson == null) {
                gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            }
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AddHeadersInterceptor())
                            // If we have logging, the unit test will fail.
                            // (Unimplemented Android framework class.)
//                    .addInterceptor(new LoggingInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            oneM2MServiceMap.put(baseUrl, retrofit.create(OneM2MService.class));
        }
    }

    @NonNull
    private RequestHolder populateRequestHolder() {
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setResource(this);
        return requestHolder;
    }

//    @Nullable
//    private static ResponseHolder execute(Request request) {
//        try {
//            Response response = getOkHttpClient().newCall(request).execute();
//            int statusCode = response.code();
//            String text = response.body().string();
//            initialiseGson();
//            ResponseHolder responseHolder = gson.fromJson(text, ResponseHolder.class);
//            if (responseHolder == null) {
//                responseHolder = new ResponseHolder();
//                responseHolder.setBody(text);
//            }
//            responseHolder.setStatusCode(statusCode);
//            responseHolder.setHeaders(request.headers());
//            return responseHolder;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @NonNull
    private static synchronized String getUniqueRequestId() {
//        return "PaulsOwnRequest4";
        return String.valueOf(requestId++);
    }
}

