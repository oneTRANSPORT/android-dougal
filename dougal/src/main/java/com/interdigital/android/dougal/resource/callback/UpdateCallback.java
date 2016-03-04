package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.callback.BaseCallback;

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
        return resource;
    }
}
