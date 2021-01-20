package com.skypremiuminternational.app.domain.interactor.user;


import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.CardInfomationResponse;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetCreditCardInfo extends UseCase<CardInfomationResponse, String> {
  @Inject
  protected GetCreditCardInfo(DataManager dataManager,
                             ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CardInfomationResponse> provideObservable(String cardId) {
    return getDataManager().getCreditCardInfo(cardId);
  }
}
