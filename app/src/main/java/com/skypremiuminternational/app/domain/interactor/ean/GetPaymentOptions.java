package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetPaymentOptions extends UseCase<List<CardOption>, GetPaymentOptions.Params> {

  public static final String CARD_TYPE_AMERICAN_EXPRESS = "AX";
  public static final String CARD_TYPE_VISA = "VI";
  public static final String CARD_TYPE_MASTER_CARD = "CA";
  private static final List<String> supportedCardTypes = new ArrayList<>();

  static {
    supportedCardTypes.add(CARD_TYPE_AMERICAN_EXPRESS);
    supportedCardTypes.add(CARD_TYPE_MASTER_CARD);
    supportedCardTypes.add(CARD_TYPE_VISA);
  }

  @Inject
  protected GetPaymentOptions(DataManager dataManager,
                              ThreadExecutor subscriberThread,
                              PostExecutionThread observerThread) {

    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<CardOption>> provideObservable(Params params) {
    return getDataManager().getPaymentOptions(params)
        .flatMapIterable(cardOptions -> cardOptions)
        //.filter(cardOption -> isSupported(cardOption.getCardType()))
        .toList();
  }

  private boolean isSupported(String cardType) {
    return supportedCardTypes.contains(cardType);
  }

  public static class Params {
    public final String paymentOptionLink;

    public Params(String paymentOptionLink) {
      this.paymentOptionLink = paymentOptionLink;
    }
  }
}
