package com.skypremiuminternational.app.app.features.booking.history;

import android.util.Log;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.mapper.BookingHistoryMapper;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.ean.CancelBooking;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetailValue;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingHistories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BookingHistoryPresenter extends BasePresenter<BookingHistoryView> {

  private final GetBookingHistories getBookingHistories;
  private final CancelBooking cancelBooking;
  private CompositeSubscription compositeSubscription;
  private final GetBookingDetailValue getBookingDetailvalue;
  private BookingHistoryMapper bookingHistoryMapper;

  @Inject
  public BookingHistoryPresenter(GetAppVersion getAppVersion,
                                 InternalStorageManager internalStorageManager, GetBookingHistories getBookingHistories,
                                 CancelBooking cancelBooking,GetBookingDetailValue getBookingDetailvalue,BookingHistoryMapper bookingHistoryMapper) {
    super(getAppVersion, internalStorageManager);
    this.cancelBooking = cancelBooking;
    compositeSubscription = new CompositeSubscription();
    this.getBookingHistories = getBookingHistories;
    this.getBookingDetailvalue = getBookingDetailvalue;
    this.bookingHistoryMapper = bookingHistoryMapper;
  }

  public void getBookingHistories(String status, String sortingField, String sortingDirection) {
    getView().showLoading();
    add(getBookingHistories.execute(new SingleSubscriber<List<BookingHistory>>() {
      @Override
      public void onSuccess(List<BookingHistory> bookingHistories) {
        getView().hideLoading();
        getView().render(bookingHistories);
//        Log.d("skyHistory",bookingHistories.toString());
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
        getView().hideLoading();
        getView().render(error);
      }
    }, new GetBookingHistories.Params(sortingDirection, sortingField, status)));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }
  /**
   * 20200310 WIKI Nhat Nguyen - get bookingdetail value for cancel button
   * @param bookingId
   * @param rooms
   * @param pos
   */
  public void getBookingDetailvalueview(String bookingId,
                                    ArrayList<BookingHistory.Room> rooms,int pos) {
    getView().showLoading();
    add(getBookingDetailvalue.execute(new SingleSubscriber<Booking>() {
      @Override
      public void onSuccess(Booking value) {



        List<BookingData> bookingDataList =  bookingHistoryMapper.mapBookingDatas(value.getBookingData());

        if (bookingDataList != null && bookingDataList.size() > 0) {

          if(bookingDataList.get(0).getCancelsPenaltiesList().size()>1){
            getView().hideLoading();

            if(Validator.isTextValid(bookingDataList.get(0).getCancelsPenaltiesList().get(0).getStart())){
              getView().render(bookingDataList.get(0),true,bookingId,pos,rooms);
            }else {

              getView().render(bookingDataList.get(0),true,bookingId,pos,rooms);
            }

          }else{

            getView().hideLoading();


             getView().render(bookingDataList.get(0),true,bookingId,pos,rooms);
          }

        }
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
        getView().hideLoading();
        getView().render(null,false,bookingId,pos,rooms);
        getView().render(error);
      }
    }, new GetBookingDetailValue.Params(bookingId, rooms)));
  }
  /**
   * 20200310 WIKI Nhat Nguyen - get bookingdetail value to pass to detail activity
   * @param bookingId
   * @param rooms
   * @param pos
   */
  public void getBookingDetailvalue(String bookingId,
                                    ArrayList<BookingHistory.Room> rooms,int pos) {
     getView().showLoading();
    add(getBookingDetailvalue.execute(new SingleSubscriber<Booking>() {
      @Override
      public void onSuccess(Booking value) {



        List<BookingData> bookingDataList =  bookingHistoryMapper.mapBookingDatas(value.getBookingData());

        if (bookingDataList != null && bookingDataList.size() > 0) {

          if(bookingDataList.get(0).getCancelsPenaltiesList().size()>1){
            getView().hideLoading();

            if(Validator.isTextValid(bookingDataList.get(0).getCancelsPenaltiesList().get(0).getStart())){
              getView().render(bookingDataList.get(0),true,bookingId,pos);
            }else {

              getView().render(bookingDataList.get(0),true,bookingId,pos);
            }

          }else{

             getView().hideLoading();

              getView().render(bookingDataList.get(0),true,bookingId,pos);
          }

        }
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
        getView().hideLoading();
        getView().render(null,false,bookingId,pos);
        // getView().render(error);
      }
    }, new GetBookingDetailValue.Params(bookingId, rooms)));
  }

  public void cancelBooking(String bookingId, String status, String sortingField,
                            String sortingDirection) {

    add(cancelBooking.execute(new SingleSubscriber<List<BookingHistory>>() {
      @Override
      public void onSuccess(List<BookingHistory> value) {

        getView().render(value);
        getView().render(true,status);
      }

      @Override
      public void onError(Throwable error) {

        getView().render(false,status);
        getView().render(new Exception("Failed to cancel the booking"));
      }
    }, new CancelBooking.Params(bookingId, sortingDirection, sortingField, status)));
  }
}
