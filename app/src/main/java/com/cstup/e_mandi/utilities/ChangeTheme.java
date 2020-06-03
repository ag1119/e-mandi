package com.cstup.e_mandi.utilities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.cstup.e_mandi.R;

public class ChangeTheme {
    private static int stheme;
    public final static int THEME_VEGETABLES = 0;
    public final static int THEME_GRAINS = 1;
    public final static int THEME_FRUITS = 2;
    public static void setTheme(AppCompatActivity activity , int theme){
        stheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity , activity.getClass()));
    }

    public static void onActivityCreateSetTheme(AppCompatActivity activity){
        switch (stheme){
            case THEME_GRAINS:
                activity.setTheme(R.style.AppTheme2);
                break;
            case THEME_FRUITS:
                activity.setTheme(R.style.AppTheme3);
                break;
            case THEME_VEGETABLES:
            default:
                activity.setTheme(R.style.AppTheme);
        }
    }
}
