package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.network.response.ResponseHolder;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Callback;

public class RetrieveCallback<R extends Resource> extends BaseCallback<R, ResponseHolder>
        implements Callback<ResponseHolder> {

    private String aeId;
    private String baseUrl;
    private String retrievePath;

    public RetrieveCallback(String aeId, String baseUrl, String retrievePath,
                            DougalCallback dougalCallback) {
        super(Types.STATUS_CODE_OK, dougalCallback);
        this.aeId = aeId;
        this.baseUrl = baseUrl;
        this.retrievePath = retrievePath;
    }

    @Override
    protected R processResponse(ResponseHolder responseHolder) {
        R r = (R) responseHolder.getResource();
        if (r != null) {
            r = checkNonBlocking(r);
            r.setAeId(aeId);
            r.setBaseUrl(baseUrl);
            r.setRetrievePath(retrievePath);
        }
        return r;
    }
}
