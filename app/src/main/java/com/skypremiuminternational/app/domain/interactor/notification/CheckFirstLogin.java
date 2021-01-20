package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class CheckFirstLogin extends UseCase<CheckLoginResponse, String> {

  @Inject
  protected CheckFirstLogin(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CheckLoginResponse> provideObservable(String oAuth) {
    return getDataManager().checkFirstLogin(oAuth);
  }

}
