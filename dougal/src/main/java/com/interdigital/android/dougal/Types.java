package com.interdigital.android.dougal;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Types {

    @Retention(SOURCE)
    @IntDef({RESOURCE_TYPE_DISCOVERY, RESOURCE_TYPE_ACCESS_CONTROL_POLICY,
            RESOURCE_TYPE_APPLICATION_ENTITY, RESOURCE_TYPE_CONTAINER,
            RESOURCE_TYPE_CONTENT_INSTANCE, RESOURCE_TYPE_CSE_BASE,
            RESOURCE_TYPE_DELIVERY, RESOURCE_TYPE_EVENT_CONFIG,
            RESOURCE_TYPE_EXEC_INSTANCE, RESOURCE_TYPE_GROUP,
            RESOURCE_TYPE_LOCATION_POLICY,
            RESOURCE_TYPE_M2M_SERVICE_SUBSCRIPTION_PROFILE,
            RESOURCE_TYPE_MGMT_CMD, RESOURCE_TYPE_MGMT_OBJ,
            RESOURCE_TYPE_NODE, RESOURCE_TYPE_POLLING_CHANNEL,
            RESOURCE_TYPE_REMOTE_CSE, RESOURCE_TYPE_REQUEST,
            RESOURCE_TYPE_SCHEDULE, RESOURCE_TYPE_SERVICE_SUBSCRIBED_NODE,
            RESOURCE_TYPE_SERVICE_SUBSCRIBED_APP_RULE,
            RESOURCE_TYPE_STATS_COLLECT, RESOURCE_TYPE_STATS_CONFIG,
            RESOURCE_TYPE_SUBSCRIPTION, RESOURCE_TYPE_ACCESS_CONTROL_POLICY_ANNC,
            RESOURCE_TYPE_AE_ANNC, RESOURCE_TYPE_CONTAINER_ANNC,
            RESOURCE_TYPE_CONTENT_INSTANCE_ANNC, RESOURCE_TYPE_GROUP_ANNC,
            RESOURCE_TYPE_LOCATION_POLICY_ANNC, RESOURCE_TYPE_MGMT_OBJ_ANNC,
            RESOURCE_TYPE_NODE_ANNC, RESOURCE_TYPE_REMOTE_CSE_ANNC,
            RESOURCE_TYPE_SCHEDULE_ANNC
    })
    public @interface ResourceType {
    }

    public static final int RESOURCE_TYPE_DISCOVERY = -1; // Made up.  Should change spec.
    public static final int RESOURCE_TYPE_ACCESS_CONTROL_POLICY = 1;
    public static final int RESOURCE_TYPE_APPLICATION_ENTITY = 2;
    public static final int RESOURCE_TYPE_CONTAINER = 3;
    public static final int RESOURCE_TYPE_CONTENT_INSTANCE = 4;
    public static final int RESOURCE_TYPE_CSE_BASE = 5;
    public static final int RESOURCE_TYPE_DELIVERY = 6;
    public static final int RESOURCE_TYPE_EVENT_CONFIG = 7;
    public static final int RESOURCE_TYPE_EXEC_INSTANCE = 8;
    public static final int RESOURCE_TYPE_GROUP = 9;
    public static final int RESOURCE_TYPE_LOCATION_POLICY = 10;
    public static final int RESOURCE_TYPE_M2M_SERVICE_SUBSCRIPTION_PROFILE = 11;
    public static final int RESOURCE_TYPE_MGMT_CMD = 12;
    public static final int RESOURCE_TYPE_MGMT_OBJ = 13;
    public static final int RESOURCE_TYPE_NODE = 14;
    public static final int RESOURCE_TYPE_POLLING_CHANNEL = 15;
    public static final int RESOURCE_TYPE_REMOTE_CSE = 16;
    public static final int RESOURCE_TYPE_REQUEST = 17;
    public static final int RESOURCE_TYPE_SCHEDULE = 18;
    public static final int RESOURCE_TYPE_SERVICE_SUBSCRIBED_APP_RULE = 19;
    public static final int RESOURCE_TYPE_SERVICE_SUBSCRIBED_NODE = 20;
    public static final int RESOURCE_TYPE_STATS_COLLECT = 21;
    public static final int RESOURCE_TYPE_STATS_CONFIG = 22;
    public static final int RESOURCE_TYPE_SUBSCRIPTION = 23;
    // Needed for group member type.
    public static final int RESOURCE_TYPE_MIXED = 24;
    public static final int RESOURCE_TYPE_ACCESS_CONTROL_POLICY_ANNC = 10001;
    public static final int RESOURCE_TYPE_AE_ANNC = 10002;
    public static final int RESOURCE_TYPE_CONTAINER_ANNC = 10003;
    public static final int RESOURCE_TYPE_CONTENT_INSTANCE_ANNC = 10004;
    public static final int RESOURCE_TYPE_GROUP_ANNC = 10009;
    public static final int RESOURCE_TYPE_LOCATION_POLICY_ANNC = 10010;
    public static final int RESOURCE_TYPE_MGMT_OBJ_ANNC = 10013;
    public static final int RESOURCE_TYPE_NODE_ANNC = 10014;
    public static final int RESOURCE_TYPE_REMOTE_CSE_ANNC = 10016;
    public static final int RESOURCE_TYPE_SCHEDULE_ANNC = 10018;

    @Retention(SOURCE)
    @IntDef({STATUS_CODE_ACCEPTED, STATUS_CODE_OK, STATUS_CODE_CREATED,
            STATUS_CODE_DELETED, STATUS_CODE_UPDATED,
            STATUS_CODE_BAD_REQUEST, STATUS_CODE_NOT_FOUND,
            STATUS_CODE_OPERATION_NOT_ALLOWED, STATUS_CODE_REQUEST_TIMEOUT,
            STATUS_CODE_SUBSCRIPTION_CREATOR_HAS_NO_PRIVILEGE,
            STATUS_CODE_CONTENTS_UNACCEPTABLE, STATUS_CODE_ACCESS_DENIED,
            STATUS_CODE_GROUP_REQUEST_IDENTIFIER_EXISTS, STATUS_CODE_CONFLICT,
            STATUS_CODE_INTERNAL_SERVER_ERROR, STATUS_CODE_NOT_IMPLEMENTED,
            STATUS_CODE_TARGET_NOT_REACHABLE, STATUS_CODE_NO_PRIVILEGE,
            STATUS_CODE_ALREADY_EXISTS, STATUS_CODE_TARGET_NOT_SUBSCRIBABLE,
            STATUS_CODE_SUBSCRIPTION_VERIFICATION_INITIATION_FAILED,
            STATUS_CODE_SUBSCRIPTION_HOST_HAS_NO_PRIVILEGE,
            STATUS_CODE_NON_BLOCKING_REQUEST_NOT_SUPPORTED,
            STATUS_CODE_EXTERNAL_OBJECT_NOT_REACHABLE,
            STATUS_CODE_EXTERNAL_OBJECT_NOT_FOUND,
            STATUS_CODE_MAX_NUMBER_OF_MEMBER_EXCEEDED,
            STATUS_CODE_MEMBER_TYPE_INCONSISTENT,
            STATUS_CODE_MANAGEMENT_SESSION_CANNOT_BE_ESTABLISHED,
            STATUS_CODE_MANAGEMENT_SESSION_ESTABLISHMENT_TIMEOUT,
            STATUS_CODE_INVALID_CMDTYPE, STATUS_CODE_INVALID_ARGUMENTS,
            STATUS_CODE_INSUFFICIENT_ARGUMENT,
            STATUS_CODE_MGMT_CONVERSION_ERROR, STATUS_CODE_MGMT_CANCELLATION_FAILED,
            STATUS_CODE_ALREADY_COMPLETE, STATUS_CODE_COMMAND_NOT_CANCELLABLE})
    public @interface StatusCode {
    }

    public static final int STATUS_CODE_ACCEPTED = 1000;
    public static final int STATUS_CODE_OK = 2000;
    public static final int STATUS_CODE_CREATED = 2001;
    public static final int STATUS_CODE_DELETED = 2002;
    public static final int STATUS_CODE_UPDATED = 2004;
    public static final int STATUS_CODE_BAD_REQUEST = 4000;
    public static final int STATUS_CODE_NOT_FOUND = 4004;
    public static final int STATUS_CODE_OPERATION_NOT_ALLOWED = 4005;
    public static final int STATUS_CODE_REQUEST_TIMEOUT = 4008;
    public static final int STATUS_CODE_SUBSCRIPTION_CREATOR_HAS_NO_PRIVILEGE = 4101;
    public static final int STATUS_CODE_CONTENTS_UNACCEPTABLE = 4102;
    public static final int STATUS_CODE_ACCESS_DENIED = 4103;
    public static final int STATUS_CODE_GROUP_REQUEST_IDENTIFIER_EXISTS = 4104;
    public static final int STATUS_CODE_CONFLICT = 4105;
    public static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 5000;
    public static final int STATUS_CODE_NOT_IMPLEMENTED = 5001;
    public static final int STATUS_CODE_TARGET_NOT_REACHABLE = 5103;
    public static final int STATUS_CODE_NO_PRIVILEGE = 5105;
    public static final int STATUS_CODE_ALREADY_EXISTS = 5106;
    public static final int STATUS_CODE_TARGET_NOT_SUBSCRIBABLE = 5203;
    public static final int STATUS_CODE_SUBSCRIPTION_VERIFICATION_INITIATION_FAILED = 5204;
    public static final int STATUS_CODE_SUBSCRIPTION_HOST_HAS_NO_PRIVILEGE = 5205;
    public static final int STATUS_CODE_NON_BLOCKING_REQUEST_NOT_SUPPORTED = 5206;
    public static final int STATUS_CODE_EXTERNAL_OBJECT_NOT_REACHABLE = 6003;
    public static final int STATUS_CODE_EXTERNAL_OBJECT_NOT_FOUND = 6005;
    public static final int STATUS_CODE_MAX_NUMBER_OF_MEMBER_EXCEEDED = 6010;
    public static final int STATUS_CODE_MEMBER_TYPE_INCONSISTENT = 6011;
    public static final int STATUS_CODE_MANAGEMENT_SESSION_CANNOT_BE_ESTABLISHED = 6020;
    public static final int STATUS_CODE_MANAGEMENT_SESSION_ESTABLISHMENT_TIMEOUT = 6021;
    public static final int STATUS_CODE_INVALID_CMDTYPE = 6022;
    public static final int STATUS_CODE_INVALID_ARGUMENTS = 6023;
    public static final int STATUS_CODE_INSUFFICIENT_ARGUMENT = 6024;
    public static final int STATUS_CODE_MGMT_CONVERSION_ERROR = 6025;
    public static final int STATUS_CODE_MGMT_CANCELLATION_FAILED = 6026;
    public static final int STATUS_CODE_ALREADY_COMPLETE = 6028;
    public static final int STATUS_CODE_COMMAND_NOT_CANCELLABLE = 6029;

    @Retention(SOURCE)
    @IntDef({FILTER_USAGE_DISCOVERY_CRITERIA,
            FILTER_USAGE_CONDITIONAL_RETRIEVAL})
    public @interface FilterUsage {
    }

    public static final int FILTER_USAGE_DISCOVERY_CRITERIA = 1;
    public static final int FILTER_USAGE_CONDITIONAL_RETRIEVAL = 2; // Default.

    @Retention(SOURCE)
    @StringDef({FILTER_CRITERIA_CREATED_BEFORE,
            FILTER_CRITERIA_CREATED_AFTER, FILTER_CRITERIA_MODIFIED_SINCE,
            FILTER_CRITERIA_UNMODIFIED_SINCE, FILTER_CRITERIA_STATE_TAG_SMALLER,
            FILTER_CRITERIA_STATE_TAG_BIGGER, FILTER_CRITERIA_EXPIRE_BEFORE,
            FILTER_CRITERIA_EXPIRE_AFTER, FILTER_CRITERIA_LABELS,
            FILTER_CRITERIA_RESOURCE_TYPE, FILTER_CRITERIA_SIZE_ABOVE,
            FILTER_CRITERIA_SIZE_BELOW, FILTER_CRITERIA_CONTENT_TYPE,
            FILTER_CRITERIA_LIMIT, FILTER_CRITERIA_ATTRIBUTE,
            FILTER_CRITERIA_FILTER_USAGE})
    // TODO Stop this colliding with the class definition.
    public @interface FilterCriteria {
    }

    public static final String FILTER_CRITERIA_CREATED_BEFORE = "crb";
    public static final String FILTER_CRITERIA_CREATED_AFTER = "cra";
    public static final String FILTER_CRITERIA_MODIFIED_SINCE = "ms";
    public static final String FILTER_CRITERIA_UNMODIFIED_SINCE = "us";
    public static final String FILTER_CRITERIA_STATE_TAG_SMALLER = "sts";
    public static final String FILTER_CRITERIA_STATE_TAG_BIGGER = "stb";
    public static final String FILTER_CRITERIA_EXPIRE_BEFORE = "exb";
    public static final String FILTER_CRITERIA_EXPIRE_AFTER = "exa";
    public static final String FILTER_CRITERIA_LABELS = "lbl";
    public static final String FILTER_CRITERIA_RESOURCE_TYPE = "rty";
    public static final String FILTER_CRITERIA_SIZE_ABOVE = "sza";
    public static final String FILTER_CRITERIA_SIZE_BELOW = "szb";
    public static final String FILTER_CRITERIA_CONTENT_TYPE = "cty";
    public static final String FILTER_CRITERIA_LIMIT = "lim";
    public static final String FILTER_CRITERIA_ATTRIBUTE = "atr";
    public static final String FILTER_CRITERIA_FILTER_USAGE = "fu";

    @Retention(SOURCE)
    @IntDef({CONSISTENCY_STRATEGY_ABANDON_MEMBER,
            CONSISTENCY_STRATEGY_ABANDON_GROUP,
            CONSISTENCY_STRATEGY_SET_MIXED})
    public @interface ConsistencyStrategy {
    }

    public static final int CONSISTENCY_STRATEGY_ABANDON_MEMBER = 1;
    public static final int CONSISTENCY_STRATEGY_ABANDON_GROUP = 2;
    public static final int CONSISTENCY_STRATEGY_SET_MIXED = 3;

    @Retention(SOURCE)
    @IntDef({ACCESS_CONTROL_OPERATION_CREATE,
            ACCESS_CONTROL_OPERATION_RETRIEVE,
            ACCESS_CONTROL_OPERATION_UPDATE,
            ACCESS_CONTROL_OPERATION_DELETE,
            ACCESS_CONTROL_OPERATION_DISCOVERY,
            ACCESS_CONTROL_OPERATION_NOTIFY})
    public @interface AccessControlOperation {
    }

    public static final int ACCESS_CONTROL_OPERATION_CREATE = 1;
    public static final int ACCESS_CONTROL_OPERATION_RETRIEVE = 2;
    public static final int ACCESS_CONTROL_OPERATION_UPDATE = 4;
    public static final int ACCESS_CONTROL_OPERATION_DELETE = 8;
    public static final int ACCESS_CONTROL_OPERATION_NOTIFY = 16;
    public static final int ACCESS_CONTROL_OPERATION_DISCOVERY = 32;
}
