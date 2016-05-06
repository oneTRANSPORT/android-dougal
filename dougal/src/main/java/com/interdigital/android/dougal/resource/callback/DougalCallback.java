package com.interdigital.android.dougal.resource.callback;

import com.interdigital.android.dougal.resource.Resource;

public interface DougalCallback {

    void getResponse(Resource resource, Throwable throwable);

}
