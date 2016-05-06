package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Callback;

public class NonBlockingIdCallback<R extends Resource> extends BaseCallback<R, ResponseHolder>
        implements Callback<ResponseHolder> {

    public NonBlockingIdCallback(DougalCallback dougalCallback) {
        super(Types.STATUS_CODE_ACCEPTED, dougalCallback);
    }

    @Override
    protected R processResponse(ResponseHolder responseHolder) {
        return (R) responseHolder.getResource();
    }
}
