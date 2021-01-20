package com.skypremiuminternational.app.app.internal.di.booking;

import com.skypremiuminternational.app.app.features.booking.detail.BookingDetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface BookingDetailSubcomponent extends AndroidInjector<BookingDetailActivity> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BookingDetailActivity> {

  }
}
