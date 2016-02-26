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
import com.interdigital.android.dougal.network.LoggingInterceptor;
import com.interdigital.android.dougal.network.request.RequestHolder;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
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
    private static HashMap<String, DougalService> oneM2MServiceMap = new HashMap<>();

    // The request id must be unique to this session.
    private static long requestId = 1L;
    private static LoggingInterceptor loggingInterceptor;

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

    public Response<ResponseHolder> create(
            String baseUrl, String path, String aeId, String userName, String password)
            throws DougalException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> call = oneM2MServiceMap.get(baseUrl).createAe(
                path, auth, aeId, resourceName, requestHolder);
        try {
            Response<ResponseHolder> response = call.execute();
            int code = getCodeFromResponse(response);
            if (code != Types.STATUS_CODE_CREATED) {
                throw new DougalException("Error code = " + code);
            }
            return response;
        } catch (IOException ioException) {
            throw new DougalException("IO Exception");
        }
    }

    public static Response<ResponseHolder> retrieve(
            String baseUrl, String path, String aeId, String userName, String password)
            throws DougalException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<ResponseHolder> call = oneM2MServiceMap.get(baseUrl).retrieveAe(path, auth, aeId);
        try {
            Response<ResponseHolder> response = call.execute();
            int code = getCodeFromResponse(response);
            if (code != Types.STATUS_CODE_OK) {
                throw new DougalException("Error code = " + code);
            }
            return response;
        } catch (IOException ioException) {
            throw new DougalException("IO Exception");
        }
    }

    // TODO Difficult because the CSE currently requires differential updates.
    public Response<ResponseHolder> update(
            String aeId, String userName, String password) throws IOException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        RequestHolder requestHolder = new RequestHolder(this);
        Call<ResponseHolder> call = oneM2MServiceMap.get(baseUrl).updateAe(
                path, auth, aeId, requestHolder);
        Response<ResponseHolder> response = call.execute();
        return response;
    }

    public static Response<Void> delete(
            String baseUrl, String path, String aeId, String userName, String password)
            throws DougalException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = oneM2MServiceMap.get(baseUrl).deleteAe(path, auth, aeId);
        try {
            Response<Void> response = call.execute();
            int code = getCodeFromResponse(response);
            if (code != Types.STATUS_CODE_DELETED) {
                throw new DougalException("Error code = " + code);
            }
            return response;
        } catch (IOException ioException) {
            throw new DougalException("IO Exception");
        }
    }

    public Response<Void> delete(String aeId, String userName, String password) throws IOException {
        maybeMakeOneM2MService(baseUrl);
        String auth = Credentials.basic(userName, password);
        Call<Void> call = oneM2MServiceMap.get(baseUrl).deleteAe(path, auth, aeId);
        Response<Void> response = call.execute();
        return response;
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

    public void setRequestBodyLogging(boolean logging) {
        if (loggingInterceptor != null) {
            loggingInterceptor.setLogRequestBody(logging);
        }
    }

    public void setResponseBodyLogging(boolean logging) {
        if (loggingInterceptor != null) {
            loggingInterceptor.setLogResponseBody(logging);
        }
    }

    // Most applications should not need to call this.
    public void free() {
        gson = null;
        oneM2MServiceMap.clear();
    }

    private static int getCodeFromResponse(Response response) {
        if (response.headers().get("X-M2M-RSC") != null) {
            return Integer.parseInt(response.headers().get("X-M2M-RSC"));
        }
        return 0;
    }

    private static void maybeMakeOneM2MService(String baseUrl) {
        if (!oneM2MServiceMap.containsKey(baseUrl)) {
            if (gson == null) {
                gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            }
            loggingInterceptor = new LoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AddHeadersInterceptor())
                            // If we have logging enabled, the unit test will fail.
                            // (Unimplemented Android framework class.)
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            oneM2MServiceMap.put(baseUrl, retrofit.create(DougalService.class));
        }
    }

    @NonNull
    private static synchronized String getUniqueRequestId() {
        return String.valueOf(requestId++);
    }
}

