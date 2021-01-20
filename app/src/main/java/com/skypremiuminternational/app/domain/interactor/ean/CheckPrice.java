package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckResult;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckSummary;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class CheckPrice extends UseCase<PriceCheckResult, CheckPrice.Params> {

  @Inject
  protected CheckPrice(DataManager dataManager,
                       ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<PriceCheckResult> provideObservable(Params params) {
    return getDataManager().checkPrice(params);
  }

  public static class Params {
    public final int roomCount;
    public final int adultCount;
    public final List<Child> children;
    public final String bookingLink;

    public Params(int roomCount, int adultCount,
                  List<Child> children,
                  String bookingLink) {
      this.roomCount = roomCount;
      this.adultCount = adultCount;
      this.children = children;
      this.bookingLink = bookingLink;
    }
  }
}
