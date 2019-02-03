package com.mobile.quiz.utility;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager {

    public static final String KEY_REGISTRATION_ID = "registrationid";
    public static final String KEY_STUDENT_NAME = "studentname";
    public static final String KEY_PIN = "pin";
    private static final String SHARED_PREF_NAME = "rrqsharedpref";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setUserData(String key, String value) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getUserData(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}