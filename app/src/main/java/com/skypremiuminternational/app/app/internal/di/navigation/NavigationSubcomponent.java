package com.skypremiuminternational.app.app.internal.di.navigation;

import com.skypremiuminternational.app.app.features.navigation.NavigationDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface NavigationSubcomponent
    extends AndroidInjector<NavigationDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<NavigationDialogFragment> {
  }
}
