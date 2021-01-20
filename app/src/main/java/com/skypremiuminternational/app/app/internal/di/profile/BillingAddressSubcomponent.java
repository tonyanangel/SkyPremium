package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.billingaddress.ManageBillingAddressActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface BillingAddressSubcomponent extends AndroidInjector<ManageBillingAddressActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ManageBillingAddressActivity> {

  }
}
