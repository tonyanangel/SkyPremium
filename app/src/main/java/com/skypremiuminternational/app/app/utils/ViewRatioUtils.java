package com.skypremiuminternational.app.app.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

public class ViewRatioUtils {

  public static int width;

  public static void set16_9(View v, int Padding, Context context) {
    CalculateDisplay(Padding, context);
    int height = (width / 16) * 9;
    v.getLayoutParams().height = height;
  }

  public static void set3_2(View v, int Padding, Context context) {
    CalculateDisplay(Padding, context);
    int height = (width / 3) * 2;
    v.getLayoutParams().height = height;
  }

  public static void set2_3(View v, int Padding, Context context) {
    CalculateDisplay(Padding, context);
    int height = (width / 2) * 3;
    v.getLayoutParams().height = height;
  }

  public static void set3_1(View v, int Padding, Context context) {
    CalculateDisplay(Padding, context);
    int height = (width / 3) * 1;
    v.getLayoutParams().height = height;
  }

  public static void setSquare(View v, int Padding, Context context) {
    CalculateDisplay(Padding, context);
    int height = width;
    v.getLayoutParams().height = height;
  }

  public static void setGrid(View v, int count, Context context) {
    CalculateDisplay(0, context);
    v.getLayoutParams().width = width / count;
    int height = ((width / count) / 3) * 2;
    v.getLayoutParams().height = height;
  }

  public static void setProduct(View v, ImageView iv, Context context) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
        .getMetrics(displaymetrics);
    width = displaymetrics.widthPixels - MetricsUtils.convertDpToPixel(56, context);

    v.getLayoutParams().width = width / 2;
    iv.getLayoutParams().width = width / 2;

    //v.getLayoutParams().height = ((width / 16) / 2) * 20;
    iv.getLayoutParams().height = ((width / 16) / 2) * 10;
  }

  public static void setRoom(ViewGroup rootView, ImageView imageView, Context context) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
        .getMetrics(displaymetrics);
    int width = displaymetrics.widthPixels - MetricsUtils.convertDpToPixel(32, context);

    rootView.getLayoutParams().width = width;
    imageView.getLayoutParams().width = width;

    imageView.getLayoutParams().height = width - MetricsUtils.convertDpToPixel(125, context);
  }

  public static void setRooms(View rootView, ImageView imageView, Context context) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
        .getMetrics(displaymetrics);
    int width = displaymetrics.widthPixels - MetricsUtils.convertDpToPixel(96, context);

    rootView.getLayoutParams().width = width;
    imageView.getLayoutParams().width = width;

    imageView.getLayoutParams().height = width - MetricsUtils.convertDpToPixel(120, context);
  }

  public static void adjustWidhthForProduct(View v, ImageView iv, Context context) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
        .getMetrics(displaymetrics);
    width = displaymetrics.widthPixels - MetricsUtils.convertDpToPixel(46, context);
    //    width = displaymetrics.widthPixels;

    v.getLayoutParams().width = width / 2;
    iv.getLayoutParams().width = width / 2;
    v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
  }

  public static void setEstoreProduct(View v, ImageView iv, Context context, boolean flashSale) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
        .getMetrics(displaymetrics);
    width = displaymetrics.widthPixels - MetricsUtils.convertDpToPixel(46, context);
    //    width = displaymetrics.widthPixels;

    v.getLayoutParams().width = width / 2;
    iv.getLayoutParams().width = width / 2;
    v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
    //iv.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

    int countDownH = MetricsUtils.convertDpToPixel(28, context);
    //    v.getLayoutParams().height =
    //        flashSale ? ((width / 16) / 2) * 22 + countDownH : ((width / 16) / 2) * 22;
    //    iv.getLayoutParams().height = ((width / 16) / 2) * 10;
  }

  public static void setListViewHeight(ExpandableListView listView) {
    ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
    int totalHeight = 0;
    for (int i = 0; i < listAdapter.getGroupCount(); i++) {
      View groupView = listAdapter.getGroupView(i, true, null, listView);
      groupView.measure(0, View.MeasureSpec.UNSPECIFIED);
      totalHeight += groupView.getMeasuredHeight();

      if (listView.isGroupExpanded(i)) {
        for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
          View listItem = listAdapter.getChildView(i, j, false, null, listView);
          listItem.measure(0, View.MeasureSpec.UNSPECIFIED);
          totalHeight += listItem.getMeasuredHeight();
          totalHeight += listView.getDividerHeight();
        }
      }
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
    listView.setLayoutParams(params);
    listView.requestLayout();
  }

  private static void CalculateDisplay(int Padding, Context context) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    // int height = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels - (Padding * 2);
  }
}
