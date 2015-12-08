package com.interdigital.android.onem2msdk;

public class SDK {

    private static SDK instance;
    private static long requestId = 0L;

    private SDK() {

    }

    public static synchronized SDK getInstance() {
        if (instance == null) {
            instance = new SDK();
        }
        return instance;
    }

    public synchronized long getUniqueRequestId() {
        return requestId++;
    }
}
