package com.skypremiuminternational.app.app.internal.di.forgot_password;

import com.skypremiuminternational.app.app.features.forgot_password.ForgotPasswordActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface ForgotPasswordSubcomponent
    extends AndroidInjector<ForgotPasswordActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ForgotPasswordActivity> {
  }
}
