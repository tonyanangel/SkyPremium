package com.skypremiuminternational.app.app.internal.di.checkingout;

import com.skypremiuminternational.app.app.features.checkout.steptwo.CheckoutReviewFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by aeindraaung on 2/4/18.
 */

@Subcomponent()
public interface CheckoutReviewSubcomponent extends AndroidInjector<CheckoutReviewFragment> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<CheckoutReviewFragment> {
  }
}
