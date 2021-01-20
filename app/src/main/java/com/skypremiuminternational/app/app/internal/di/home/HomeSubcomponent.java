package com.skypremiuminternational.app.app.internal.di.home;

import com.skypremiuminternational.app.app.features.home.HomeFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface HomeSubcomponent extends AndroidInjector<HomeFragment> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<HomeFragment> {
  }
}
