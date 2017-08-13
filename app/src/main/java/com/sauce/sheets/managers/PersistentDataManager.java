package com.sauce.sheets.managers;

import android.content.Context;
import android.content.SharedPreferences;


public class PersistentDataManager {

    public static final String USER_DATA= "user_data";

    private static final int DEFAULT_INT       = -1;
    private static final String DEFAULT_STRING = "";

    private SharedPreferences sharedPreferences;


    public PersistentDataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, DEFAULT_INT);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);

        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, DEFAULT_STRING);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

}