package com.skypremiuminternational.app.app.features.booking.detail;

import android.text.Html;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.model.BookingHistory;
import com.skypremiuminternational.app.app.utils.URLUtils;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.mapper.BookingHistoryMapper;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.data.model.ean.booking.history.BookingData;
import com.skypremiuminternational.app.data.model.ean.booking.history.CancelsPenalties;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.ean.CancelBooking;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetail;
import com.skypremiuminternational.app.domain.interactor.ean.GetBookingDetailValue;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BookingDetailPresenter extends BasePresenter<BookingDetailView> {

  private final GetBookingDetail getBookingDetail;
  private final GetBookingDetailValue getBookingDetailvalue;
  private final CancelBooking cancelBooking;


  private CompositeSubscription compositeSubscription;
  private BookingHistoryMapper bookingHistoryMapper;


  @Inject
  public BookingDetailPresenter(
      GetAppVersion getAppVersion,
      InternalStorageManager internalStorageManager, GetBookingDetail getBookingDetail, GetBookingDetailValue getBookingDetailValue,CancelBooking cancelBooking,BookingHistoryMapper bookingHistoryMapper) {
    super(getAppVersion, internalStorageManager);
    this.getBookingDetail = getBookingDetail;
    this.getBookingDetailvalue = getBookingDetailValue;
    this.cancelBooking = cancelBooking;
    this.bookingHistoryMapper = bookingHistoryMapper;
    compositeSubscription = new CompositeSubscription();
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
   * 20200229 WIKI Nhat Nguyen - get booking detail
   * @param bookingId
   * @param rooms
   */
  public void getBookingDetailvalue(String bookingId,
                               ArrayList<BookingHistory.Room> rooms) {
   // getView().showLoading();
    add(getBookingDetailvalue.execute(new SingleSubscriber<Booking>() {
      @Override
      public void onSuccess(Booking value) {


        List<BookingData> bookingDataList =  bookingHistoryMapper.mapBookingDatas(value.getBookingData());

        if (bookingDataList != null && bookingDataList.size() > 0) {

          if(bookingDataList.get(0).getCancelsPenaltiesList().size()>1){

            if(Validator.isTextValid(bookingDataList.get(0).getCancelsPenaltiesList().get(0).getStart())){
              getView().render(bookingDataList.get(0));
            }else {

              getView().render(bookingDataList.get(0));
            }

          }else{

           // getView().hideLoading();

            if(Validator.isTextValid(bookingDataList.get(0).getCancelsPenaltiesList().get(0).getStart())){
              getView().render(bookingDataList.get(0));
            }else {

              getView().render(bookingDataList.get(0));
            }
          }

        }
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
       // getView().render(error);
      }
    }, new GetBookingDetailValue.Params(bookingId, rooms)));
  }

  public void cancelBooking(String bookingId, String status, String sortingField,
                            String sortingDirection) {
    add(cancelBooking.execute(new SingleSubscriber<List<BookingHistory>>() {
      @Override
      public void onSuccess(List<BookingHistory> value) {
        getView().render(value,true);
      }

      @Override
      public void onError(Throwable error) {
        getView().render(null,false);
        getView().render(new Exception("Failed to cancel the booking"));
      }
    }, new CancelBooking.Params(bookingId, sortingDirection, sortingField, status)));
  }



  public void getBookingDetail(String bookingId,
                               ArrayList<BookingHistory.Room> rooms) {
    getView().showLoading();
    add(getBookingDetail.execute(new SingleSubscriber<BookingDetail>() {
      @Override
      public void onSuccess(BookingDetail value) {
        getView().hideLoading();
        getView().render(value);
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
        getView().hideLoading();
        getView().render(error);
      }
    }, new GetBookingDetail.Params(bookingId, rooms)));
  }
}
