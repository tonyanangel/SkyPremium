package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.manage_credit_card.ManageCreditCardActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface ManageCreditCardsSubcomponent
    extends AndroidInjector<ManageCreditCardActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ManageCreditCardActivity> {
  }
}
