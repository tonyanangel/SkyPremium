package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by johnsonmaung on 10/6/17.
 */
@Subcomponent()
public interface DateRangePickerSubcomponent
    extends AndroidInjector<DateRangePickerDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<DateRangePickerDialogFragment> {
  }
}
