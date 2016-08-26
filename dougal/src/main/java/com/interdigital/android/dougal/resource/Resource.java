package com.interdigital.android.dougal.resource;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.DougalService;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.AddHeadersInterceptor;
import com.interdigital.android.dougal.network.RewriteCompatibilityInterceptor;
import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.dougal.resource.callback.NonBlockingIdCallback;
import com.interdigital.android.dougal.shared.FilterCriteria;

import java.io.File;
import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Resource {

    // TODO Move these somewhere better.
    public static final String NO_AUTH = "HTTP 401 Not Authorized";
    public static final int NO_AUTH_CODE = 401;

    @Retention(SOURCE)
    @IntDef({RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
            RESPONSE_TYPE_NON_BLOCKING_REQUEST_ASYNCH,
            RESPONSE_TYPE_BLOCKING_REQUEST})
    public @interface ResponseType {
    }

    public static final int RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH = 1;
    public static final int RESPONSE_TYPE_NON_BLOCKING_REQUEST_ASYNCH = 2;
    public static final int RESPONSE_TYPE_BLOCKING_REQUEST = 3;

    private static final String KEY_RESPONSE_TYPE = "rt";
    private static final String CONTENT_TYPE_PREFIX = "application/json; ty=";

    // We should be able to use one Gson instance for everything.  Should be thread-safe.
    public static Gson gson;

    private static final String RESPONSE_STATUS_CODE = "X-M2M-RSC";

    // On the off-chance that we need to connect to multiple oneM2M servers with different
    // base URLs, use a map of services.
    private static HashMap<String, DougalService> dougalServiceMap = new HashMap<>();

    private static HttpLoggingInterceptor httpLoggingInterceptor;

    // Must send this with every request, so keep a copy in the resource.
    private String aeId;
    private String baseUrl;
    private String createPath;
    private String retrievePath;
    @Expose
    @SerializedName("ri")
    private String resourceId;
    @Expose
    @SerializedName("rn")
    private String resourceName;
    // This is sent in the Content-Type header, but we might get it back so delete it in the interceptor.
    @Expose
    @SerializedName("ty")
    @Types.ResourceType
    private Integer resourceType;
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
    //    @Expose  We don't know what the link attribute is.  Nodelink?
    //    @SerializedName("")
    //    Private link

    // TODO Check: make methods protected instead of public where necessary.

    public Resource(@NonNull String aeId, @NonNull String resourceId, @NonNull String resourceName,
                    @Types.ResourceType int resourceType, @NonNull String baseUrl,
                    @NonNull String createPath) {
        this.aeId = aeId;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.baseUrl = baseUrl;
        this.createPath = createPath;
        retrievePath = createPath + File.separator + resourceName;
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

    // Need to add filtering on all RUD methods.
    protected Response<ResponseHolder> create(String userName, String password,
                                              @ResponseType int responseType, Resource creator) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        String contentType = CONTENT_TYPE_PREFIX + resourceType;
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).create(aeId, createPath, auth,
                contentType, getRequestId(), responseType, requestHolder);
        Response<ResponseHolder> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_CREATED);
                Resource createdResource = response.body().getResource();
                creator.setCreationTime(createdResource.getCreationTime());
                creator.setLastModifiedTime(createdResource.getLastModifiedTime());
                creator.setParentId(createdResource.getParentId());
                creator.setResourceId(createdResource.getResourceId());
                creator.setResourceName(createdResource.getResourceName());
                retrievePath = createPath + File.separator + createdResource.getResourceName();
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
        return response;
    }

    // TODO Callbacks need to support async ACCEPTED.
    protected void createAsync(String userName, String password, Callback<ResponseHolder> callback,
                               @ResponseType int responseType) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        String contentType = CONTENT_TYPE_PREFIX + resourceType;
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).create(aeId, createPath, auth,
                contentType, getRequestId(), responseType, requestHolder);
        call.enqueue(callback);
    }

    protected static Response<ResponseHolder> retrieveBase(@NonNull String aeId,
                                                           @NonNull String baseUrl, @NonNull String retrievePath,
                                                           String userName, String password,
                                                           @ResponseType int responseType, FilterCriteria filterCriteria)
            throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = new HashMap<>();
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .retrieve(aeId, retrievePath, auth, getRequestId(), queryMap);
        Response<ResponseHolder> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_OK);
                Resource retrievedResource = response.body().getResource();
                retrievedResource.setAeId(aeId);
                retrievedResource.setBaseUrl(baseUrl);
                retrievedResource.setRetrievePath(retrievePath);
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
        return response;
    }

    protected static void retrieveBaseAsync(@NonNull String aeId, @NonNull String baseUrl,
                                            @NonNull String retrievePath, String userName, String password,
                                            @ResponseType int responseType, FilterCriteria filterCriteria,
                                            Callback<ResponseHolder> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = new HashMap<>();
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .retrieve(aeId, retrievePath, auth, getRequestId(), queryMap);
        call.enqueue(callback);
    }

    public static Resource retrieveIdNonBlocking(@NonNull String aeId, @NonNull String baseUrl,
                                                 @NonNull String retrievePath, String userName, String password,
                                                 FilterCriteria filterCriteria) throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, filterCriteria).body().getResource();
    }

    public static void retrieveIdNonBlockingAsync(@NonNull String aeId, @NonNull String baseUrl,
                                                  @NonNull String retrievePath, String userName, String password,
                                                  FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        NonBlockingIdCallback<Resource> callback = new NonBlockingIdCallback<>(dougalCallback);
        retrieveBaseAsync(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, filterCriteria, callback);
    }

    public static ResponseHolder retrievePayloadNonBlockingBase(@NonNull String aeId,
                                                                @NonNull String baseUrl, @NonNull String retrievePath,
                                                                String userName, String password)
            throws Exception {
        NonBlockingRequest nonBlockingRequest = (NonBlockingRequest) retrieveBase(aeId, baseUrl,
                retrievePath, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST, null)
                .body().getResource();
        ResponseHolder responseHolder = nonBlockingRequest.getPrimitiveContent();
        responseHolder.setStatusCode(nonBlockingRequest.getOperationResult().getResponseStatusCode());
        return responseHolder;
    }

    protected static Response<ResponseHolder> retrieveChildren(@NonNull String aeId,
                                                           @NonNull String baseUrl, @NonNull String retrievePath,
                                                           String userName, String password, @ResponseType int responseType)
            throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("rcn","6");
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .retrieve(aeId, retrievePath, auth, getRequestId(), queryMap);
        Response<ResponseHolder> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_OK);
                Resource retrievedResource = response.body().getResource();
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
        return response;
    }

    protected Response<ResponseHolder> update(String userName, String password,
                                              @ResponseType int responseType) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).update(
                aeId, retrievePath, auth, getRequestId(), queryMap, requestHolder);
        Response<ResponseHolder> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_UPDATED);
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
        return response;
    }

    protected void updateAsync(String userName, String password, @ResponseType int responseType,
                               Callback<ResponseHolder> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).update(
                aeId, retrievePath, auth, getRequestId(), queryMap, requestHolder);
        call.enqueue(callback);
    }

    // TODO Non-blocking updates.

    public void delete(String userName, String password) throws Exception {
        delete(userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void delete(@NonNull String aeId, @NonNull String baseUrl,
                              @NonNull String retrievePath, String userName, String password) throws Exception {
        delete(aeId, baseUrl, retrievePath, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteAsync(String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteAsync(@NonNull String aeId, @NonNull String baseUrl,
                                   @NonNull String retrievePath, String userName, String password,
                                   DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, new DeleteCallback(dougalCallback));
    }

    protected static void delete(@NonNull String aeId, @NonNull String baseUrl,
                                 @NonNull String retrievePath, String userName, String password,
                                 @ResponseType int responseType) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, retrievePath, auth, getRequestId(),
                queryMap);
        Response<Void> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_DELETED);
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
    }

    protected void delete(String userName, String password, @ResponseType int responseType)
            throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, retrievePath, auth, getRequestId(),
                queryMap);
        Response<Void> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_DELETED);
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
    }

    protected static void deleteAsync(@NonNull String aeId, @NonNull String baseUrl,
                                      @NonNull String retrievePath, String userName, String password,
                                      @ResponseType int responseType, Callback<Void> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, retrievePath, auth, getRequestId(),
                queryMap);
        call.enqueue(callback);
    }

    protected void deleteAsync(String userName, String password, @ResponseType int responseType,
                               Callback<Void> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, retrievePath, auth, getRequestId(),
                queryMap);
        call.enqueue(callback);
    }

    protected static Response<ResponseHolder> discoverBase(@NonNull String aeId,
                                                           @NonNull String baseUrl, @NonNull String retrievePath,
                                                           String userName, String password, @ResponseType int responseType,
                                                           FilterCriteria filterCriteria) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = new HashMap<>();
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .discover(aeId, retrievePath, auth, getRequestId(), queryMap);
        Response<ResponseHolder> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_OK);
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
        return response;
    }

    protected static void discoverAsyncBase(@NonNull String aeId, @NonNull String baseUrl,
                                            @NonNull String retrievePath, String userName, String password,
                                            @ResponseType int responseType, FilterCriteria filterCriteria,
                                            Callback<ResponseHolder> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = new HashMap<>();
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        queryMap.put(KEY_RESPONSE_TYPE, String.valueOf(responseType));
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .discover(aeId, retrievePath, auth, getRequestId(), queryMap);
        call.enqueue(callback);
    }

    public String getAeId() {
        return aeId;
    }

    public void setAeId(String aeId) {
        this.aeId = aeId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCreatePath() {
        return createPath;
    }

    public void setCreatePath(String createPath) {
        this.createPath = createPath;
    }

    public String getRetrievePath() {
        return retrievePath;
    }

    public void setRetrievePath(String retrievePath) {
        this.retrievePath = retrievePath;
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
    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(@Types.ResourceType Integer resourceType) {
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

    public void setHttpLoggingLevel(HttpLoggingInterceptor.Level level) {
        if (httpLoggingInterceptor != null) {
            httpLoggingInterceptor.setLevel(level);
        }
    }

    // Most applications should not need to call this.
    public void free() {
        gson = null;
        dougalServiceMap.clear();
    }

    public static int getCodeFromResponse(Response response) {
        String code = response.headers().get(RESPONSE_STATUS_CODE);
        if (code != null) {
            return Integer.parseInt(code);
        }
        return 0;
    }

    public static void addInterceptors(String baseUrl, Interceptor[] interceptors) {
        // Override current service if any.
        createDougalService(baseUrl, interceptors);
    }

    private static void maybeCreateDougalService(String baseUrl) {
        if (!dougalServiceMap.containsKey(baseUrl)) {
            createDougalService(baseUrl, null);
        }
    }

    private static void createDougalService(String baseUrl, Interceptor[] interceptors) {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AddHeadersInterceptor())
                .addInterceptor(new RewriteCompatibilityInterceptor());
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        OkHttpClient okHttpClient = builder
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        dougalServiceMap.put(baseUrl, retrofit.create(DougalService.class));
        Log.i("Resource", "Created service: " + baseUrl);
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
        return UUID.randomUUID().toString();
    }
}

