package com.interdigital.android.dougal.resource;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

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
import com.interdigital.android.dougal.shared.FilterCriteria;

import java.io.IOException;
import java.lang.annotation.Retention;
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

import static java.lang.annotation.RetentionPolicy.SOURCE;

public abstract class Resource {

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

    private static final String CONTENT_TYPE_PREFIX = "application/json; ty=";

    // We should be able to use one Gson instance for everything.  Should be thread-safe.
    public static Gson gson;

    private static final String RESPONSE_STATUS_CODE = "X-M2M-RSC";

    // On the off-chance that we need to connect to multiple oneM2M servers with different
    // base URLs, use a map of services.
    private static HashMap<String, DougalService> dougalServiceMap = new HashMap<>();

    // The request id must be unique to this session.
    private static long requestId = 1L;
    private static HttpLoggingInterceptor httpLoggingInterceptor;

    // The CSE doesn't like receiving the ri or rn attributes in a create request.
    @Expose
    @SerializedName("ri")
    private String resourceId;
    @Expose
    @SerializedName("rn")
    private String resourceName;
    // This is sent in the Content-Type header.
    //    @Expose
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

    // TODO Make methods protected instead of public.

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

    // TODO Need to pass responseType.
    // Need to add filtering on all RUD methods.
    protected Response<ResponseHolder> create(@NonNull String aeId, @NonNull String baseUrl,
                                           @NonNull String path, String userName, String password,
                                           @ResponseType int responseType) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        String contentType = CONTENT_TYPE_PREFIX + resourceType;
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).create(aeId, path, auth,
                resourceName, contentType, getRequestId(), responseType, requestHolder);
        Response<ResponseHolder> response = call.execute();
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                checkStatusCodes(response, Types.STATUS_CODE_CREATED);
                break;
            default:
                checkStatusCodes(response, Types.STATUS_CODE_ACCEPTED);
                break;
        }
        return response;
    }

    // TODO Callbacks need to support async ACCEPTED.
    protected void createAsync(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                            String userName, String password, Callback<ResponseHolder> callback,
                            @ResponseType int responseType) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        String contentType = CONTENT_TYPE_PREFIX + resourceType;
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).create(aeId, path, auth,
                resourceName, contentType, getRequestId(), responseType, requestHolder);
        call.enqueue(callback);
    }

    protected static Response<ResponseHolder> retrieveBase(@NonNull String aeId,
                                                        @NonNull String baseUrl, @NonNull String path,
                                                        String userName, String password,
                                                        @ResponseType int responseType, FilterCriteria filterCriteria)
            throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = null;
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .retrieve(aeId, path, auth, getRequestId(), responseType, queryMap);
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

    protected static void retrieveAsyncBase(@NonNull String aeId, @NonNull String baseUrl,
                                         @NonNull String path, String userName, String password,
                                         @ResponseType int responseType,
                                         Callback<ResponseHolder> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .retrieve(aeId, path, auth, getRequestId(), responseType, null);
        call.enqueue(callback);
    }

    // TODO Difficult because the CSE currently requires differential updates.
    protected Response<ResponseHolder> update(
            @NonNull String aeId, String userName, String password,
            @ResponseType int responseType) throws IOException {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).update(
                aeId, path, auth, getRequestId(), responseType, null, requestHolder);
        Response<ResponseHolder> response = call.execute();
        // TODO Decide what to do here.
        return response;
    }

    protected void updateAsync(@NonNull String aeId, String userName, String password,
                            @ResponseType int responseType, Callback<ResponseHolder> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl).update(
                aeId, path, auth, getRequestId(), responseType, null, requestHolder);
        call.enqueue(callback);
    }

    protected static void delete(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                              String userName, String password, @ResponseType int responseType)
            throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, path, auth, getRequestId(),
                responseType, null);
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

    protected void delete(@NonNull String aeId, String userName, String password,
                       @ResponseType int responseType) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, path, auth, getRequestId(),
                responseType, null);
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

    protected static void deleteAsync(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                                   String userName, String password,
                                   @ResponseType int responseType, Callback<Void> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, path, auth, getRequestId(),
                responseType, null);
        call.enqueue(callback);
    }

    protected void deleteAsync(@NonNull String aeId, String userName, String password,
                            @ResponseType int responseType, Callback<Void> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = dougalServiceMap.get(baseUrl).delete(aeId, path, auth, getRequestId(),
                responseType, null);
        call.enqueue(callback);
    }

    protected static Response<ResponseHolder> discoverBase(@NonNull String aeId,
                                                        @NonNull String baseUrl, @NonNull String path,
                                                        String userName, String password, @ResponseType int responseType,
                                                        FilterCriteria filterCriteria) throws Exception {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = null;
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .discover(aeId, path, auth, getRequestId(), responseType, queryMap);
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
                                         @NonNull String path, String userName, String password,
                                         @ResponseType int responseType, FilterCriteria filterCriteria,
                                         Callback<ResponseHolder> callback) {
        maybeCreateDougalService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Map<String, String> queryMap = null;
        if (filterCriteria != null) {
            queryMap = filterCriteria.getQueryMap();
        }
        Call<ResponseHolder> call = dougalServiceMap.get(baseUrl)
                .discover(aeId, path, auth, getRequestId(), responseType, queryMap);
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
        dougalServiceMap.clear();
    }

    public static int getCodeFromResponse(Response response) {
        String code = response.headers().get(RESPONSE_STATUS_CODE);
        if (code != null) {
            return Integer.parseInt(code);
        }
        return 0;
    }

    private static void maybeCreateDougalService(String baseUrl) {
        if (!dougalServiceMap.containsKey(baseUrl)) {
            httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AddHeadersInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new RewriteCompatibilityInterceptor())
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

