package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 3/5/18.
 */

public class DetailDeliveryAddress
    extends UseCase<OrderDetailResponse, DetailDeliveryAddress.Params> {
  @Inject
  protected DetailDeliveryAddress(DataManager dataManager,
                                  ThreadExecutor subscriberThread,
                                  PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<OrderDetailResponse> provideObservable(Params params) {
    return getDataManager().getOrderDetail(String.valueOf(params.addressId));
  }

  public static class Params {
    public final int addressId;

    public Params(int addressId) {
      this.addressId = addressId;
    }
  }
}
