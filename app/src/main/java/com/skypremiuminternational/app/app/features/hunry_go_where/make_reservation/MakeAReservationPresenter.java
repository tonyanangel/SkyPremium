package com.skypremiuminternational.app.app.features.hunry_go_where.make_reservation;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.auth.GetRenewalToken;
import com.skypremiuminternational.app.domain.interactor.cart.CheckLimit;
import com.skypremiuminternational.app.domain.interactor.cart.CreateCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetAllCost;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartDetail;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartDetailBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.GetCostBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrder;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceOrderBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.PlaceRenewalOrder;
import com.skypremiuminternational.app.domain.interactor.cart.SetShippingAndBillingAddress;
import com.skypremiuminternational.app.domain.interactor.cart.UpdateBuyNowCart;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodes;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetReservationTime;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetRestaurantMsg;
import com.skypremiuminternational.app.domain.interactor.rating_comment.AddRatingComment;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetReviewDetail;
import com.skypremiuminternational.app.domain.interactor.user.GetCreditCards;
import com.skypremiuminternational.app.domain.interactor.user.GetDeliveryAddress;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.interactor.user.billingaddress.GetBillingAddresses;
import com.skypremiuminternational.app.domain.models.hunry_go_where.DataMessage;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;
import com.skypremiuminternational.app.domain.models.hunry_go_where.RestaurantMessageResponse;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MakeAReservationPresenter extends BaseFragmentPresenter<MakeAReservationView> {



  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  InternalStorageManager internalStorageManager;
  private final GetReservationTime getReservationTime;
  private final GetRestaurantMsg getRestaurantMsg;


  @Inject
  public MakeAReservationPresenter(
                InternalStorageManager internalStorageManager, GetReservationTime getReservationTime,
                GetRestaurantMsg getRestaurantMsg
  ) {
    this.internalStorageManager = internalStorageManager;
    this.getReservationTime = getReservationTime;
    this.getRestaurantMsg = getRestaurantMsg;
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


  public void getReservationTime(String date , String restaurantId){
    getView().showLoading();
    GetReservationTime.Params params = new GetReservationTime.Params(date,restaurantId);

    add(getReservationTime.execute(new SingleSubscriber<ReservationTimeResponse>() {
      @Override
      public void onSuccess(ReservationTimeResponse value) {
        getView().hideLoading();
        getView().renderReservationTime(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderReservationTimeFailed();

      }
    },params));
  }

  public void getGetRestaurantMsg(String restaurantId) {
    getView().showLoading();

    add(getRestaurantMsg.execute(new SingleSubscriber<RestaurantMessageResponse>() {
      @Override
      public void onSuccess(RestaurantMessageResponse value) {
        getView().hideLoading();
        String msg = "";

        for(DataMessage item : value.getMessage().getData() ){
          if(item.getRestaurantId().equals(restaurantId)){
            msg =  item.getMessage();
          }
        }

        getView().renderRestaurantMsg(msg);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();

      }
    },restaurantId));
  }
}
