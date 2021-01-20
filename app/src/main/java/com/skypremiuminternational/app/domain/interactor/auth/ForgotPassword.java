package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.network.request.ForgotPasswordRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.signin.InvalidEmailException;
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

public class ForgotPassword extends UseCase<ResponseBody, ForgotPasswordRequest> {

  @Inject
  protected ForgotPassword(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(ForgotPasswordRequest params) {
    if (!Validator.isEmailValid(params.getEmail())) {
      return Observable.error(new InvalidEmailException());
    }
    return getDataManager().forgotPassword(params).doOnNext(new Action1<ResponseBody>() {
      @Override
      public void call(ResponseBody responseBody) {
        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
      }
    });
    /*return Single.create(new Single.OnSubscribe<ResponseBody>() {
      @Override public void call(SingleSubscriber<? super ResponseBody> singleSubscriber) {
        //getDataManager().saveToken();
        //singleSubscriber.onSuccess(Boolean.TRUE);
      }
    }).toObservable();*/
  }
}
