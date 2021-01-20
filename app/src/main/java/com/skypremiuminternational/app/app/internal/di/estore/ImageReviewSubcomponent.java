package com.skypremiuminternational.app.app.internal.di.estore;

import com.skypremiuminternational.app.app.features.estore.detail.review.ImageReviewDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface ImageReviewSubcomponent extends AndroidInjector<ImageReviewDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ImageReviewDialogFragment> {
  }
}
