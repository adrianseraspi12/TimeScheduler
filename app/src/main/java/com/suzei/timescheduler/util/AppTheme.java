package com.suzei.timescheduler.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.PreferenceManager;
import android.util.TypedValue;

import com.suzei.timescheduler.R;

public class AppTheme {

    public static void set(Activity activity) {
        String style = PreferenceManager.getDefaultSharedPreferences(activity)
                .getString("app_theme", "Default Theme");
        activity.setTheme(get(style));
    }

    public static int getAttrColor(Activity activity, int attrColor) {
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = activity.obtainStyledAttributes(
                typedValue.data,
                new int[] {attrColor});
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();
        return color;
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
