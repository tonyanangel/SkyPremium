package com.skypremiuminternational.app.app.internal.di.notification_setting;

import com.skypremiuminternational.app.app.features.notification_setting.NotificationSettingActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


@Subcomponent
public interface NotificationSettingSubcomponent extends AndroidInjector<NotificationSettingActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<NotificationSettingActivity> {
  }

}