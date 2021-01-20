package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.skypremiuminternational.app.R;

/**
 * Created by aeindraaung on 1/22/18.
 */

public class AspectRatioImageView extends ForegroundImageView {
  private String ratio;

  public AspectRatioImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);

    ratio = a.getString(0);

    a.recycle();
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    int height = 0;
    /*You can change aspect ratio as you like */

    switch (ratio) {
      case "16:9":
        height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthSpec) * 9 / 16,
            View.MeasureSpec.EXACTLY);
        break;
      case "4:3":
        height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthSpec) * 3 / 4,
            View.MeasureSpec.EXACTLY);
        break;
      case "1:1":
        height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthSpec) * 1,
            View.MeasureSpec.EXACTLY);
        break;
      case "3:4":
        height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthSpec) * 4 / 3,
            View.MeasureSpec.EXACTLY);
        break;
      case "3:2":
        height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthSpec) * 2 / 3,
            View.MeasureSpec.EXACTLY);
        break;
      default:
        height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthSpec) * 3 / 4,
            View.MeasureSpec.EXACTLY);
        break;
    }

    super.onMeasure(widthSpec, height);
  }
}
