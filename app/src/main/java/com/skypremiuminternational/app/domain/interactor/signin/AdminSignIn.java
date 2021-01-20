package com.skypremiuminternational.app.domain.interactor.signin;

import com.skypremiuminternational.app.data.network.request.AdminSignInRequest;
import com.skypremiuminternational.app.domain.DataManager;
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

public class AdminSignIn extends UseCase<ResponseBody, AdminSignInRequest> {

  @Inject
  protected AdminSignIn(DataManager dataManager, ThreadExecutor subscriberThread,
                        PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(AdminSignInRequest params) {
    return getDataManager().adminSignIn(params).doOnNext(new Action1<ResponseBody>() {
      @Override
      public void call(ResponseBody responseBody) {
        try {
          String token = responseBody.string();
          getDataManager().saveAdminToken(token);
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
