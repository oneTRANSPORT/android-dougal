package com.interdigital.android.dougal.resource;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.callback.CreateCallback;
import com.interdigital.android.dougal.resource.callback.DougalCallback;
import com.interdigital.android.dougal.resource.callback.NonBlockingIdCallback;

import retrofit2.Response;

public class Group extends AnnounceableResource {

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

    public Group(@NonNull String aeId, @NonNull String resourceId, @NonNull String resourceName,
                 @NonNull String baseUrl, @NonNull String createPath, @NonNull Integer maxNumberOfMembers,
                 @NonNull String[] memberIds) {
        super(aeId, resourceId, resourceName, Types.RESOURCE_TYPE_GROUP, baseUrl, createPath);
        this.maxNumberOfMembers = maxNumberOfMembers;
        this.memberIds = memberIds;
    }

    // TODO Finish this.
    public void create(String userName, String password) throws Exception {
        Response<ResponseHolder> response = create(userName, password,
                RESPONSE_TYPE_BLOCKING_REQUEST, this);
    }

    public void createAsync(String userName, String password, DougalCallback dougalCallback) {
        createAsync(userName, password, new CreateCallback<>(this, dougalCallback),
                RESPONSE_TYPE_BLOCKING_REQUEST);
    }

    // TODO Test.
    public Resource createNonBlocking(String userName, String password) throws Exception {
        return create(userName, password, RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH,
                this).body().getResource();
    }

    // TODO Test.
    public void createNonBlockingAsync(String userName, String password,
                                       DougalCallback dougalCallback) {
        createAsync(userName, password, new NonBlockingIdCallback<>(dougalCallback),
                RESPONSE_TYPE_NON_BLOCKING_REQUEST_SYNCH);
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
