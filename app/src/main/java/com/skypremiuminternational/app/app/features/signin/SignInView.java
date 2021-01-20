package com.skypremiuminternational.app.app.features.signin;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public interface SignInView<T extends Presentable> extends Viewable<T> {

  void render(SignInViewState viewState);

  void renderSetting(SettingNotificationResponse response);

  void renderGotoLanding();

  void renderGotoLanding(boolean isFirstLogin);

  void showDialogLoading(String msg);

  void hideDialogLoading();
}
