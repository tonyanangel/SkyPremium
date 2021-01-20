package com.skypremiuminternational.app.app.features.checkout.room;

import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewPresenter;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.List;

public interface RoomCheckoutView<T extends Presentable> extends Viewable<T> {
  void showGuestDetailFragment(int roomCount, int adultCount, List<Child> children,
                               PhoneCode phoneCode, List<BookerInfo> bookerInfos, String email,
                               boolean isBookForSomeone, UserDetailResponse userDetailResponse,
                               String bedTypes, String bedGroup);

  void render(Throwable error);

  void goToPaymentReview(CheckoutPaymentReviewPresenter.Params params);

  void goToStepThree(
          BookingDetail bookingDetail, String email, String propertyName, List<TourismFee> fees);


  void showProcessingOrder();

  void hideProcessingOrder();

  void renderBookingError(Exception error);
}
