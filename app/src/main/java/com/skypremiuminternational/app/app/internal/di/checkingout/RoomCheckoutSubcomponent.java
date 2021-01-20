package com.skypremiuminternational.app.app.internal.di.checkingout;

import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface RoomCheckoutSubcomponent extends AndroidInjector<RoomCheckoutActivity> {
  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<RoomCheckoutActivity> {

  }
}
