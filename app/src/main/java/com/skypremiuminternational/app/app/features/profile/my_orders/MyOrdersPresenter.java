package com.skypremiuminternational.app.app.features.profile.my_orders;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.GetOrderHistoryRequest;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.my_orders.GetOrderHistory;
import com.skypremiuminternational.app.domain.interactor.splash.CheckUserLoggedIn;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderResponse;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class MyOrdersPresenter extends BasePresenter<MyOrdersView> {

  private final GetOrderHistory getOrderHistory;

  private GetOrderHistoryRequest filterRequest;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public MyOrdersPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                           GetOrderHistory getOrderHistory) {
    super(getAppVersion, internalStorageManager);
    this.getOrderHistory = getOrderHistory;
  }

  public void getOrderHistory() {
    getView().showLoading("Loading ...");
    add(getOrderHistory.execute(new SingleSubscriber<MyOrderResponse>() {
      @Override
      public void onSuccess(MyOrderResponse value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(new Exception("Failed to load order history"));
      }
    }, filterRequest));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void setRequest(String filterSorting, String filterCategory) {
    if (filterCategory.equalsIgnoreCase("all")) {
      filterRequest = GetOrderHistoryRequest.getAll(filterSorting);
    } else {
      filterRequest = GetOrderHistoryRequest.getWithFILTER(filterCategory, filterSorting);
    }
  }
}
