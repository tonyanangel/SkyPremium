package com.skypremiuminternational.app.app.internal.di.my_reservations;

import com.skypremiuminternational.app.app.features.checkout.stepthree.CheckoutOrderPlacedFragment;
import com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation.MakeAReservationDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


/**
 * Created by WIKI Toan Tran on 20201123.
 */
@Subcomponent()
public interface MakeAReservationSubcomponent extends AndroidInjector<MakeAReservationDialogFragment> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<MakeAReservationDialogFragment> {
  }
}
