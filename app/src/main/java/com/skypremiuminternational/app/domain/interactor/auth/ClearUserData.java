package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;

/**
 * Created by hein on 5/19/17.
 */

public class ClearUserData extends UseCase<Boolean, Void> {

  @Inject
  protected ClearUserData(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Boolean> provideObservable(Void aVoid) {
    return Single.create(new Single.OnSubscribe<Boolean>() {
      @Override
      public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
        getDataManager().clearAuthToken();
        getDataManager().removeCartId();
        singleSubscriber.onSuccess(Boolean.TRUE);
      }
    }).toObservable();
  }
}
