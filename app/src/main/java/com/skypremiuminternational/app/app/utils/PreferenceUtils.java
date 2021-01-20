package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by johnsonmaung on 7/11/17.
 */

@Singleton
public class PreferenceUtils {

  private static final String PREFS_NAME = "SkyPremium";
  private SharedPreferences prefs;

  @Inject
  public PreferenceUtils(Context context) {
    prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }

  public String get(String key, String defaultValue) {
    return prefs.getString(key, defaultValue);
  }

  public void save(String key, String value) {
    prefs.edit().putString(key, value).apply();
  }

  public boolean get(String key, boolean defaultValue) {
    return prefs.getBoolean(key, defaultValue);
  }

  public void save(String key, boolean value) {
    prefs.edit().putBoolean(key, value).apply();
  }

  public void remove(String key) {
    prefs.edit().remove(key).apply();
  }
  public void clearAllPrefs(){
    SharedPreferences.Editor editor = prefs.edit();
    editor.clear();
    editor.apply();
  }
}
