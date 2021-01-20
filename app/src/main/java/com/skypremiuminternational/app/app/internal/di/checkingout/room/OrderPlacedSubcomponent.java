package com.skypremiuminternational.app.app.internal.di.checkingout.room;

import com.skypremiuminternational.app.app.features.checkout.room.stepthree.OrderPlacedFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface OrderPlacedSubcomponent extends AndroidInjector<OrderPlacedFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<OrderPlacedFragment> {

  }
}
