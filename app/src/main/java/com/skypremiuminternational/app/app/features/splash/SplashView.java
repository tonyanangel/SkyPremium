package com.skypremiuminternational.app.app.features.splash;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface SplashView<T extends Presentable> extends Viewable<T> {

  void render(SplashViewState viewState);

  void renderSetting(SettingNotificationResponse response);


  void renderGotoLanding();

  void renderGotoLanding(boolean isFirstLogin);
}
