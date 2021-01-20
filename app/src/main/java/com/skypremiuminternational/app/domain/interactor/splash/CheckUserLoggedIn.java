package com.skypremiuminternational.app.domain.interactor.splash;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.signin.UserNotFoundException;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 7/11/17.
 */

public class CheckUserLoggedIn extends UseCase<Boolean, Void> {

  @Inject
  protected CheckUserLoggedIn(DataManager dataManager, ThreadExecutor subscriberThread,
                              PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Boolean> provideObservable(Void aVoid) {
    if (getDataManager().getUserToken().equals("Bearer ")) {
      return Observable.error(new UserNotFoundException());
    } else {
      return Observable.just(true);
    }
  }
}
