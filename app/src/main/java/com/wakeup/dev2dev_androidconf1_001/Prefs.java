package com.wakeup.dev2dev_androidconf1_001;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Евгения on 19.03.2016.
 */
public class Prefs {
    private static final String KEY_PEOPLE = "people";


    public static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void savePeople(Context context, String json) {
        getPrefs(context).edit().putString(KEY_PEOPLE, json).apply();
    }

    public static String getPeople(Context context) {
        return getPrefs(context).getString(KEY_PEOPLE, null);
    }
}
