package com.interdigital.android.dougal.resource;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;

import retrofit2.Callback;

public class RetrieveCallback<R extends Resource> extends BaseCallback<R, ResponseHolder>
        implements Callback<ResponseHolder> {

    private String baseUrl;
    private String path;

    public RetrieveCallback(String baseUrl, String path, DougalCallback dougalCallback) {
        super(Types.STATUS_CODE_OK, dougalCallback);
        this.baseUrl = baseUrl;
        this.path = path;
    }

    @Override
    protected R processResponse(ResponseHolder responseHolder) {
        R r = (R) responseHolder.getResource();
        if (r != null) {
            r.setBaseUrl(baseUrl);
            r.setPath(path);
        }
        return r;
    }
}
