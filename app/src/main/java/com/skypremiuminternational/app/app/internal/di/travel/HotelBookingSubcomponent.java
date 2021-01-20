package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.booking.HotelBookingDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by johnsonmaung on 10/6/17.
 */
@Subcomponent()
public interface HotelBookingSubcomponent
    extends AndroidInjector<HotelBookingDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<HotelBookingDialogFragment> {
  }
}
