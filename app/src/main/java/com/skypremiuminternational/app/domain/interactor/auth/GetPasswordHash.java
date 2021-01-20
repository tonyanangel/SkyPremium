package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.network.request.PasswordHashRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.signin.InvalidPasswordException;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by hein on 5/19/17.
 */

public class GetPasswordHash extends UseCase<ResponseBody, PasswordHashRequest> {

  @Inject
  protected GetPasswordHash(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(PasswordHashRequest params) {
    if (!Validator.isPasswordValid(params.getPassword())) {
      return Observable.error(new InvalidPasswordException());
    }

    return getDataManager().getPasswordHash(params).doOnNext(new Action1<ResponseBody>() {
      @Override
      public void call(ResponseBody responseBody) {

      }
    });
  }
}
