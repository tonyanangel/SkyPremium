package com.skypremiuminternational.app.app.internal.di.forgot_password;

import com.skypremiuminternational.app.app.features.forgot_password.success.ForgotPasswordSuccessDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface ForgotPasswordSuccessSubcomponent
    extends AndroidInjector<ForgotPasswordSuccessDialogFragment> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ForgotPasswordSuccessDialogFragment> {
  }
}
