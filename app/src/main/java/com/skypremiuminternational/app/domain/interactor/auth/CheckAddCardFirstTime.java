package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.data.network.request.RenewalRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class CheckAddCardFirstTime extends UseCase<Boolean, Void> {
  @Inject
  protected CheckAddCardFirstTime(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }


  @Override
  public Observable<Boolean> provideObservable( Void d) {
    return getDataManager().checkAddCardFirstTime();
  }
}
