package com.skypremiuminternational.app.app.features.notifications;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;

import java.util.List;

public interface NotificationView  <T extends Presentable> extends Viewable<T> {




  void renderListNotification(List<NotificationItem> list);

  void showDialogLoading();

  void hideDialogLoading();

  void renderGoToDetails(NotificationItem item);

  void onExistEstoreProduct(String redirect,String sku);

  void onNonExistEstoreProduct(String redirect,String filter,String sortBy,String page);

  void goToInviteFriend(String code, String salutation, String firstname, String lastname,
                        String description,String imgBannerUrl);
  void renderChangeListNotification(List<NotificationItem> items);

  void forceLogin();
}
