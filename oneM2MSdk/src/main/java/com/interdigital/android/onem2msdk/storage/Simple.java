package com.interdigital.android.onem2msdk.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class Simple {

    public static final String FILE_NAME = "onem2m_simple_storage";

    private static void setString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    private static String getString(Context context, String key, String initialValue) {
        return getSharedPreferences(context).getString(key, initialValue);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
