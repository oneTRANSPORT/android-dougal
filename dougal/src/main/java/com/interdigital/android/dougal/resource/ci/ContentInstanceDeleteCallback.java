package com.interdigital.android.dougal.resource.ci;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO Make generic?
public class ContentInstanceDeleteCallback implements Callback<Void> {

    private DougalCallback dougalCallback;

    public ContentInstanceDeleteCallback(DougalCallback dougalCallback) {
        this.dougalCallback = dougalCallback;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (dougalCallback == null) {
            return;
        }
        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != Types.STATUS_CODE_DELETED) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        dougalCallback.getResult(null, null);
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
