package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface TravelDetailSubcomponent
    extends AndroidInjector<TravelDetailActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<TravelDetailActivity> {
  }
}
