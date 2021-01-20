package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.NotificationDeleteRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class GetNotificationSetting extends UseCase<SettingNotificationResponse, String> {


  @Inject
  protected GetNotificationSetting(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<SettingNotificationResponse> provideObservable(String oAuth) {
    return getDataManager().getNotificationSetting(oAuth);
  }

}
