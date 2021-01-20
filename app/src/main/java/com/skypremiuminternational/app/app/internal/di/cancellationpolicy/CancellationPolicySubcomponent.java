package com.skypremiuminternational.app.app.internal.di.cancellationpolicy;

import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface CancellationPolicySubcomponent
    extends AndroidInjector<CancellationPolicyDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<CancellationPolicyDialogFragment> {

  }
}
