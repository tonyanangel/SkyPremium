package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.NotificationDeleteRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationUpdateRequest;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class DeleteNotifications  extends UseCase<ResponseBody, DeleteNotifications.Params> {

  @Inject
  protected DeleteNotifications(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(DeleteNotifications.Params params) {
    return getDataManager().deleteNotification(params.oAuth, params.request);
  }


  public static class Params {
    String oAuth;
    NotificationDeleteRequest request;


    public Params(String oAuth, NotificationDeleteRequest request) {
      this.oAuth = oAuth;
      this.request = request;
    }
  }
}