package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewDialogFragment;
import com.skypremiuminternational.app.app.features.profile.order.detail.see_review.ReviewContentDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface ReviewContentSubcomponent extends AndroidInjector<ReviewContentDialogFragment>{


  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ReviewContentDialogFragment> {
  }
}
