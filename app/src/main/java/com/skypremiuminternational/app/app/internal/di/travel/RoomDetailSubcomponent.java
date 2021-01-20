package com.skypremiuminternational.app.app.internal.di.travel;

import com.skypremiuminternational.app.app.features.travel.ean.detail.room.RoomDetailDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface RoomDetailSubcomponent extends AndroidInjector<RoomDetailDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<RoomDetailDialogFragment> {

  }
}
