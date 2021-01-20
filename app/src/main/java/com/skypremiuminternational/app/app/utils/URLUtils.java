package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;

/**
 * Created by sandi on 10/23/17.
 */

public class URLUtils {



  public static String formatImageURL(String url) {
      return BuildConfig.BASE_IMG_URL + "/media/catalog/product" + url;

  }
  public static String formatImageURL_Cld(String url) {
      return BuildConfig.BASE_IMG_URL_1 + "/media/catalog/product" + url;

  }

  public static RequestListener getGlideListener (Context context, String link, ImageView ivProduct){
    Handler handler = new Handler();
    RequestListener requestListener = new RequestListener() {

      @Override
      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
        handler.post(new Runnable() {
                       @Override
                       public void run() {
                        RequestOptions requestOptions = new RequestOptions();
                         requestOptions.placeholder(R.drawable.placeholder);
                         requestOptions.error(R.drawable.white);

                         Glide.with(context)
                             .load(URLUtils.formatImageURL_Cld(link))
                             .apply(requestOptions)
                             .into(ivProduct);
                       }
                     });

        return true;
      }

      @Override
      public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
        return false;
      }
    };
    return requestListener;
  }

  public static String bannerImageURL(String url) {
    return BuildConfig.BASE_IMG_URL + "/media/catalog/category/" + url;
  }
  public static String commentImageURL(String url) {
    return BuildConfig.BASE_IMG_URL + "/media/catalog/product" + url;
  }

  public static String hgwImageURL(String restaurantId){
      return BuildConfig.API_HGW + BuildConfig.API_HGW_IMG+ "/thumb_"+restaurantId+ ".jpg";
  }

}
