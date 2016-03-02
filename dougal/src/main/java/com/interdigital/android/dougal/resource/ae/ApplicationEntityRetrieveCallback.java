package com.interdigital.android.dougal.resource.ae;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;
import com.interdigital.android.dougal.resource.ae.ApplicationEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationEntityRetrieveCallback implements Callback<ResponseHolder> {

    private String baseUrl;
    private String path;
    private DougalCallback dougalCallback;

    public ApplicationEntityRetrieveCallback(String baseUrl, String path, DougalCallback dougalCallback) {
        this.baseUrl = baseUrl;
        this.path = path;
        this.dougalCallback = dougalCallback;
    }

    @Override
    public void onResponse(Call<ResponseHolder> call, Response<ResponseHolder> response) {
        if (dougalCallback == null) {
            return;
        }
        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != Types.STATUS_CODE_OK) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        ResponseHolder responseHolder = response.body();
        if (responseHolder == null) {
            dougalCallback.getResult(null, new DougalException(code));
            return;
        }
        ApplicationEntity applicationEntity = response.body().getApplicationEntity();
        if (applicationEntity != null) {
            applicationEntity.setBaseUrl(baseUrl);
            applicationEntity.setPath(path);
        }
        dougalCallback.getResult(applicationEntity, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
