package com.skypremiuminternational.app.data.network;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.data.network.interceptor.HeaderInterceptor;
import com.skypremiuminternational.app.data.network.interceptor.HttpLoggingInterceptor;

import java.math.BigDecimal;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by johnsonmaung on 9/26/17.
 */

public class RestAdapter {

  public static Retrofit create() {

    CookieManager cookieManager = new CookieManager();
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    //allow cookie and session
    httpClient.cookieJar(new JavaNetCookieJar(cookieManager));
    httpClient.connectTimeout(3, TimeUnit.MINUTES);
    httpClient.writeTimeout(3, TimeUnit.MINUTES);
    httpClient.readTimeout(3, TimeUnit.MINUTES);

    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
        Log.e("Logging API", message);
      }
      );
      httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      httpClient.addInterceptor(httpLoggingInterceptor);
    }

    httpClient.addInterceptor(new HeaderInterceptor());
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        if (f.getName().startsWith("_")) return true;
        return false;
      }

      @Override
      public boolean shouldSkipClass(Class<?> incomingClass) {
        return false;
      }
    });

    gsonBuilder.registerTypeAdapter(Double.class,
        (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
          BigDecimal value = BigDecimal.valueOf(src);

          return new JsonPrimitive(value.toPlainString());
        });

    gsonBuilder.registerTypeAdapterFactory(new AutoValueAdapterFactory()).create();

    Gson gson =
        gsonBuilder.setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").serializeNulls().create();

    return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();
  }
}
