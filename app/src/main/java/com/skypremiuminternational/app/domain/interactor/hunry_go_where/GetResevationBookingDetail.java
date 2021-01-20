package com.skypremiuminternational.app.domain.interactor.hunry_go_where;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReserveHistoryItem;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class GetResevationBookingDetail extends UseCase<ReserveHistoryItem,String> {

  @Inject
  protected GetResevationBookingDetail(DataManager dataManager, ThreadExecutor subscriberThread,
                                  PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ReserveHistoryItem> provideObservable(String bookingId) {
    return getDataManager().getDetailReservationBooking(bookingId);
  }
}