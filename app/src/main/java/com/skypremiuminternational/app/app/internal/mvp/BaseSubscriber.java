package com.skypremiuminternational.app.app.internal.mvp;

import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.domain.exception.signin.SessionTimeoutException;

import retrofit2.HttpException;
import rx.SingleSubscriber;

/**
 * Created by johnsonmaung on 9/4/17.
 */

public abstract class BaseSubscriber<T> extends SingleSubscriber<T> {

  @Override
  public void onError(Throwable error) {
    error.printStackTrace();
    if (error instanceof HttpException) {
      if (((HttpException) error).code() == 401) {
        RxBus.get().post(new SessionTimeoutException());
      }
    }
  }
}
