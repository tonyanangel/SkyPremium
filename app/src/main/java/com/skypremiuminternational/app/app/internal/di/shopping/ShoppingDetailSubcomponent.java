package com.skypremiuminternational.app.app.internal.di.shopping;

import com.skypremiuminternational.app.app.features.shopping.detail.ShoppingDetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface ShoppingDetailSubcomponent
    extends AndroidInjector<ShoppingDetailActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ShoppingDetailActivity> {
  }
}
