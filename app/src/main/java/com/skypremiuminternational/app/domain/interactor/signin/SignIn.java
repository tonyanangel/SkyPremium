package com.skypremiuminternational.app.domain.interactor.signin;

import android.util.Log;

import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.network.request.SignInRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.signin.InvalidEmailException;
import com.skypremiuminternational.app.domain.exception.signin.InvalidPasswordException;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by hein on 5/19/17.
 */

public class SignIn extends UseCase<ResponseBody, SignInRequest> {

  @Inject
  protected SignIn(DataManager dataManager, ThreadExecutor subscriberThread,
                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(SignInRequest params) {
    if (!Validator.isEmailValid(params.getUsername())) {
      return Observable.error(new InvalidEmailException());
    }
    if (!Validator.isPasswordValid(params.getPassword())) {
      return Observable.error(new InvalidPasswordException());
    }
    return getDataManager().signIn(params).doOnNext(new Action1<ResponseBody>() {
      @Override
      public void call(ResponseBody responseBody) {
        try {
          String token = responseBody.string();
          getDataManager().saveUserToken(token);

        } catch (IOException e) {
          e.printStackTrace();
        }
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
