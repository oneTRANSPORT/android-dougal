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

    public void create(String userName, String password) throws Exception {
        create(userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void createNonBlocking(String userName, String password) throws Exception {
        create(userName, password, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }

    public void createAsync(String userName, String password, DougalCallback dougalCallback) {
        createAsync(userName, password, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static Schedule retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                    @NonNull String retrievePath, String userName, String password)
            throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getSchedule();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath,
                                     String userName, String password, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<Schedule>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static Schedule retrieveNonBlockingPayload(@NonNull String aeId,
                                                      @NonNull String baseUrl, @NonNull String retrievePath,
                                                      String userName, String password) throws Exception {
        return ((NonBlockingRequest) retrieveBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getResource())
                .getPrimitiveContent().getSchedule();
    }

    public void update(String userName, String password) throws Exception {
        Response<ResponseHolder> response = update(userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getSchedule().getLastModifiedTime());
    }

    public void updateAsync(String userName, String password, DougalCallback dougalCallback) {
        updateAsync(userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new UpdateCallback<>(this, dougalCallback));
    }

    public static Discovery discover(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String userName, String password,
                                     FilterCriteria filterCriteria)
            throws Exception {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_SCHEDULE);
        }
        return discoverBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria).body().getDiscovery();
    }

    public static void discoverAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath, String userName, String password,
                                     FilterCriteria filterCriteria, DougalCallback dougalCallback) {
        if (filterCriteria == null) {
            filterCriteria = new FilterCriteria();
        }
        if (filterCriteria.getResourceType() == null) {
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_SCHEDULE);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<Discovery>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public ScheduleEntry[] getScheduleEntries() {
        return scheduleEntries;
    }

    public void setScheduleEntries(ScheduleEntry[] scheduleEntries) {
        this.scheduleEntries = scheduleEntries;
    }

    // TODO    Should this be in a superclass?
    private String create(String userName, String password, @ResponseType int responseType)
            throws Exception {
        Response<ResponseHolder> response = create(userName, password, responseType, this);
        switch (responseType) {
            case RESPONSE_TYPE_BLOCKING_REQUEST:
                return null;
            default:
                return response.body().getResource().getResourceId();
        }
    }
}
