package com.skypremiuminternational.app.app.internal.di.buy_now;

import com.skypremiuminternational.app.app.features.buy_now.BuyNowActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface BuyNowSubcomponent extends AndroidInjector<BuyNowActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BuyNowActivity> {
  }

}
