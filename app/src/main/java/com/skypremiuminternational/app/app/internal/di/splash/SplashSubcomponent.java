package com.skypremiuminternational.app.app.internal.di.splash;

import com.skypremiuminternational.app.app.features.splash.SplashActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface SplashSubcomponent extends AndroidInjector<SplashActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SplashActivity> {
  }
}
