package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class SendNotification extends UseCase<ResponseBody, SendNotification.Params> {
  @Inject
  protected SendNotification(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(SendNotification.Params params) {
    return getDataManager().sendNotification(params.oAuth,params.request);
  }


  public static class Params{
    String oAuth;
    NotificationRequest request;


    public Params(String oAuth, NotificationRequest request) {
      this.oAuth = oAuth;
      this.request = request;
    }
  }
}
