package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DeleteCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.dougal.resource.callback.NonBlockingIdCallback;

import retrofit2.Response;

public class Group extends AnnounceableResource {

    private String aeId;
    @Expose
    @SerializedName("cr")
    private String creator;
    @Expose
    @SerializedName("mt")
    @Types.ResourceType
    private Integer memberType;
    @Expose
    @SerializedName("cnm")
    private Integer currentNumberOfMembers;
    @Expose
    @SerializedName("mnm")
    private Integer maxNumberOfMembers;
    @Expose
    @SerializedName("mid")
    private String[] memberIds;
    @Expose
    @SerializedName("macp")
    private String[] membersAccessControlPolicyIds;
    @Expose
    @SerializedName("mtv")
    private Boolean memberTypeValidated;
    @Expose
    @SerializedName("csy")
    @Types.ConsistencyStrategy
    private Integer consistencyStrategy;
    @Expose
    @SerializedName("gn")
    private String groupName;
    @Expose
    @SerializedName("fopt")
    private String fanOutPoint;

    public Group(String aeId, String resourceId, String resourceName, String parentId,
                 String expiryTime, String[] accessControlPolicyIds, String[] labels,
                 @Types.ResourceType Integer memberType, Integer maxNumberOfMembers,
                 String[] memberIds, @Types.ConsistencyStrategy Integer consistencyStrategy,
                 String groupName) {
        super(resourceId, resourceName, Types.RESOURCE_TYPE_GROUP, parentId, expiryTime,
                accessControlPolicyIds, labels);
        this.aeId = aeId;
        this.memberType = memberType;
        this.maxNumberOfMembers = maxNumberOfMembers;
        this.memberIds = memberIds;
        this.consistencyStrategy = consistencyStrategy;
        this.groupName = groupName;
    }

    public void create(String baseUrl, String path, String userName, String password)
            throws Exception {
        Response<ResponseHolder> response = create(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, this);
    }

    public void createAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(aeId, baseUrl, path, userName, password, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    // TODO Test.
    public Resource createNonBlocking(String baseUrl, String path, String userName, String password)
            throws Exception {
        return create(aeId, baseUrl, path, userName, password,
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH, this).body().getResource();
    }

    // TODO Test.
    public void createNonBlockingAsync(
            String baseUrl, String path, String userName, String password, DougalCallback dougalCallback) {
        createAsync(aeId, baseUrl, path, userName, password,
                new NonBlockingIdCallback<>(dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
    }


    public void delete(String userName, String password) throws Exception {
        delete(aeId, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public static void delete(@NonNull String aeId, @NonNull String baseUrl, @NonNull String path,
                              String userName, String password) throws Exception {
        delete(aeId, baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    public void deleteAsync(
            String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public static void deleteAsync(String aeId, String baseUrl, String path,
                                   String userName, String password, DougalCallback dougalCallback) {
        deleteAsync(aeId, baseUrl, path, userName, password, RESPONSE_TYPE_BLOCKING_REQUEST,
                new DeleteCallback(dougalCallback));
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Types.ResourceType
    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(@Types.ResourceType Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getCurrentNumberOfMembers() {
        return currentNumberOfMembers;
    }

    public void setCurrentNumberOfMembers(Integer currentNumberOfMembers) {
        this.currentNumberOfMembers = currentNumberOfMembers;
    }

    public Integer getMaxNumberOfMembers() {
        return maxNumberOfMembers;
    }

    public void setMaxNumberOfMembers(Integer maxNumberOfMembers) {
        this.maxNumberOfMembers = maxNumberOfMembers;
    }

    public String[] getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String[] memberIds) {
        this.memberIds = memberIds;
    }

    public String[] getMembersAccessControlPolicyIds() {
        return membersAccessControlPolicyIds;
    }

    public void setMembersAccessControlPolicyIds(String[] membersAccessControlPolicyIds) {
        this.membersAccessControlPolicyIds = membersAccessControlPolicyIds;
    }

    public Boolean getMemberTypeValidated() {
        return memberTypeValidated;
    }

    public void setMemberTypeValidated(Boolean memberTypeValidated) {
        this.memberTypeValidated = memberTypeValidated;
    }

    public Integer getConsistencyStrategy() {
        return consistencyStrategy;
    }

    public void setConsistencyStrategy(Integer consistencyStrategy) {
        this.consistencyStrategy = consistencyStrategy;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFanOutPoint() {
        return fanOutPoint;
    }

    public void setFanOutPoint(String fanOutPoint) {
        this.fanOutPoint = fanOutPoint;
    }
}
