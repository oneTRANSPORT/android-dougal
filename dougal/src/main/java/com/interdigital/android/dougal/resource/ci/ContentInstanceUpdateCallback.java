package com.interdigital.android.dougal.resource.ci;


import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.exception.DougalException;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.DougalCallback;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO Could all these be made into a generic type?
public class ContentInstanceUpdateCallback implements Callback<ResponseHolder> {

    private ContentInstance contentInstance;
    private DougalCallback dougalCallback;

    public ContentInstanceUpdateCallback(
            ContentInstance contentInstance, DougalCallback dougalCallback) {
        this.contentInstance = contentInstance;
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
        dougalCallback.getResult(contentInstance, null);
    }

    @Override
    public void onFailure(Call<ResponseHolder> call, Throwable t) {
        dougalCallback.getResult(null, t);
    }
}
