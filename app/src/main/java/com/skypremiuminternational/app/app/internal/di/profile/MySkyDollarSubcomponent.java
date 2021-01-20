package com.skypremiuminternational.app.app.internal.di.profile;
import com.skypremiuminternational.app.app.features.profile.my_sky_dollar.MySkyDollarActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface MySkyDollarSubcomponent extends AndroidInjector<MySkyDollarActivity> {
  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<MySkyDollarActivity> {
  }
}
