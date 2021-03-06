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

public class Subscription extends RegularResource {

    @Expose
    @SerializedName("enc")
    private EventNotificationCriteria eventNotificationCriteria;
    @Expose
    @SerializedName("exc")
    private Integer expirationCounter; // Unsigned int.
    @Expose
    @SerializedName("nu")
    private String[] notificationUris;
    @Expose
    @SerializedName("gpi")
    private String groupId;
    @Expose
    @SerializedName("nfu")
    private String notificationForwardingUri;
    @Expose
    @SerializedName("bn")
    private BatchNotify batchNotify;
    @Expose
    @SerializedName("rl")
    private RateLimit rateLimit;
    @Expose
    @SerializedName("psn")
    private Integer preSubscriptionNotify; // Unsigned int.
    @Expose
    @SerializedName("pn")
    @Types.PendingNotification
    private Integer pendingNotification;
    @Expose
    @SerializedName("nsp")
    private Integer notificationStoragePriority;
    @Expose
    @SerializedName("ln")
    private Boolean latestNotify;
    @Expose
    @SerializedName("nct")
    @Types.NotificationContentType
    private Integer notificationContentType;
    @Expose
    @SerializedName("nec")
    private Integer notificationEventCat; // An enum with user-defined values?
    @Expose
    @SerializedName("cr")
    private String creator;
    @Expose
    @SerializedName("su")
    private String subscriberUri;

    public Subscription(@NonNull String aeId, @NonNull String resourceId,
                        @NonNull String resourceName, @NonNull String baseUrl, @NonNull String createPath) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_SUBSCRIPTION, baseUrl,
                createPath);
    }

    // TODO    Superclass?
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

    public static Subscription retrieve(@NonNull String aeId, @NonNull String baseUrl,
                                        @NonNull String retrievePath, String token)
            throws Exception {
        return retrieveBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getSubscription();
    }

    public static void retrieveAsync(@NonNull String aeId, @NonNull String baseUrl,
                                     @NonNull String retrievePath,
                                     String token, DougalCallback dougalCallback) {
        retrieveBaseAsync(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null,
                new RetrieveCallback<Subscription>(aeId, baseUrl, retrievePath, dougalCallback));
    }

    public static Subscription retrieveNonBlockingPayload(@NonNull String aeId,
                                                          @NonNull String baseUrl, @NonNull String retrievePath,
                                                          String token) throws Exception {
        return ((NonBlockingRequest) retrieveBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, null).body().getResource())
                .getPrimitiveContent().getSubscription();
    }

    public void update(String token) throws Exception {
        Response<ResponseHolder> response = update(token,
                RESPONSE_TYPE_BLOCKING_REQUEST);
        setLastModifiedTime(response.body().getSubscription().getLastModifiedTime());
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
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_SUBSCRIPTION);
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
            filterCriteria.putResourceType(Types.RESOURCE_TYPE_SUBSCRIPTION);
        }
        discoverAsyncBase(aeId, baseUrl, retrievePath, token,
                RESPONSE_TYPE_BLOCKING_REQUEST, filterCriteria,
                new RetrieveCallback<UriList>(aeId, baseUrl, retrievePath, dougalCallback));
    }


    public EventNotificationCriteria getEventNotificationCriteria() {
        return eventNotificationCriteria;
    }

    public void setEventNotificationCriteria(EventNotificationCriteria eventNotificationCriteria) {
        this.eventNotificationCriteria = eventNotificationCriteria;
    }

    public Integer getExpirationCounter() {
        return expirationCounter;
    }

    public void setExpirationCounter(Integer expirationCounter) {
        this.expirationCounter = expirationCounter;
    }

    public String[] getNotificationUris() {
        return notificationUris;
    }

    public void setNotificationUris(String[] notificationUris) {
        this.notificationUris = notificationUris;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNotificationForwardingUri() {
        return notificationForwardingUri;
    }

    public void setNotificationForwardingUri(String notificationForwardingUri) {
        this.notificationForwardingUri = notificationForwardingUri;
    }

    public BatchNotify getBatchNotify() {
        return batchNotify;
    }

    public void setBatchNotify(BatchNotify batchNotify) {
        this.batchNotify = batchNotify;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(RateLimit rateLimit) {
        this.rateLimit = rateLimit;
    }

    public Integer getPreSubscriptionNotify() {
        return preSubscriptionNotify;
    }

    public void setPreSubscriptionNotify(Integer preSubscriptionNotify) {
        this.preSubscriptionNotify = preSubscriptionNotify;
    }

    public Integer getPendingNotification() {
        return pendingNotification;
    }

    public void setPendingNotification(Integer pendingNotification) {
        this.pendingNotification = pendingNotification;
    }

    public Integer getNotificationStoragePriority() {
        return notificationStoragePriority;
    }

    public void setNotificationStoragePriority(Integer notificationStoragePriority) {
        this.notificationStoragePriority = notificationStoragePriority;
    }

    public Boolean getLatestNotify() {
        return latestNotify;
    }

    public void setLatestNotify(Boolean latestNotify) {
        this.latestNotify = latestNotify;
    }

    public Integer getNotificationContentType() {
        return notificationContentType;
    }

    public void setNotificationContentType(Integer notificationContentType) {
        this.notificationContentType = notificationContentType;
    }

    public Integer getNotificationEventCat() {
        return notificationEventCat;
    }

    public void setNotificationEventCat(Integer notificationEventCat) {
        this.notificationEventCat = notificationEventCat;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubscriberUri() {
        return subscriberUri;
    }

    public void setSubscriberUri(String subscriberUri) {
        this.subscriberUri = subscriberUri;
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
