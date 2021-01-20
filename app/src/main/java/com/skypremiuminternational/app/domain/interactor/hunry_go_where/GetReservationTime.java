
package com.skypremiuminternational.app.domain.interactor.hunry_go_where;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;

import javax.inject.Inject;

import rx.Observable;

public class GetReservationTime extends UseCase<ReservationTimeResponse, GetReservationTime.Params> {

  @Inject
  protected GetReservationTime(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ReservationTimeResponse> provideObservable(Params params) {
    return getDataManager().getReservationTime(params.date, params.restaurantId);
  }

  public static class Params {
    String date;
    String restaurantId;

    public Params(String date, String restaurantId) {
      this.date = date;
      this.restaurantId = restaurantId;
    }
  }

}
