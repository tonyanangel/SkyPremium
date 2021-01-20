package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.my_orders.MyOrderActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface MyOrdersSubcomponent extends AndroidInjector<MyOrderActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<MyOrderActivity> {
  }
}
