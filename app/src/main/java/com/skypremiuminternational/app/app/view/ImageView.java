package com.skypremiuminternational.app.app.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.utils.Utils;

import org.jsoup.nodes.Element;


/**
 * Created by angebagui on 06/08/2016.
 */

public class ImageView extends ElementView {

  private static final String TAG = ImageView.class.getSimpleName();


  public ImageView(Context context, Element element) {
    super(context, element);
  }

  protected ImageView(Context context, AttributeSet attrs, Element element) {
    super(context, attrs, element);
  }

  @Override
  public void render() {
    Log.d(TAG, "I am image view");
    setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    android.widget.ImageView imageView = new android.widget.ImageView(getContext());
    imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    final String url = getLink();
    imageView.setPadding(Utils.dpToPx(getContext(), 8), Utils.dpToPx(getContext(), 16),
        Utils.dpToPx(getContext(), 8), Utils.dpToPx(getContext(), 16));
    imageView.setAdjustViewBounds(true);
    if (!TextUtils.isEmpty(url)) {
      Glide.with(getContext()).load(url).into(imageView);
    }
    addView(imageView);
  }

  public String getLink() {
    if (getElement() == null)
      return null;
    String raw = getElement().toString();
    String raws[] = raw.split("url");
    if (raws.length > 1 && getElement().attr("id") != null && getElement().attr("id")
        .equals("table-responsive-mobile")) {
      String rawUrl = raws[1].substring(2, raws[1].lastIndexOf("}") - 2);
      return BuildConfig.BASE_IMG_URL + "/media" + rawUrl.replace(" ", "/");
    }
    return "";
  }
}
