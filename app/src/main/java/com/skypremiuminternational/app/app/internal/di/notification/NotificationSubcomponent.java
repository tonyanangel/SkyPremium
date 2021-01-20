package com.skypremiuminternational.app.app.internal.di.notification;


import com.skypremiuminternational.app.app.features.notifications.NotificationActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface NotificationSubcomponent extends AndroidInjector<NotificationActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<NotificationActivity> {
  }

}