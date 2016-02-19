package com.interdigital.android.onem2msdk.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Simple {

    public static final String FILE_NAME = "onem2m_simple_storage";

    private static final String INSTALLATION_ID_PREFIX = "pref_key_installation_id_prefix";
    private static final String INSTALLATION_ID = "pref_key_installation_id";

    public static void setInstallationIdPrefix(Context context, String prefix) {
        setString(context, INSTALLATION_ID_PREFIX, prefix);
    }

    public static String getInstallationId(Context context) {
        String installationIdPrefix = getString(context, INSTALLATION_ID_PREFIX, "");
        if (installationIdPrefix.equals((""))) {
            return "";
        }
        String installationId = getString(context, INSTALLATION_ID, "");
        if (installationId.equals("")) {
            try {
                MessageDigest digester = MessageDigest.getInstance("MD5");
                byte[] bytes = (String.valueOf(System.currentTimeMillis())
                        + String.valueOf(SystemClock.elapsedRealtimeNanos())
                        + String.valueOf(Math.random())).getBytes();
                digester.update(bytes, 0, bytes.length);
                installationId = new String(Base64.encode(digester.digest(), 0));
                setString(context, INSTALLATION_ID_PREFIX + INSTALLATION_ID, installationId);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return getString(context, INSTALLATION_ID, "");
    }

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
