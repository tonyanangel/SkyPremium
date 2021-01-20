package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ean.suggestion.SuggestionActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface SuggestionSubcomponent extends AndroidInjector<SuggestionActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SuggestionActivity> {

  }
}
