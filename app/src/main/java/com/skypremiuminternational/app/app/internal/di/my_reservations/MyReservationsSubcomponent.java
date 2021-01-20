package com.skypremiuminternational.app.app.internal.di.my_reservations;

import com.skypremiuminternational.app.app.features.hunry_go_where.my_reservation.MyResevationsActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


/**
 * Created by WIKI Toan Tran on 2020 11 16.
 */
@Subcomponent()
public interface MyReservationsSubcomponent extends AndroidInjector<MyResevationsActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<MyResevationsActivity> {
  }
}
