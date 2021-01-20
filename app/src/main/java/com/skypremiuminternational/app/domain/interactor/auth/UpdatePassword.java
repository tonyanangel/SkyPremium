package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.data.network.request.UpdatePasswordRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by hein on 5/19/17.
 */

public class UpdatePassword extends UseCase<ResponseBody, UpdatePasswordRequest> {

  @Inject
  protected UpdatePassword(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(UpdatePasswordRequest params) {
    //if (!Validator.isPasswordValid(params.getCurrent_password())) {
    //  return Observable.error(new InvalidPasswordException());
    //}
    /*if (!Validator.isPasswordValid(params.getNew_password())) {
      return Observable.error(new InvalidPasswordException());
    }
    if (!Validator.isPasswordValid(params.getConfirm_password())) {
      return Observable.error(new InvalidPasswordException());
    }*/
    //if (!params.getNew_password().equals(params.getConfirm_password())) {
    //  return Observable.error(new PasswordDoNotMatchException());
    //}
    //String salt = BCrypt.gensalt();
    //String hashedPassword = BCrypt.hashpw(params.getNew_password(), salt);
    //params.setPasswordHash(hashedPassword + ":" + salt + ":1");

    return getDataManager().updatePassword(params).doOnNext(new Action1<ResponseBody>() {
      @Override
      public void call(ResponseBody responseBody) {
        try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
