package com.skypremiuminternational.app.domain.interactor.notification;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.notification.UpdateMappVersionRequest;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class UpdateMappVersion  extends UseCase<ResponseBody, String>  {

  @Inject
  protected UpdateMappVersion(DataManager dataManager, ThreadExecutor subscriberThread,
                                 PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(String oAuth) {
    return getDataManager().updateMappVersion(oAuth);
  }


}
