package com.sauce.sheets.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;


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

    public void saveArray(String key, List<String> arrayToSave) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonText = gson.toJson(arrayToSave);
        editor.putString(key, jsonText);
        editor.apply();
    }

    public String[] getArray(String key){
        Gson gson = new Gson();
        String jsonText = sharedPreferences.getString(key, null);
        String[] text = gson.fromJson(jsonText, String[].class);
        return text;
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