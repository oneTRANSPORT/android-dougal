package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCallback<T extends Resource> implements Callback<ResponseHolder> {

    private T resource;
    private DougalCallback dougalCallback;

    public CreateCallback(T resource, DougalCallback dougalCallback) {
        this.resource = resource;
        this.dougalCallback = dougalCallback;
    }

    @Override
    public void onResponse(Call<ResponseHolder> call, Response<ResponseHolder> response) {
        if (dougalCallback == null) {
            return;
        }
        if (response.code() == Resource.NO_AUTH_CODE) {
            dougalCallback.getResponse(null, new DougalException(Resource.NO_AUTH));
            return;
        }
        @Types.StatusCode
        int code = Resource.getCodeFromResponse(response);
        if (code != Types.STATUS_CODE_CREATED) {
            dougalCallback.getResponse(null, new DougalException(code));
            return;
        }
        ResponseHolder responseHolder = response.body();
        if (responseHolder == null) {
            dougalCallback.getResponse(null, new DougalException(code));
            return;
        }
        T returnT = (T) responseHolder.getResource();
        if (resource != null) {
            // These are common to all resources.
            // For the full resource, do a RETRIEVE request.
            resource.setCreationTime(returnT.getCreationTime());
            resource.setExpiryTime(returnT.getExpiryTime());
            resource.setLastModifiedTime(returnT.getLastModifiedTime());
            resource.setParentId(returnT.getParentId());
            resource.setResourceId(returnT.getResourceId());
            resource.setResourceName(returnT.getResourceName());
        }
        dougalCallback.getResponse(resource, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResponse(null, t);
    }
}
