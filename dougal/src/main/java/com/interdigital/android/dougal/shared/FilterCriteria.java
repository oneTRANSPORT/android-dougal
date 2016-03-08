package com.interdigital.android.dougal.shared;

import com.interdigital.android.dougal.Types;

import java.util.HashMap;

// TODO Stop this colliding with the enum definition.
public class FilterCriteria {

    private HashMap<String, String> queryMap = new HashMap<>();

    public HashMap<String, String> getQueryMap() {
        return queryMap;
    }

    public String getCreatedBefore() {
        return queryMap.get(Types.FILTER_CRITERIA_CREATED_BEFORE);
    }

    public void putCreatedBefore(String createdBefore) {
        queryMap.put(Types.FILTER_CRITERIA_CREATED_BEFORE, createdBefore);
    }

    public String getCreatedAfter() {
        return queryMap.get(Types.FILTER_CRITERIA_CREATED_AFTER);
    }

    public void putCreatedAfter(String createdAfter) {
        queryMap.put(Types.FILTER_CRITERIA_CREATED_AFTER, createdAfter);
    }

    public String getModifiedSince() {
        return queryMap.get(Types.FILTER_CRITERIA_MODIFIED_SINCE);
    }

    public void putModifiedSince(String modifiedSince) {
        queryMap.put(Types.FILTER_CRITERIA_MODIFIED_SINCE, modifiedSince);
    }

    public String getUnmodifiedSince() {
        return queryMap.get(Types.FILTER_CRITERIA_UNMODIFIED_SINCE);
    }

    public void putUnmodifiedSince(String unmodifiedSince) {
        queryMap.put(Types.FILTER_CRITERIA_UNMODIFIED_SINCE, unmodifiedSince);
    }

    public Integer getStateTagSmaller() {
        String tag = queryMap.get(Types.FILTER_CRITERIA_STATE_TAG_SMALLER);
        if (tag != null) {
            return Integer.parseInt(tag);
        }
        return null;
    }

    public void putStateTagSmaller(Integer stateTagSmaller) {
        if (stateTagSmaller != null) {
            queryMap.put(Types.FILTER_CRITERIA_STATE_TAG_SMALLER,
                    String.valueOf(stateTagSmaller));
        } else {
            queryMap.remove(Types.FILTER_CRITERIA_STATE_TAG_SMALLER);
        }
    }

    public Integer getStateTagBigger() {
        String tag = queryMap.get(Types.FILTER_CRITERIA_STATE_TAG_BIGGER);
        if (tag != null) {
            return Integer.parseInt(tag);
        }
        return null;
    }

    public void putStateTagBigger(Integer stateTagBigger) {
        if (stateTagBigger != null) {
            queryMap.put(Types.FILTER_CRITERIA_STATE_TAG_BIGGER,
                    String.valueOf(stateTagBigger));
        } else {
            queryMap.remove(Types.FILTER_CRITERIA_STATE_TAG_BIGGER);
        }
    }

    public String getExpireBefore() {
        return queryMap.get(Types.FILTER_CRITERIA_EXPIRE_BEFORE);
    }

    public void putExpireBefore(String expireBefore) {
        queryMap.put(Types.FILTER_CRITERIA_EXPIRE_BEFORE, expireBefore);
    }

    public String getExpireAfter() {
        return queryMap.get(Types.FILTER_CRITERIA_EXPIRE_AFTER);
    }

    public void putExpireAfter(String expireAfter) {
        queryMap.put(Types.FILTER_CRITERIA_EXPIRE_AFTER, expireAfter);
    }
// TODO Not sure how to inject these into Retrofit.
// TODO Or how they should be added to the request, probably label[]=...&label[]=...
//    public String[] getLabels() {
//        return labels;
//    }
//
//    public void setLabels(String[] labels) {
//        this.labels = labels;
//    }

    public Integer getResourceType() {
        String type = queryMap.get(Types.FILTER_CRITERIA_RESOURCE_TYPE);
        if (type != null) {
            return Integer.parseInt(type);
        }
        return null;
    }

    public void putResourceType(Integer resourceType) {
        if (resourceType != null) {
            queryMap.put(Types.FILTER_CRITERIA_RESOURCE_TYPE,
                    String.valueOf(resourceType));
        } else {
            queryMap.remove(Types.FILTER_CRITERIA_RESOURCE_TYPE);
        }
    }

    public Integer getSizeAbove() {
        String size = queryMap.get(Types.FILTER_CRITERIA_SIZE_ABOVE);
        if (size != null) {
            return Integer.parseInt(size);
        }
        return null;
    }

    public void putSizeAbove(Integer sizeAbove) {
        if (sizeAbove != null) {
            queryMap.put(Types.FILTER_CRITERIA_SIZE_ABOVE,
                    String.valueOf(sizeAbove));
        } else {
            queryMap.remove(Types.FILTER_CRITERIA_SIZE_ABOVE);
        }
    }

    public Integer getSizeBelow() {
        String size = queryMap.get(Types.FILTER_CRITERIA_SIZE_BELOW);
        if (size != null) {
            return Integer.parseInt(size);
        }
        return null;
    }

    public void putSizeBelow(Integer sizeBelow) {
        if (sizeBelow != null) {
            queryMap.put(Types.FILTER_CRITERIA_SIZE_BELOW,
                    String.valueOf(sizeBelow));
        } else {
            queryMap.remove(Types.FILTER_CRITERIA_SIZE_BELOW);
        }
    }

    public String getContentType() {
        return queryMap.get(Types.FILTER_CRITERIA_CONTENT_TYPE);
    }

    public void putContentType(String contentType) {
        queryMap.put(Types.FILTER_CRITERIA_CONTENT_TYPE, contentType);
    }

    public Integer getLimit() {
        String limit = queryMap.get(Types.FILTER_CRITERIA_LIMIT);
        if (limit != null) {
            return Integer.parseInt(limit);
        }
        return null;
    }

    public void putLimit(Integer limit) {
        if (limit != null) {
            queryMap.put(Types.FILTER_CRITERIA_LIMIT,
                    String.valueOf(limit));
        } else {
            queryMap.remove(Types.FILTER_CRITERIA_LIMIT);
        }
    }

    public String getAttribute() {
        return queryMap.get(Types.FILTER_CRITERIA_ATTRIBUTE);
    }

    public void putAttribute(String attribute) {
        queryMap.put(Types.FILTER_CRITERIA_ATTRIBUTE, attribute);
    }
}
