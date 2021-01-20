package com.skypremiuminternational.app.app.internal.di.faq;

import com.skypremiuminternational.app.app.features.faq.FaqActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface FaqSubcomponent extends AndroidInjector<FaqActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<FaqActivity> {
  }
}
