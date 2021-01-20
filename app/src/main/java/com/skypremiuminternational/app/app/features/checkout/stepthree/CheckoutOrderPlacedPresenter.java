package com.skypremiuminternational.app.app.features.checkout.stepthree;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.cart.CreateCart;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.user.GetOrderDetail;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aeindraaung on 2/4/18.
 */

public class CheckoutOrderPlacedPresenter extends BaseFragmentPresenter<CheckoutOrderPlacedView> {

  private final GetOrderDetail getOrderDetail;
  private final CreateCart createCart;
  private final CompositeSubscription compositeSubscription;
  private final GetISOCountryCodes getISOCountryCodes;
  private final GetCountryCodes getCountryCodes;

  @Inject
  public CheckoutOrderPlacedPresenter(GetOrderDetail getOrderDetail,
                                      GetISOCountryCodes getISOCountryCodes, CreateCart createCart,
                                      GetCountryCodes getCountryCodes) {
    this.getOrderDetail = getOrderDetail;
    this.getISOCountryCodes = getISOCountryCodes;
    this.createCart = createCart;
    this.getCountryCodes = getCountryCodes;
    this.compositeSubscription = new CompositeSubscription();
  }

  private void getOrderDetail(Integer orderId) {
    add(getOrderDetail.asObservable(new GetOrderDetail.Params(orderId)).subscribe(orderDetail -> {
      getView().hideLoading();
      getView().render(orderDetail);
    }, throwable -> {
      getView().hideLoading();
      getView().render(new Exception("Failed to get order detail"));
    }));
  }
  public void getCountryCodes(Integer orderId) {
    getView().showLoading();
    add(getISOCountryCodes.execute(new SingleSubscriber<List<ISOCountry>>() {
      @Override
      public void onSuccess(List<ISOCountry> value) {
        getView().render(value);
        getOrderDetail(orderId);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load country codes"));
      }
    } ));
  }
  /*public void getCountryCodes(Integer orderId) {
    getView().showLoading();
    add(getCountryCodes.execute(new SingleSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> value) {
        getView().render(value);
        getOrderDetail(orderId);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    }));
  }*/



  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }
}
