package com.skypremiuminternational.app.app.internal.di.demo;

import com.skypremiuminternational.app.app.features.demo_two_activity.DemoTwoActivity;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface DemoTwoSubcomponent extends AndroidInjector<DemoTwoActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DemoTwoActivity> {
    }
}
