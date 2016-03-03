package com.interdigital.android.dougal.resource;

import com.interdigital.android.dougal.Types;

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
