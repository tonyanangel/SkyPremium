package com.skypremiuminternational.app.app.internal.di.demo;

import com.skypremiuminternational.app.app.features.demo3_activity.DemoThreeActivity;
import com.skypremiuminternational.app.app.features.demo_activity.DemoActvity;
import com.skypremiuminternational.app.app.features.hunry_go_where.detail.DetailsResevationActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


@Subcomponent()
public interface DemoThreeSubcomponent extends AndroidInjector<DemoThreeActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DemoThreeActivity> {
    }
}