package com.skypremiuminternational.app.app.features.notification_setting;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;

public interface NotificationSettingView <T extends Presentable> extends Viewable<T> {
  void updateCartCount(String s);



  void showDialogLoading();
  void hideDialogLoading();


  void renderSetting(SettingNotificationResponse response);

  void renderSaveSetting();
}
