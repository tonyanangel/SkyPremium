package com.skypremiuminternational.app.app.internal.di.reset_password;

import com.skypremiuminternational.app.app.features.reset_password.ResetPasswordActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface ResetPasswordSubcomponent
    extends AndroidInjector<ResetPasswordActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<ResetPasswordActivity> {
  }
}
