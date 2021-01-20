package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.my_favourites.MyFavouritesActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface MyFavouritesSubcomponent
    extends AndroidInjector<MyFavouritesActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<MyFavouritesActivity> {
  }
}
