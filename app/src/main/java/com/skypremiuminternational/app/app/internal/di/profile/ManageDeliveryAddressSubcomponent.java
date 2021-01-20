package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.manage_delivery_address.ManageDeliveryAddressActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by aeindraaung on 2/19/18.
 */

@Subcomponent()
public interface ManageDeliveryAddressSubcomponent extends
    AndroidInjector<ManageDeliveryAddressActivity> {
  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ManageDeliveryAddressActivity> {
  }
}
