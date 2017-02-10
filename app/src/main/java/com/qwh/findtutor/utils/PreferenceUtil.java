package com.qwh.findtutor.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qwh on 2016/4/21.
 */

public class PreferenceUtil {
    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mEditor = null;

    public static void init(Context context) {
        if (null == mSharedPreferences) {
            mSharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void removeKey(String key) {
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.commit();
    }

    public static void removeAll() {
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }

    public static void commitString(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getString(String key, String faillValue) {
        return mSharedPreferences.getString(key, faillValue);
    }

    public static void commitInt(String key, int value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getInt(String key, int failValue) {
        return mSharedPreferences.getInt(key, failValue);
    }

    public static void commitFloat(String key, float value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public static Float getFloat(String key, float failValue) {
        return mSharedPreferences.getFloat(key, failValue);
    }

    public static void commitBoolean(String key, boolean value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static Boolean getBoolean(String key, boolean failValue) {
        return mSharedPreferences.getBoolean(key, failValue);
    }
}
