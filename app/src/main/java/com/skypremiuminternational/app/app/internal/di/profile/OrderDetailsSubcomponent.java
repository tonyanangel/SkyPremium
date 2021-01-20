package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.order.detail.OrderDetailsActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by wmw on 2/16/2018.
 */

@Subcomponent
public interface OrderDetailsSubcomponent extends AndroidInjector<OrderDetailsActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<OrderDetailsActivity> {
  }
}
