package com.skypremiuminternational.app.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.fragment.app.Fragment;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.di.DaggerApplicationComponent;
import com.skypremiuminternational.app.app.utils.DebugTree;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.support.HasSupportFragmentInjector;

import javax.inject.Inject;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import timber.log.Timber;


/**
 * Created by johnsonmaung on 7/10/17.
 */

public class App extends MultiDexApplication
    implements HasActivityInjector, HasSupportFragmentInjector,
        HasBroadcastReceiverInjector {

  @Inject
  DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
  @Inject
  DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;
  @Inject
  DispatchingAndroidInjector<BroadcastReceiver> dispatchingBroadcastReceiverInjector;

  private static Context context;
  public static boolean isOpenningApp = false;
  public static boolean isInApp = false;
  public static boolean isSendingNotification = false;
  public static String idDeliveryAddress = null;
  public static String idBillingAddress = null;
  public static String idCard = null;

  @Override
  public void onCreate() {
    super.onCreate();

    context = getApplicationContext();

     DaggerApplicationComponent.builder().application(this).build().inject(this);

    ViewPump.init(ViewPump.builder()
            .addInterceptor(new CalligraphyInterceptor(
                    new CalligraphyConfig.Builder()
                            .setDefaultFontPath(getString(R.string.fonts_Lato_Regular))
                            .setFontAttrId(R.attr.fontPath)
                            .build()))
            .build());
    if (BuildConfig.DEBUG) {
      CustomActivityOnCrash.install(this);
    }
    Timber.plant(new DebugTree());
    MultiDex.install(this);
  }

  public static Context getAppContext() {
    return context;
  }

  @Override
  public DispatchingAndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  @Override
  public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidFragmentInjector;
  }

  @Override
  public DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
    return dispatchingBroadcastReceiverInjector;
  }

  @Override
  public void onTerminate() {
    isOpenningApp =  false;
    super.onTerminate();
  }

}
