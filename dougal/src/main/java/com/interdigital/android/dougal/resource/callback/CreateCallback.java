package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Callback;

public class CreateCallback<R extends Resource> extends BaseCallback<R, ResponseHolder>
        implements Callback<ResponseHolder> {

    private R resource;

    public CreateCallback(R resource, DougalCallback dougalCallback) {
        super(Types.STATUS_CODE_CREATED, dougalCallback);
        this.resource = resource;
    }

    @Override
    protected R processResponse(ResponseHolder responseHolder) {
        R r = (R) responseHolder.getResource();
        if (resource != null) {
            // These are common to all resources.
            // For the full resource, do a RETRIEVE request.
            resource.setCreationTime(r.getCreationTime());
            resource.setExpiryTime(r.getExpiryTime());
            resource.setLastModifiedTime(r.getLastModifiedTime());
            resource.setParentId(r.getParentId());
            resource.setResourceId(r.getResourceId());
            resource.setResourceName(r.getResourceName());
        }
        return r;
    }
}