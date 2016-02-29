package com.interdigital.android.dougal.exception;

import com.interdigital.android.dougal.Types;

public class DougalException extends Exception {

    @Types.StatusCode
    private int code;

    public DougalException(@Types.StatusCode int code) {
        super();
        this.code = code;
    }

    @Override
    public String getMessage() {
        return "Status code = " + code;
    }

    @Types.StatusCode
    public int getCode() {
        return code;
    }
}
