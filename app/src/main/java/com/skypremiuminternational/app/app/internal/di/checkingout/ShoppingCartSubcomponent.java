package com.skypremiuminternational.app.app.internal.di.checkingout;

import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by aeindraaung on 1/26/18.
 */

@Subcomponent
public interface ShoppingCartSubcomponent extends AndroidInjector<ShoppingCartActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ShoppingCartActivity> {

  }
}
