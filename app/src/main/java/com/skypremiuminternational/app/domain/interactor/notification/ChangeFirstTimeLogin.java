

package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationDeleteRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class ChangeFirstTimeLogin extends UseCase<ResponseBody, ChangeFirstTimeLogin.Params> {

  @Inject
  protected ChangeFirstTimeLogin(DataManager dataManager, ThreadExecutor subscriberThread,
                                 PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(ChangeFirstTimeLogin.Params params) {
    return getDataManager().changeFirstTimeLogin(params.oAuth, params.request);
  }

  public static class Params {
    String oAuth;
    FirstTimeToTrueRequest request;

    public Params(String oAuth, FirstTimeToTrueRequest request) {
      this.oAuth = oAuth;
      this.request = request;
    }
  }
}


