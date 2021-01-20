package com.skypremiuminternational.app.domain.interactor.notification;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationUpdateRequest;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class ReadNotifications  extends UseCase<ResponseBody, ReadNotifications.Params> {

  @Inject
  public ReadNotifications(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(ReadNotifications.Params params) {
    return getDataManager().updateReadNotification(params.oAuth, params.request);
  }


  public static class Params {
    String oAuth;
    NotificationUpdateRequest  request;

    public Params(String oAuth, NotificationUpdateRequest request) {
      this.oAuth = oAuth;
      this.request = request;
    }
  }
}
