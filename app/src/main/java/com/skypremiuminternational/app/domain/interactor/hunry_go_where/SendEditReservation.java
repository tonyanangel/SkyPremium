package com.skypremiuminternational.app.domain.interactor.hunry_go_where;


import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class SendEditReservation extends UseCase<ReservationResultResponse, SendEditReservation.Params> {

  @Inject
  protected SendEditReservation(DataManager dataManager, ThreadExecutor subscriberThread,
                                  PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ReservationResultResponse> provideObservable(SendEditReservation.Params params) {
    return getDataManager().sendEditReservation(params.map, params.reservationId, params.verificationKey);
  }

  public static class Params{


    Map<String, String>  map;
    String reservationId;
    String verificationKey;


    public Params(Map<String, String>  map,String reservationId, String verificationKey) {
      this.map = map;
      this.reservationId = reservationId;
      this.verificationKey = verificationKey;
    }
  }
}