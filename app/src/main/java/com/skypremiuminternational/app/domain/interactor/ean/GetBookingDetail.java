package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

public class GetBookingDetail extends UseCase<BookingDetail, GetBookingDetail.Params> {

  @Inject
  protected GetBookingDetail(DataManager dataManager,
                             ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<BookingDetail> provideObservable(Params params) {
    return getDataManager().getBookingDetail(params);
  }

  public static class Params {
    public final String bookingId;
    public final ArrayList<BookingHistory.Room> rooms;

    public Params(String bookingId,
                  ArrayList<BookingHistory.Room> rooms) {
      this.bookingId = bookingId;
      this.rooms = rooms;
    }
  }
}
