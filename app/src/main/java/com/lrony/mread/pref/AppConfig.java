package com.lrony.mread.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.lrony.mread.App;

/**
 * Created by Lrony on 18-4-26.
 */
public class AppConfig {

    public final static String APP_CONFIG = "appConfig";

    private final static String K_NIGHT_MODE = "night_mode";

    public static boolean isNightMode() {
        return getPreferences().getBoolean(K_NIGHT_MODE, false);
    }

    public static void setNightMode(boolean isNightMode) {
        getPreferences()
                .edit()
                .putBoolean(K_NIGHT_MODE, isNightMode)
                .apply();
    }

    private static SharedPreferences sPref;

    private static SharedPreferences getPreferences() {
        if (sPref == null) {
            sPref = App.context().getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
        }
        return sPref;
    }
}
