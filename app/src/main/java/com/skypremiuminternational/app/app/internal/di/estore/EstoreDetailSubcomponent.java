package com.skypremiuminternational.app.app.internal.di.estore;

import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by wmw on 2/6/2018.
 */
@Subcomponent
public interface EstoreDetailSubcomponent extends AndroidInjector<EstoreDetailActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<EstoreDetailActivity> {
  }

}
