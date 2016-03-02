package com.interdigital.android.dougal.resource.co;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContainerRetrieveCallback implements Callback<ResponseHolder> {

    private String baseUrl;
    private String path;
    private DougalCallback dougalCallback;

    public ContainerRetrieveCallback(String baseUrl, String path, DougalCallback dougalCallback) {
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
        Container container = response.body().getContainer();
        if (container != null) {
            container.setBaseUrl(baseUrl);
            container.setPath(path);
        }
        dougalCallback.getResult(container, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
