package com.interdigital.android.dougal.resource;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;

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

        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != successCode) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        int httpStatusCode = response.code();
        if (httpStatusCode == Resource.NO_AUTH_CODE) {
            dougalCallback.getResult(null, new DougalException(Resource.NO_AUTH));
        }
        C c = response.body();
        if (c != null) {
            dougalCallback.getResult(processResponse(c), null);
            return;
        }
        dougalCallback.getResult(null, null);
    }

    @Override
    public void onFailure(Call<C> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }

    protected abstract R processResponse(C c);
}
