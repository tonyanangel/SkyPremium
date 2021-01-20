package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class CancelBooking extends UseCase<List<BookingHistory>, CancelBooking.Params> {

  private final GetBookingHistories getBookingHistories;

  @Inject
  protected CancelBooking(DataManager dataManager,
                          ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread,
                          GetBookingHistories getBookingHistories) {
    super(dataManager, subscriberThread, observerThread);
    this.getBookingHistories = getBookingHistories;
  }

  @Override
  public Observable<List<BookingHistory>> provideObservable(Params params) {
    return getDataManager().cancelBooking(params.bookingId)
        .flatMap(ignored -> getBookingHistories.asObservable(
            new GetBookingHistories.Params(params.sortDirection, params.sortField, params.status)));
  }

  public static class Params {
    public final String bookingId;
    public final String sortDirection;
    public final String sortField;
    public final String status;

    public Params(String bookingId, String sortDirection, String sortField, String status) {
      this.bookingId = bookingId;
      this.sortDirection = sortDirection;
      this.sortField = sortField;
      this.status = status;
    }
  }
}
