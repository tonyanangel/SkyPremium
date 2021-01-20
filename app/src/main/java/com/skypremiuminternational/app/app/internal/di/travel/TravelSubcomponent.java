package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ProductListFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface TravelSubcomponent extends AndroidInjector<ProductListFragment> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ProductListFragment> {
  }
}
