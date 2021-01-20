package com.skypremiuminternational.app.domain.interactor.my_orders;

import com.skypremiuminternational.app.data.network.request.GetOrderHistoryRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.myOrder.MyOrderResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 2/9/18.
 */

public class GetOrderHistory extends UseCase<MyOrderResponse, GetOrderHistoryRequest> {
  @Inject
  protected GetOrderHistory(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<MyOrderResponse> provideObservable(
      GetOrderHistoryRequest getOrderHistoryRequest) {
    return getDataManager().getOrderHistoryFromAPI(getOrderHistoryRequest);
  }
}
