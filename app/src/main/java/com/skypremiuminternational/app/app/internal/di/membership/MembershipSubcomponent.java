package com.skypremiuminternational.app.app.internal.di.membership;

import com.skypremiuminternational.app.app.features.memership_services.MembershipActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by hein on 5/9/17.
 */
@Subcomponent()
public interface MembershipSubcomponent
    extends AndroidInjector<MembershipActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<MembershipActivity> {
  }
}
