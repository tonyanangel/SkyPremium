package com.skypremiuminternational.app.app.internal.di.membership_renewal;

import com.skypremiuminternational.app.app.features.membership_renewal.MembershipRenewalActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WIKI Toan Tran on 04/30/2020.
 */
@Subcomponent()
public interface MembershipRenewalSubcomponent extends AndroidInjector<MembershipRenewalActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MembershipRenewalActivity> {
    }
}
