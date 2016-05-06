package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.NonBlockingRequest;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<R extends Resource, C> implements Callback<C> {

    @Types.StatusCode
    private int successCode;
    private DougalCallback dougalCallback;

    public BaseCallback(@Types.StatusCode int successCode, DougalCallback dougalCallback) {
        this.successCode = successCode;
        this.dougalCallback = dougalCallback;
    }

    @Override
    public void onResponse(Call<C> call, Response<C> response) {
        if (dougalCallback == null) {
            return;
        }
        if (response.code() == Resource.NO_AUTH_CODE) {
            dougalCallback.getResponse(null, new DougalException(Resource.NO_AUTH));
            return;
        }
        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != successCode) {
            dougalCallback.getResponse(null, new DougalException(code));
            return;
        }
        C c = response.body();
        if (c != null) {
            dougalCallback.getResponse(processResponse(c), null);
            return;
        }
        dougalCallback.getResponse(null, null);
    }

    @Override
    public void onFailure(Call<C> call, Throwable t) {
        dougalCallback.getResponse(null, t);
    }

    protected R checkNonBlocking(R r) {
        @Types.ResourceType
        Integer type = r.getResourceType();
        // Is this payload from a non-blocking request?
        // If so, unpack operation result.
        if (type != null && type == Types.RESOURCE_TYPE_REQUEST) {
            ResponseHolder responseHolder = ((NonBlockingRequest) r).getPrimitiveContent();
            if (responseHolder != null) {
                r = (R) responseHolder.getResource();
            }
        }
        return r;
    }

    protected abstract R processResponse(C c);
}
