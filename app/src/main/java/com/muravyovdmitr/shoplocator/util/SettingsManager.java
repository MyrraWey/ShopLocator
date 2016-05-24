package com.muravyovdmitr.shoplocator.util;

import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;

/**
 * Created by dmitrij on 5/10/16.
 */
public class SettingsManager {
    private static String SPLASHING_SCREEN_DURATION = "SplashScreenDuration";
    private static String SPLASHING_SCREEN_ENABLE = "SplashScreenEnable";
    private static String LAST_SYNC_DATE = "LastSyncDate";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public SettingsManager(Context context) {
        this.mPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        this.mEditor = this.mPreferences.edit();
    }

    public int getSplashingScreenDuration() {
        return this.mPreferences.getInt(SPLASHING_SCREEN_DURATION, 3);
    }

    public void setSplashingScreenDuration(int duration) {
        this.mEditor
                .putInt(SPLASHING_SCREEN_DURATION, duration)
                .commit();
    }

    public boolean isSplashingScreenEnable() {
        return this.mPreferences.getBoolean(SPLASHING_SCREEN_ENABLE, true);
    }

    public void setSplashingScreenEnable(boolean enable) {
        this.mEditor
                .putBoolean(SPLASHING_SCREEN_ENABLE, enable)
                .commit();
    }

    public void setLastSyncDate(String lastSyncDate) {
        this.mEditor
                .putString(LAST_SYNC_DATE, lastSyncDate)
                .commit();
    }

    public String getLastSyncDate() {
        return this.mPreferences.getString(LAST_SYNC_DATE, null);
    }
}
