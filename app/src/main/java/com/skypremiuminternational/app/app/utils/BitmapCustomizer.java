package com.skypremiuminternational.app.app.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.NonNull;

import com.skypremiuminternational.app.R;

import javax.inject.Inject;

/**
 * Created by codigo on 9/1/18.
 */

public final class BitmapCustomizer {

  private final Context context;
  private final Paint textPaint;

  @Inject
  public BitmapCustomizer(Context context) {
    this.context = context;
    int textColor;
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      textColor = context.getResources().getColor(R.color.white);
    } else {
      textColor = context.getColor(R.color.white);
    }
    textPaint = new Paint();
    textPaint.setColor(textColor);
  }

  public Bitmap customize(@NonNull final Bitmap baseImage, final int baseW, final int baseH) {

    Bitmap customBitmap = Bitmap.createBitmap(baseW, baseH, getSuitableConfig());
    Canvas canvas = new Canvas(customBitmap);

    canvas.drawBitmap(baseImage, 0, 0, null);
    return customBitmap;
  }

  private Bitmap.Config getSuitableConfig() {

    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // Prefer higher quality images unless we're on a low RAM device
      return activityManager.isLowRamDevice() ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
    } else {
      return Bitmap.Config.RGB_565;
    }
  }
}

