package com.skypremiuminternational.app.domain.interactor.hunry_go_where;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class SendCreateReservation extends UseCase<ReservationResultResponse, Map<String, String>> {

  @Inject
  protected SendCreateReservation(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ReservationResultResponse> provideObservable(Map<String, String> mapRequest) {
    return getDataManager().sendCreateReservation(mapRequest);
  }
}