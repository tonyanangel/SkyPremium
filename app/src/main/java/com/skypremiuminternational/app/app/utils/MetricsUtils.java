package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import javax.inject.Provider;

/**
 * Created by johnsonmaung on 9/25/17.
 */

public class MetricsUtils {
  /**
   * This method converts dp unit to equivalent pixels, depending on device density.
   *
   * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into
   *                pixels
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent px equivalent to dp depending on device density
   */
  public static int convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return Math.round(px);
  }

  public static int convertSpToPixels(float sp, Context context) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
  }

  /**
   * This method converts device specific pixels to density independent pixels.
   *
   * @param px      A value in px (pixels) unit. Which we need to convert into db
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent dp equivalent to px value
   */
  public static float convertPixelsToDp(float px, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return dp;
  }
}
