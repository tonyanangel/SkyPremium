package com.skypremiuminternational.app.app.internal.di.search;

import com.skypremiuminternational.app.app.features.search.result_products.SearchProductsFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface SearchProductsSubcomponent
    extends AndroidInjector<SearchProductsFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<SearchProductsFragment> {
  }
}
