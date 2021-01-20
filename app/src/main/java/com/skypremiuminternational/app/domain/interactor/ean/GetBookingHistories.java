package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetBookingHistories extends UseCase<List<BookingHistory>, GetBookingHistories.Params> {

  @Inject
  protected GetBookingHistories(DataManager dataManager,
                                ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<BookingHistory>> provideObservable(Params params) {
    return getDataManager().getBookingHistories(params);
  }

  public static class Params {
    public final String sortDirection;
    public final String sortField;
    public final String status;

    public Params(String sortDirection, String sortField, String status) {
      this.sortDirection = sortDirection;
      this.sortField = sortField;
      this.status = status;
    }
  }
}
