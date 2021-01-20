package com.skypremiuminternational.app.app.internal.di.booking;

import com.skypremiuminternational.app.app.features.booking.summary.BookingSummaryActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface BookingSummarySubcomponent extends AndroidInjector<BookingSummaryActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BookingSummaryActivity> {

  }
}
