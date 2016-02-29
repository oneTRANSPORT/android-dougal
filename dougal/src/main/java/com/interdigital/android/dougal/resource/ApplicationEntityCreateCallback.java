package com.interdigital.android.dougal.resource;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationEntityCreateCallback implements Callback<ResponseHolder> {

    private ApplicationEntity applicationEntity;
    private DougalCallback dougalCallback;

    public ApplicationEntityCreateCallback(
            ApplicationEntity applicationEntity, DougalCallback dougalCallback) {
        this.applicationEntity = applicationEntity;
        this.dougalCallback = dougalCallback;
    }

    @Override
    public void onResponse(Call<ResponseHolder> call, Response<ResponseHolder> response) {
        if (dougalCallback == null) {
            return;
        }
        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != Types.STATUS_CODE_CREATED) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        ResponseHolder responseHolder = response.body();
        if (responseHolder == null) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        ApplicationEntity returnAe = response.body().getApplicationEntity();
        if (applicationEntity != null) {
            applicationEntity.setCreationTime(returnAe.getCreationTime());
            applicationEntity.setExpiryTime(returnAe.getExpiryTime());
            applicationEntity.setLastModifiedTime(returnAe.getLastModifiedTime());
            applicationEntity.setParentId(returnAe.getParentId());
            applicationEntity.setResourceId(returnAe.getResourceId());
            applicationEntity.setResourceName(returnAe.getResourceName());
        }
        dougalCallback.getResult(applicationEntity, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        // TODO Network failure?
    }
}
