package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ean.occupancy.OccupancyPickerDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by codigo on 20/4/18.
 */

@Subcomponent
public interface OccupancyPickerSubcomponent
    extends AndroidInjector<OccupancyPickerDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<OccupancyPickerDialogFragment> {
  }
}

