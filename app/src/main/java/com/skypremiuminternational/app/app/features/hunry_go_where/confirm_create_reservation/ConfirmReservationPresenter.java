package com.skypremiuminternational.app.app.features.hunry_go_where.confirm_create_reservation;

import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerView;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.ReservationRequest;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.CancelReservation;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetReservationTime;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.SendCreateReservation;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.SendEditReservation;
import com.skypremiuminternational.app.domain.models.hunry_go_where.CancelReservationResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationResultResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ConfirmReservationPresenter  extends BaseFragmentPresenter<ConfirmReservationView> {


  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  InternalStorageManager internalStorageManager;
  private final GetReservationTime getReservationTime;
  private final SendCreateReservation sendCreateReservation;
  private final SendEditReservation sendEditReservation;
  private final CancelReservation cancelReservation;

  @Inject
  public ConfirmReservationPresenter(
      InternalStorageManager internalStorageManager, GetReservationTime getReservationTime,
      SendCreateReservation sendCreateReservation, SendEditReservation sendEditReservation,
      CancelReservation cancelReservation
  ) {
    this.internalStorageManager = internalStorageManager;
    this.getReservationTime = getReservationTime;
    this.sendCreateReservation = sendCreateReservation;
    this.sendEditReservation = sendEditReservation;
    this.cancelReservation = cancelReservation;
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }


  public void sendCreateReservation(ReservationRequest request){
    getView().showLoading();
    add(sendCreateReservation.execute(new SingleSubscriber<ReservationResultResponse>() {
      @Override
      public void onSuccess(ReservationResultResponse value) {
        getView().hideLoading();
        getView().renderCreateSuccess(value);
      }

      @Override
      public void onError(Throwable error) {

        getView().hideLoading();
        getView().renderCreateFailed();
      }
    },request.toMap()));
  }
  public void sendEditReservation(ReservationRequest request, String reservationId,
                                  String verificationKey){
    SendEditReservation.Params params = new SendEditReservation.Params(request.toMap(),
                              reservationId , verificationKey);



    getView().showLoading();
    add(sendEditReservation.execute(new SingleSubscriber<ReservationResultResponse>() {
      @Override
      public void onSuccess(ReservationResultResponse value) {
        getView().hideLoading();
        getView().renderCreateSuccess(value);
      }

      @Override
      public void onError(Throwable error) {

        getView().hideLoading();
        getView().renderCreateFailed();
      }
    },params));
  }

  public void cancelReservation(String reservationId,
                                  String verificationKey){
    CancelReservation.Params params = new CancelReservation.Params(reservationId , verificationKey);



    getView().showLoading();
    add(cancelReservation.execute(new SingleSubscriber<CancelReservationResponse>() {
      @Override
      public void onSuccess(CancelReservationResponse value) {
        getView().hideLoading();
        getView().renderCancel(value);
      }

      @Override
      public void onError(Throwable error) {

        getView().hideLoading();
        getView().renderCreateFailed();
      }
    },params));
  }

}
