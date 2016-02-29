package com.interdigital.android.dougal.resource;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationEntityUpdateCallback implements Callback<ResponseHolder> {

    private ApplicationEntity applicationEntity;
    private DougalCallback dougalCallback;

    public ApplicationEntityUpdateCallback(
            ApplicationEntity applicationEntity, DougalCallback dougalCallback) {
        this.applicationEntity = applicationEntity;
        this.dougalCallback = dougalCallback;
    }

    @Override
    public void onResponse(Call<ResponseHolder> call, Response<ResponseHolder> response) {
// TODO Work out how to test all this.  May need updating.
        if (dougalCallback == null) {
            return;
        }
        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != Types.STATUS_CODE_UPDATED) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        ResponseHolder responseHolder = response.body();
        if (responseHolder == null) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        dougalCallback.getResult(applicationEntity, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
