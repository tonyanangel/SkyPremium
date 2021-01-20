package com.skypremiuminternational.app.app.internal.di.landing;

import com.skypremiuminternational.app.app.features.landing.LandingActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface LandingSubcomponent extends AndroidInjector<LandingActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<LandingActivity> {
  }
}
