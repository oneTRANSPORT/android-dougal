package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.dougal.resource.callback.RetrieveCallback;
import com.interdigital.android.dougal.resource.callback.UpdateCallback;
import com.interdigital.android.dougal.shared.FilterCriteria;

import retrofit2.Response;

public class Schedule extends AnnounceableSubordinateResource {

    @Expose
    @SerializedName("se")
    private ScheduleEntry[] scheduleEntries;

    public Schedule(@NonNull String aeId, @NonNull String resourceId,
                    @NonNull String resourceName, @NonNull String baseUrl, @NonNull String createPath,
                    @NonNull ScheduleEntry[] scheduleEntries) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_SCHEDULE, baseUrl,
                createPath);
        this.scheduleEntries = scheduleEntries;
    }

    public void create(String token) throws Exception {
        create(token, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void createNonBlocking(String token) throws Exception {
        create(token, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public void createAsync(String token, DougalCallback dougalCallback) {
        createAsync(token, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static Schedule retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                    @NonNull String retrievePath, String token)
            throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getSchedule();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath,
                                     String token, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<Schedule>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static Schedule retrieveNonBlockingPayload(@NonNull String aeId,
                                                      @NonNull String baseUrl, @NonNull String retrievePath,
                                                      String token) throws Exception {
        return ((NonBlockingRequest) retrieveBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getResource())
                .getPrimitiveContent().getSchedule();
    }

    public void update(String token) throws Exception {
        Response<ResponseHolder> response = update(token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getSchedule().getLastModifiedTime());
    }

    public void updateAsync(String token, DougalCallback dougalCallback) {
        updateAsync(token, RESPONSE_TYPE_BLOCKING_REQUEST,
                new UpdateCallback<>(this, dougalCallback));
    }

    public static UriList discover(@NonNull String aeId, @NonNull String baseUrl,
                                   @NonNull String retrievePath, String token,
                                   FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_SCHEDULE);
        }
        return discoverBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getUriList();
    }

    public static void discoverAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String token,
                                     FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_SCHEDULE);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<UriList>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public ScheduleEntry[] getScheduleEntries() {
        return scheduleEntries;
    }

    public void setScheduleEntries(ScheduleEntry[] scheduleEntries) {
        this.scheduleEntries = scheduleEntries;
    }

    // TODO    Should this be in a superclass?
    private String create(String token, @ResponseType int responseType)
            throws Exception {
        Response<ResponseHolder> response = create(token, responseType, this);
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                return null;
            default:
                return response.body().getResource().getResourceId();
        }
    }
}
