package com.skypremiuminternational.app.app.internal.di.demo;

import com.skypremiuminternational.app.app.features.demo_activity.DemoActvity;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WIKI Toan Tran on 2020 11 18.
 */
@Subcomponent()
public interface DemoSubcomponent extends AndroidInjector<DemoActvity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DemoActvity> {
    }
}
