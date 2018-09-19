package com.suzei.timescheduler.util;

import android.app.Activity;
import android.support.v7.preference.PreferenceManager;

import com.suzei.timescheduler.R;

public class AppTheme {

    public static void set(Activity activity) {
        String style = PreferenceManager.getDefaultSharedPreferences(activity)
                .getString("app_theme", "Default Theme");
        activity.setTheme(get(style));
    }

    private static int get(String theme) {
        switch (theme) {
            case "Default Theme":
                return R.style.AppTheme;

            case "Dark Theme":
                return R.style.AppTheme_Dark;

            case "Green Theme":
                return R.style.AppTheme_Light_Green;

            case "Green Dark Theme":
                return R.style.AppTheme_Dark_Green;

            case "Pink Theme":
                return R.style.AppTheme_Light_Pink;

            case "Pink Dark Theme":
                return R.style.AppTheme_Dark_Pink;

            default:
                throw new IllegalArgumentException("Invalid theme=" + theme);
        }
    }

}
