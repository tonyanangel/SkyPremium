package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ean.detail.property.PropertyDetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by codigo on 23/4/18.
 */

@Subcomponent
public interface PropertyDetailSubcomponent
    extends AndroidInjector<PropertyDetailActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<PropertyDetailActivity> {
  }
}