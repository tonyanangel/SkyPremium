package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.order.detail.review.ReviewDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface ReviewSubcomponent extends AndroidInjector<ReviewDialogFragment>{


    @Subcomponent.Builder
    abstract class Builder
        extends AndroidInjector.Builder<ReviewDialogFragment> {
    }
}
