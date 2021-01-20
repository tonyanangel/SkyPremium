package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by codigo on 26/2/18.
 */

public class CheckLimit extends UseCase<Boolean, Void> {
  @Inject
  protected CheckLimit(DataManager dataManager, ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Boolean> provideObservable(Void aVoid) {
    return getDataManager().checkIfCartHasReachedLimit()
        .flatMap((Func1<CheckLimitResponse, Observable<Boolean>>) checkLimitResponse -> {
          if (checkLimitResponse.status_limit) {
            return Observable.error(
                new CartLimitException(checkLimitResponse.limit_errors));
          }
          return Observable.just(Boolean.TRUE);
        });
  }
}
