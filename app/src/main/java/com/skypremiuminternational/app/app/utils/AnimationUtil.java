package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by codigo on 22/1/18.
 */

public class AnimationUtil {

  public static Drawable getProgressBarIndeterminate(Context context) {
    final int[] attrs = {android.R.attr.indeterminateDrawable};
    final int attrs_indeterminateDrawable_index = 0;
    TypedArray a = context.obtainStyledAttributes(android.R.style.Widget_ProgressBar_Small, attrs);
    try {
      return a.getDrawable(attrs_indeterminateDrawable_index);
    } finally {
      a.recycle();
    }
  }

  public static void expand(final View v, Animation.AnimationListener animationListener) {
    v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    final int targetHeight = v.getMeasuredHeight();

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.getLayoutParams().height = 1;
    v.setVisibility(View.VISIBLE);
    Animation a = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        v.getLayoutParams().height = interpolatedTime == 1
            ? ViewGroup.LayoutParams.WRAP_CONTENT
            : (int) (targetHeight * interpolatedTime);
        v.requestLayout();
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };

    a.setAnimationListener(animationListener);
    // 1dp/ms
    a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
    v.startAnimation(a);
  }

  public static void collapse(final View v, Animation.AnimationListener animationListener) {
    final int initialHeight = v.getMeasuredHeight();

    Animation a = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 1) {
          v.setVisibility(View.GONE);
        } else {
          v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
          v.requestLayout();
        }
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };

    a.setAnimationListener(animationListener);
    // 1dp/ms
    a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
    v.startAnimation(a);
  }
}
