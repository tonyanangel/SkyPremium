package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import javax.inject.Inject;

import rx.Observable;

public class PlaceRenewalOrder extends UseCase<Integer, PlaceRenewalOrder.Params> {

  @Inject
  protected PlaceRenewalOrder(DataManager dataManager,
                              ThreadExecutor subscriberThread,
                              PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Integer> provideObservable(Params params) {
    if (params.checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_ESTORE) {
      throw new IllegalStateException("Checkout type " + params.checkoutType + " is not supported");
    }
    return getDataManager().placeRenewalOrder(params).map(Integer::parseInt);
  }

  public static class Params {
    public final int checkoutType;
    public final String email;
    public final BillingAddress address;
    public final CreditCardResponse creditCard;
    public final String paymentType;

    public Params(int checkoutType, String email,
                  BillingAddress address,
                  CreditCardResponse creditCard, String paymentType) {
      this.checkoutType = checkoutType;
      this.email = email;
      this.address = address;
      this.creditCard = creditCard;
      this.paymentType = paymentType;
    }
  }
}
