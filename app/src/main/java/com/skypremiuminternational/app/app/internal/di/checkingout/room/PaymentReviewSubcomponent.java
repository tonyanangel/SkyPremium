package com.skypremiuminternational.app.app.internal.di.checkingout.room;

import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface PaymentReviewSubcomponent extends AndroidInjector<CheckoutPaymentReviewFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<CheckoutPaymentReviewFragment> {

  }
}
