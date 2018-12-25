package com.example.princesaoud.dictionnaryapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

//Youssouf Kanate Developing


public class Global {

    public static void saveState(Activity activity, String key, String value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getState(Activity activity, String key){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);

    }
}
