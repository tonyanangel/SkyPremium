package com.skypremiuminternational.app.app.internal.di.profile;

import com.skypremiuminternational.app.app.features.profile.edit_profile.EditProfileActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface EditProfileSubcomponent
    extends AndroidInjector<EditProfileActivity> {

  @Subcomponent.Builder
  abstract class Builder
      extends AndroidInjector.Builder<EditProfileActivity> {
  }
}
