package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;

import retrofit2.Response;

public class LocationPolicy extends AnnounceableResource {

    @Expose
    @SerializedName("los")
    @Types.LocationSource
    private Integer locationSource;
    @Expose
    @SerializedName("lou")
    private String locationUpdatePeriod;
    @Expose
    @SerializedName("lot")
    private String locationTargetID;
    @Expose
    @SerializedName("lor")
    private String locationServer;
    @Expose
    @SerializedName("loi")
    private String locationContainerID;
    @Expose
    @SerializedName("lon")
    private String locationContainerName;
    @Expose
    @SerializedName("lost")
    private String locationStatus;

    public LocationPolicy(@NonNull String aeId, @NonNull String resourceId,
                          @NonNull String resourceName, @NonNull String baseUrl, @NonNull String createPath,
                          @Types.LocationSource int locationSource) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_LOCATION_POLICY, baseUrl,
                createPath);
        this.locationSource = locationSource;
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

    public Integer getLocationSource() {
        return locationSource;
    }

    public void setLocationSource(Integer locationSource) {
        this.locationSource = locationSource;
    }

    public String getLocationUpdatePeriod() {
        return locationUpdatePeriod;
    }

    public void setLocationUpdatePeriod(String locationUpdatePeriod) {
        this.locationUpdatePeriod = locationUpdatePeriod;
    }

    public String getLocationTargetID() {
        return locationTargetID;
    }

    public void setLocationTargetID(String locationTargetID) {
        this.locationTargetID = locationTargetID;
    }

    public String getLocationServer() {
        return locationServer;
    }

    public void setLocationServer(String locationServer) {
        this.locationServer = locationServer;
    }

    public String getLocationContainerID() {
        return locationContainerID;
    }

    public void setLocationContainerID(String locationContainerID) {
        this.locationContainerID = locationContainerID;
    }

    public String getLocationContainerName() {
        return locationContainerName;
    }

    public void setLocationContainerName(String locationContainerName) {
        this.locationContainerName = locationContainerName;
    }

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }

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
