package com.skypremiuminternational.app.app.internal.di.signin;

import com.skypremiuminternational.app.app.features.signin.SignInActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface SignInSubcomponent extends AndroidInjector<SignInActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SignInActivity> {
  }
}
