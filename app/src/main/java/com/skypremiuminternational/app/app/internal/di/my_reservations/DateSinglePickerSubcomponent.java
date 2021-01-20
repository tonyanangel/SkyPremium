package com.skypremiuminternational.app.app.internal.di.my_reservations;

import com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker.DateSinglePickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface DateSinglePickerSubcomponent extends AndroidInjector<DateSinglePickerDialogFragment> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<DateSinglePickerDialogFragment> {
  }
}
