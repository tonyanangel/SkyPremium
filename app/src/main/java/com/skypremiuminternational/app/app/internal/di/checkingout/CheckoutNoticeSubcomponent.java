package com.skypremiuminternational.app.app.internal.di.checkingout;

import com.skypremiuminternational.app.app.features.profile.edit_profile.MembershipRenewalDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by codigo on 2/4/18.
 */
@Subcomponent()
public interface CheckoutNoticeSubcomponent
    extends AndroidInjector<MembershipRenewalDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<MembershipRenewalDialogFragment> {
  }
}
