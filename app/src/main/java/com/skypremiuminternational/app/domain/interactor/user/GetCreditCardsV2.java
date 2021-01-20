package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;


public class GetCreditCardsV2 extends UseCase<List<CreditCardResponse>, Void> {
  @Inject
  protected GetCreditCardsV2(DataManager dataManager,
                           ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<CreditCardResponse>> provideObservable(Void aVoid) {
    return getDataManager().getCreditCardsV2();
  }
}
