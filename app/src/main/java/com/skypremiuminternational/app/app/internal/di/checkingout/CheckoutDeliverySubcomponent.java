package com.skypremiuminternational.app.app.internal.di.checkingout;


import com.skypremiuminternational.app.app.features.checkout.stepone.CheckoutDeliveryFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by aeindraaung on 2/4/18.
 */

@Subcomponent()
public interface CheckoutDeliverySubcomponent extends AndroidInjector<CheckoutDeliveryFragment> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<CheckoutDeliveryFragment> {
  }
}
