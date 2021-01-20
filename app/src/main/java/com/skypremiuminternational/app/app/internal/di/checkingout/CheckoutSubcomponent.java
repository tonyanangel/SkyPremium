package com.skypremiuminternational.app.app.internal.di.checkingout;

import com.skypremiuminternational.app.app.features.checkout.CheckoutActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by aeindraaung on 1/29/18.
 */

@Subcomponent
public interface CheckoutSubcomponent extends AndroidInjector<CheckoutActivity> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<CheckoutActivity> {

  }
}
