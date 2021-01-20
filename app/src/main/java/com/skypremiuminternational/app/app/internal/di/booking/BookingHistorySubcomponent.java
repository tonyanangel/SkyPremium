package com.skypremiuminternational.app.app.internal.di.booking;

import com.skypremiuminternational.app.app.features.booking.history.BookingsHistoryActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface BookingHistorySubcomponent extends AndroidInjector<BookingsHistoryActivity> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BookingsHistoryActivity> {

  }
}
