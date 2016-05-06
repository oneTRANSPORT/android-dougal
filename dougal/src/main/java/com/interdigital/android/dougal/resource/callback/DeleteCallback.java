package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.Types;
import com.interdigital.android.dougal.resource.Resource;

import retrofit2.Callback;

public class DeleteCallback extends BaseCallback<Resource, Void> implements Callback<Void> {

    public DeleteCallback(DougalCallback dougalCallback) {
        super(Types.STATUS_CODE_DELETED, dougalCallback);
    }

    @Override
    protected Resource processResponse(Void aVoid) {
        return null;
    }

}
