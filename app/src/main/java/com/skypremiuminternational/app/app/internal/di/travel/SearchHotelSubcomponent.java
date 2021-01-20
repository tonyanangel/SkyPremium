package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ean.search.SearchHotelDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface SearchHotelSubcomponent extends AndroidInjector<SearchHotelDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<SearchHotelDialogFragment> {
  }
}
