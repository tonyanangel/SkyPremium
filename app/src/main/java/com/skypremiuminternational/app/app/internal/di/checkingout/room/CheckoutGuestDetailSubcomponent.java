package com.skypremiuminternational.app.app.internal.di.checkingout.room;

import com.skypremiuminternational.app.app.features.checkout.room.stepone.CheckoutGuestDetailFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface CheckoutGuestDetailSubcomponent
    extends AndroidInjector<CheckoutGuestDetailFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<CheckoutGuestDetailFragment> {

  }
}
