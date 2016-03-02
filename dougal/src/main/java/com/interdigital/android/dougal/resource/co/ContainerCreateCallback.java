package com.interdigital.android.dougal.resource.co;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContainerCreateCallback implements Callback<ResponseHolder> {

    private Container container;
    private DougalCallback dougalCallback;

    public ContainerCreateCallback(
            Container container, DougalCallback dougalCallback) {
        this.container = container;
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
        Container returnCo = response.body().getContainer();
        if (container != null) {
            container.setCreationTime(returnCo.getCreationTime());
            container.setExpiryTime(returnCo.getExpiryTime());
            container.setLastModifiedTime(returnCo.getLastModifiedTime());
            container.setParentId(returnCo.getParentId());
            container.setResourceId(returnCo.getResourceId());
            container.setResourceName(returnCo.getResourceName());
        }
        dougalCallback.getResult(container, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
