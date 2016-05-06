package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Callback;

public class UpdateCallback<R extends Resource> extends BaseCallback<R, ResponseHolder>
        implements Callback<ResponseHolder> {

    private R resource;

    public UpdateCallback(R resource, DougalCallback dougalCallback) {
        super(Types.STATUS_CODE_UPDATED, dougalCallback);
        this.resource = resource;
    }

    @Override
    protected R processResponse(ResponseHolder responseHolder) {
        R r = (R) responseHolder.getResource();
        if (resource != null && r != null) {
            r = checkNonBlocking(r);
            // This is common to all resources.
            // TODO Find all.
            resource.setLastModifiedTime(r.getLastModifiedTime());
        }
        return resource;
    }
}
