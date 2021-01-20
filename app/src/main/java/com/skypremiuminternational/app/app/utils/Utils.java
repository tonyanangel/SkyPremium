package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.skypremiuminternational.app.app.view.ElementView;
import com.skypremiuminternational.app.app.view.IFrameView;
import com.skypremiuminternational.app.app.view.ImageView;
import com.skypremiuminternational.app.app.view.MediumTextView;
import com.skypremiuminternational.app.app.view.WebTextView;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by angebagui on 06/08/2016.
 */

public class Utils {

  public static void appendView(ElementView elementView, Elements elements) {
    for (Element e : elements) {
      Elements imgElements = JsoupUtils.getImageElements(e);
      if (imgElements.size() > 0) {
        for (Element img : imgElements) {
          elementView.addView(new ImageView(elementView.getContext(), img));
        }
      } else {
        elementView.addView(new IFrameView(elementView.getContext(), e));
      }
    }
  }

  public static void appendView(ElementView elementView, Element element) {
    if (JsoupUtils.isImage(element)) {
      elementView.addView(new ImageView(elementView.getContext(), element));
    } else {
      elementView.addView(new IFrameView(elementView.getContext(), element));
    }
  }

  public static int dpToPx(Context context, int dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return px;
  }

  public static void setTypeface(TextView textView, String fontName) {
    Typeface myTypeface =
        Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName);
    textView.setTypeface(myTypeface);
  }

  public static void appendWebView(ElementView elementView, Element element) {
    elementView.addView(new IFrameView(elementView.getContext(), element));
  }
}
