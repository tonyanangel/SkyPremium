package com.skypremiuminternational.app.app.internal.di.my_reservations;

import com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation.ConfirmReservationDialogFragment;
import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface ConfirmReservationSubcomponent
    extends AndroidInjector<ConfirmReservationDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ConfirmReservationDialogFragment> {
  }
}
