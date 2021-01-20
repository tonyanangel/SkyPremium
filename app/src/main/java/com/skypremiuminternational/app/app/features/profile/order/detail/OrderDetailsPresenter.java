package com.skypremiuminternational.app.app.features.profile.order.detail;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.user.GetOrderDetail;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wmw on 2/16/2018.
 */

public class OrderDetailsPresenter extends BasePresenter<OrderDetailsView> {
  private final GetISOCountryCodes getISOCountryCodes;
  private GetOrderDetail getOrderDetail;
  private final CompositeSubscription compositeSubscription;
  private List<ISOCountry> countryCodeList;

  @Inject
  public OrderDetailsPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                               GetOrderDetail getOrderDetail, GetISOCountryCodes getISOCountryCodes) {
    super(getAppVersion, internalStorageManager);
    this.getOrderDetail = getOrderDetail;
    this.getISOCountryCodes = getISOCountryCodes;
    compositeSubscription = new CompositeSubscription();
  }

  public void getOrderDetail(int orderId) {
    getView().showLoading("Getting the order detail...");
    add(getOrderDetail.execute(new SingleSubscriber<OrderDetailResponse>() {
      @Override
      public void onSuccess(OrderDetailResponse value) {
        getView().hideLoading();
        getView().render(countryCodeList);
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load order detail"));
      }
    }, new GetOrderDetail.Params(orderId)));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getCountry(int orderId) {
    getView().showLoading("Getting the order detail...");
    add(getISOCountryCodes.execute(new SingleSubscriber<List<ISOCountry>>() {
      @Override
      public void onSuccess(List<ISOCountry> value) {
        OrderDetailsPresenter.this.countryCodeList = value;
        getOrderDetail(orderId);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load country codes"));
      }
    } ));
  }
}
