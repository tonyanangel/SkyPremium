package com.skypremiuminternational.app.app.internal.di.detail_reservation;

import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WIKI Toan Tran on 2020 11 18.
 */
@Subcomponent()
public interface DetailReservationSubcomponent extends AndroidInjector<DetailsResevationActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<DetailsResevationActivity> {
  }
}
