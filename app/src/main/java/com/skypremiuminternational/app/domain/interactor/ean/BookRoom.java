package com.skypremiuminternational.app.domain.interactor.ean;

import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BookRoom extends UseCase<BookingDetail, BookRoom.Params> {

  @Inject
  protected BookRoom(DataManager dataManager,
                     ThreadExecutor subscriberThread,
                     PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<BookingDetail> provideObservable(Params params) {
    return getDataManager().bookRoom(params);
  }

  public static class Params {

    public final PaymentDetail paymentDetail;
    public final List<BookerInfo> bookerInfos;
    public final String email;
    public final boolean isBookForSomeone;
    public final String bookingLink;
    public final String checkIn;
    public final String checkOut;
    public final String propertyId;
    public final String bedGroup;
    public final String bedGroups;
    public final String skyDollar;


    public Params(PaymentDetail paymentDetail,
                  List<BookerInfo> bookerInfos, String email, boolean isBookForSomeone,
                  String bookingLink, String checkIn, String checkOut, String propertyId,
                  String selectedBedGroup, String bedGroups, String skyDollar) {
      this.paymentDetail = paymentDetail;
      this.bookerInfos = bookerInfos;
      this.email = email;
      this.isBookForSomeone = isBookForSomeone;
      this.bookingLink = bookingLink;
      this.checkIn = checkIn;
      this.checkOut = checkOut;
      this.propertyId = propertyId;
      this.bedGroup = selectedBedGroup;
      this.bedGroups = bedGroups;
      this.skyDollar = skyDollar;
    }
  }
}
