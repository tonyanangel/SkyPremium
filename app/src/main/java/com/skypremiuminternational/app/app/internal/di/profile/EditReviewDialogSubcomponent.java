package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.order.detail.edit_review.EditReviewDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface EditReviewDialogSubcomponent extends AndroidInjector<EditReviewDialogFragment>{


  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<EditReviewDialogFragment> {
  }
}
