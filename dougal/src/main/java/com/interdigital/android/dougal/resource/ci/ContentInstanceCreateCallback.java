package com.interdigital.android.dougal.resource.ci;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentInstanceCreateCallback implements Callback<ResponseHolder> {

    private ContentInstance contentInstance;
    private DougalCallback dougalCallback;

    public ContentInstanceCreateCallback(
            ContentInstance contentInstance, DougalCallback dougalCallback) {
        this.contentInstance = contentInstance;
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
        ContentInstance returnCo = response.body().getContentInstance();
        if (contentInstance != null) {
            contentInstance.setCreationTime(returnCo.getCreationTime());
            contentInstance.setExpiryTime(returnCo.getExpiryTime());
            contentInstance.setLastModifiedTime(returnCo.getLastModifiedTime());
            contentInstance.setParentId(returnCo.getParentId());
            contentInstance.setResourceId(returnCo.getResourceId());
            contentInstance.setResourceName(returnCo.getResourceName());
        }
        dougalCallback.getResult(contentInstance, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
