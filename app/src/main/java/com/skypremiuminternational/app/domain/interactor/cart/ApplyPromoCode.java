package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 24/2/18.
 */

public class ApplyPromoCode extends UseCase<Boolean, ApplyPromoCode.Params> {

  @Inject
  protected ApplyPromoCode(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Boolean> provideObservable(Params params) {
    return getDataManager().applyPromoCode(params);
  }

  public static class Params {
    public final String promoCode;

    public Params(String promoCode) {
      this.promoCode = promoCode;
    }
  }
}
