package com.skypremiuminternational.app.domain.interactor.hunry_go_where;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.RestaurantMessageResponse;

import javax.inject.Inject;

import rx.Observable;

public class GetRestaurantMsg  extends UseCase<RestaurantMessageResponse, String> {

  @Inject
  protected GetRestaurantMsg(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<RestaurantMessageResponse> provideObservable(String restaurantId) {
    return getDataManager().getRestaurantMsg(restaurantId);
  }


}
