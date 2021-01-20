package com.skypremiuminternational.app.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by johnsonmaung on 8/2/17.
 */

public class UIUtils {

  public static void hideKeyboard(Activity activity) {
    try {
      InputMethodManager inputManager =
          (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
          InputMethodManager.HIDE_NOT_ALWAYS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
