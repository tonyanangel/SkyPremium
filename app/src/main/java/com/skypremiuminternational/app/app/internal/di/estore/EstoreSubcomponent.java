package com.skypremiuminternational.app.app.internal.di.estore;

import com.skypremiuminternational.app.app.features.estore.EstoreActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface EstoreSubcomponent extends AndroidInjector<EstoreActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<EstoreActivity> {
  }
}
