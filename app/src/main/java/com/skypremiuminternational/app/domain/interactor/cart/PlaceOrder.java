package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 27/2/18.
 */

public class PlaceOrder extends UseCase<Integer, PlaceOrder.Params> {

  @Inject
  protected PlaceOrder(DataManager dataManager, ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Integer> provideObservable(final Params params) {
    return getDataManager().placeOrder(params).map(Integer::parseInt);
  }

  public static class Params {
    public final String email;
    public final BillingAddress address;
    public final CreditCardResponse creditCard;
    public final String paymentType;

    public Params(String email, BillingAddress address, CreditCardResponse creditCard,
                  String paymentType) {
      this.email = email;
      this.paymentType = paymentType;
      this.address = address;
      this.creditCard = creditCard;
    }
  }
}
