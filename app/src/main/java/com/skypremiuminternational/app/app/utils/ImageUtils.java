package com.skypremiuminternational.app.app.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;

public class ImageUtils {


  /**
   * using Glide to load image (Single)
   * @param imageView
   * @param url
   */
  public static  void glideImg(ImageView imageView , String url){
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.error(R.drawable.white);
    Glide.with(imageView.getContext())
        .load(url)
        .apply(requestOptions)
        .into(imageView);

  }
  public static  void glideImgDefaulShite(ImageView imageView , String url){
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.error(R.drawable.white);
    Glide.with(imageView.getContext())
        .load(url)
        .apply(requestOptions)
        .into(imageView);

  }
  public static  void glideImgDefaulSky(ImageView imageView , String url){
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.error(R.drawable.placeholder);
    Glide.with(imageView.getContext())
        .load(url)
        .apply(requestOptions)
        .into(imageView);

  }

}
