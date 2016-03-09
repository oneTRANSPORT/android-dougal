package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.DougalService;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.AddHeadersInterceptor;
import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.shared.FilterCriteria;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Resource {

    public static final String NO_AUTH = "HTTP 401 Not Authorized";
    public static final int NO_AUTH_CODE = 401;

    private static final String CONTENT_TYPE_PREFIX = "application/json; ty=";

    // We should be able to use one Gson instance for everything.  Should be thread-safe.
    private static Gson gson;
    // On the off-chance that we need to connect to multiple oneM2M servers with different
    // base URLs.
    private static HashMap<String, DougalService> oneM2MServiceMapJson = new HashMap<>();
    private static HashMap<String, DougalService> oneM2MServiceMapPlain = new HashMap<>();

    // The request id must be unique to this session.
    private static long requestId = 1L;
    private static HttpLoggingInterceptor httpLoggingInterceptor;

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
    @SerializedName("lbl")
    private String[] labels;
    //    @Expose  We don't know what the link attribute is.
    //    @SerializedName("")
    //    Private link

    private String baseUrl;
    private String path;

    public Resource(String resourceId, String resourceName, @Types.ResourceType int resourceType,
                    String parentId, String[] labels) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.parentId = parentId;
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

    public Response<ResponseHolder> create(@NonNull String aeId, @NonNull String baseUrl,
                                           @NonNull String path, String userName, String password) throws Exception {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        String contentType = CONTENT_TYPE_PREFIX + resourceType;
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl).create(
                aeId, path, auth, resourceName, contentType, getRequestId(), requestHolder);
        Response<ResponseHolder> response = call.execute();
        checkStatusCodes(response, Types.STATUS_CODE_CREATED);
        return response;
    }

    public void createAsync(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                            String userName, String password, Callback<ResponseHolder> callback) {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        String contentType = CONTENT_TYPE_PREFIX + resourceType;
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl).create(
                aeId, path, auth, resourceName, contentType, getRequestId(), requestHolder);
        call.enqueue(callback);
    }

    public static Response<ResponseHolder> retrieveBase(@NonNull String aeId,
                                                        @NonNull String baseUrl, @NonNull String path,
                                                        String userName, String password, FilterCriteria filterCriteria)
            throws Exception {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = null;
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl)
                .retrieve(aeId, path, auth, getRequestId(), queryMap);
        Response<ResponseHolder> response = call.execute();
        checkStatusCodes(response, Types.STATUS_CODE_OK);
        return response;
    }

    public static void retrieveAsyncBase(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String path, String userName, String password,
                                         Callback<ResponseHolder> callback) {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl)
                .retrieve(aeId, path, auth, getRequestId(), null);
        call.enqueue(callback);
    }

    // TODO Difficult because the CSE currently requires differential updates.
    public Response<ResponseHolder> update(
            @NonNull String aeId, String userName, String password) throws IOException {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl).update(
                aeId, path, auth, getRequestId(), requestHolder, null);
        Response<ResponseHolder> response = call.execute();
        // TODO Decide what to do here.
        return response;
    }

    public void updateAsync(
            @NonNull String aeId, String userName, String password, Callback<ResponseHolder> callback) {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl).update(
                aeId, path, auth, getRequestId(), requestHolder, null);
        call.enqueue(callback);
    }

    public static void delete(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                              String userName, String password) throws Exception {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = oneM2MServiceMapJson.get(baseUrl).delete(aeId, path, auth, getRequestId(), null);
        Response<Void> response = call.execute();
        checkStatusCodes(response, Types.STATUS_CODE_DELETED);
    }

    public void delete(@NonNull String aeId, String userName, String password)
            throws Exception {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = oneM2MServiceMapJson.get(baseUrl).delete(aeId, path, auth, getRequestId(), null);
        Response<Void> response = call.execute();
        checkStatusCodes(response, Types.STATUS_CODE_DELETED);
    }

    public static void deleteAsync(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                                   String userName, String password, Callback<Void> callback) {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = oneM2MServiceMapJson.get(baseUrl).delete(aeId, path, auth, getRequestId(), null);
        call.enqueue(callback);
    }

    public void deleteAsync(
            @NonNull String aeId, String userName, String password, Callback<Void> callback) {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = oneM2MServiceMapJson.get(baseUrl).delete(aeId, path, auth, getRequestId(), null);
        call.enqueue(callback);
    }

    public static Response<ResponseHolder> discoverBase(@NonNull String aeId,
                                                        @NonNull String baseUrl, @NonNull String path,
                                                        FilterCriteria filterCriteria, String userName, String password)
            throws Exception {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = null;
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl)
                .discover(aeId, path, auth, getRequestId(), queryMap);
        Response<ResponseHolder> response = call.execute();
        checkStatusCodes(response, Types.STATUS_CODE_OK);
        return response;
    }

    public static void discoverAsyncBase(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String path, FilterCriteria filterCriteria,
                                         String userName, String password, Callback<ResponseHolder> callback) {
        maybeMakeOneM2MService(baseUrl, oneM2MServiceMapJson);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = null;
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        Call<ResponseHolder> call = oneM2MServiceMapJson.get(baseUrl)
                .discover(aeId, path, auth, getRequestId(), queryMap);
        call.enqueue(callback);
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

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
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

    public void setHttpLoggingLevel(HttpLoggingInterceptor.Level level) {
        if (httpLoggingInterceptor != null) {
            httpLoggingInterceptor.setLevel(level);
        }
    }

    // Most applications should not need to call this.
    public void free() {
        gson = null;
        oneM2MServiceMapJson.clear();
    }

    public static int getCodeFromResponse(Response response) {
        if (response.headers().get("X-M2M-RSC") != null) {
            return Integer.parseInt(response.headers().get("X-M2M-RSC"));
        }
        return 0;
    }

    private static void maybeMakeOneM2MService(String baseUrl,
                                               HashMap<String, DougalService> serviceMap) {
        if (!serviceMap.containsKey(baseUrl)) {
            httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AddHeadersInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
                    .build();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient);
            if (serviceMap == oneM2MServiceMapJson) {
                if (gson == null) {
                    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                }
                builder.addConverterFactory(GsonConverterFactory.create(gson));
            }
            serviceMap.put(baseUrl, builder.build().create(DougalService.class));
        }
    }

    private static void checkStatusCodes(Response<?> response,
                                         @Types.StatusCode int successCode) throws DougalException {
        int httpStatusCode = response.code();
        if (httpStatusCode == NO_AUTH_CODE) {
            throw new DougalException(NO_AUTH);
        }
        @Types.StatusCode
        int code = getCodeFromResponse(response);
        if (code != successCode) {
            throw new DougalException(code);
        }
    }

    @NonNull
    private static synchronized String getRequestId() {
        return String.valueOf(requestId++);
    }
}

