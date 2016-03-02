package com.interdigital.android.dougal.exception;

import com.interdigital.android.dougal.Types;

public class DougalException extends Exception {

    @Types.StatusCode
    private int code;

    public DougalException(@Types.StatusCode int code) {
        super();
        this.code = code;
    }

    public DougalException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            return "Status code = " + code;
        }
        return super.getMessage();
    }

    @Types.StatusCode
    public int getCode() {
        return code;
    }
}
