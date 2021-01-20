package com.skypremiuminternational.app.data.network.interceptor;

import android.os.Build;

import com.skypremiuminternational.app.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    Request original = chain.request();

    String userAgent =
        "SkyPremiumAPP/" + BuildConfig.VERSION_NAME + " (Android " + Build.VERSION.RELEASE + ")";
    Request request = original.newBuilder()
        .header("User-Agent", userAgent)
        .method(original.method(), original.body())
        .build();

    return chain.proceed(request);
  }
}
