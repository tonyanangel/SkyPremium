package com.skypremiuminternational.app.app.features.checkout.room;

import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.ean.BookRoom;
import com.skypremiuminternational.app.domain.interactor.ean.GetISOCountryCodes;
import com.skypremiuminternational.app.domain.interactor.ean.GetPaymentOptions;
import com.skypremiuminternational.app.domain.interactor.phone_code.GetPhoneCodes;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckSummary;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RoomCheckoutPresenter extends BasePresenter<RoomCheckoutView> {

  private final GetPhoneCodes getPhoneCodes;
  private final GetPaymentOptions getPaymentOptions;
  private final BookRoom bookRoom;
  private final GetUserDetail getUserDetail;
  private final GetISOCountryCodes getISOCountryCodes;
  private CompositeSubscription compositeSubscription;

  //Guest Details
  private List<BookerInfo> bookerInfos;
  private boolean isBookForSomeone;
  private String email;
  private String bedGroup;

  private PhoneCode phoneCode;
  private List<ISOCountry> countryCodes;
  private List<CardOption> cardOptions;
  private UserDetailResponse userDetailResponse;

  private Params params;

  @Inject
  public RoomCheckoutPresenter(BookRoom bookRoom, GetISOCountryCodes getISOCountryCodes,
                               GetAppVersion getAppVersion, GetPhoneCodes getPhoneCodes,
                               GetUserDetail getUserDetail, InternalStorageManager internalStorageManager,
                               GetPaymentOptions getPaymentOptions) {
    super(getAppVersion, internalStorageManager);
    this.getUserDetail = getUserDetail;
    this.getISOCountryCodes = getISOCountryCodes;
    this.getPhoneCodes = getPhoneCodes;
    this.bookRoom = bookRoom;
    this.getPaymentOptions = getPaymentOptions;
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }



  public void setValues(Params params) {
    this.params = params;
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }

  public void collectGuestDetails() {
    if (phoneCode != null && userDetailResponse != null) {
      getView().showGuestDetailFragment(params.roomCount(), params.adultCount(), params.children(),
          phoneCode, bookerInfos,
          email, isBookForSomeone, userDetailResponse, params.bedTypes(), bedGroup);
    } else {
      add(getPhoneCodes.asObservable()
          .zipWith(getUserDetail.asObservable(),
              (phoneCode, userDetailResponse) -> {
                RoomCheckoutPresenter.this.phoneCode = phoneCode;
                RoomCheckoutPresenter.this.userDetailResponse = userDetailResponse;
                return true;
              })
          .subscribe(
              result -> getView().showGuestDetailFragment(params.roomCount(), params.adultCount(),
                  params.children(),
                  phoneCode,
                  bookerInfos, email, isBookForSomeone, userDetailResponse, params.bedTypes(),
                  bedGroup),
              getView()::render));
    }
  }

  public void setGuestDetail(boolean isBookForSomeone, String bedGroup, String email,
                             List<BookerInfo> bookerInfos) {
    this.isBookForSomeone = isBookForSomeone;
    this.email = email;
    this.bedGroup = bedGroup;
    this.bookerInfos = bookerInfos;
  }

  public void collectPaymentDetails() {
    if (countryCodes == null || cardOptions == null) {
      getView().showLoading();
      add(getISOCountryCodes.asObservable().zipWith(
          getPaymentOptions.asObservable(new GetPaymentOptions.Params(params.paymentOptionLink())),
          (countryCodes, cardOptions) -> {
            RoomCheckoutPresenter.this.countryCodes = countryCodes;
            RoomCheckoutPresenter.this.cardOptions = cardOptions;
            return true;
          })
          .subscribe(result -> {
                getView().hideLoading();
                getView().goToPaymentReview(CheckoutPaymentReviewPresenter.Params.builder()
                    .countryCodes(countryCodes)
                    .cardOptions(cardOptions)
                    .phoneCode(phoneCode)
                    .grandTotal(params.priceCheckSummary().grandTotal())
                    .fees(params.fees())
                    .cancelPenalty(params.cancelPenalty())
                    .propertyName(params.propertyName())
                    .checkInInstructions(params.checkInInstructions())
                    .specialCheckInInStructions(params.specialCheckInInstructions())
                    .build());
              }
              , error -> {
                getView().hideLoading();
                getView().render(error);
              }));
    } else {
      getView().goToPaymentReview(CheckoutPaymentReviewPresenter.Params.builder()
          .countryCodes(countryCodes)
          .cardOptions(cardOptions)
          .phoneCode(phoneCode)
          .grandTotal(params.priceCheckSummary().grandTotal())
          .fees(params.fees())
          .cancelPenalty(params.cancelPenalty())
          .propertyName(params.propertyName())
          .checkInInstructions(params.checkInInstructions())
          .specialCheckInInStructions(params.specialCheckInInstructions())
          .build());
    }
  }

  public void book(PaymentDetail paymentDetail) {
    getView().showProcessingOrder();
    add(bookRoom.execute(new SingleSubscriber<BookingDetail>() {
      @Override
      public void onSuccess(BookingDetail bookingDetail) {
        getView().hideProcessingOrder();
        if (bookingDetail.success()) {
          getView().goToStepThree(bookingDetail, email, params.propertyName(),params.fees());
        } else {
          getView().renderBookingError(new Exception(bookingDetail.errorMessages().get(0)));
        }
      }

      @Override
      public void onError(Throwable error) {
        error.printStackTrace();
        getView().hideProcessingOrder();
        getView().renderBookingError(new Exception("Failed to create booking"));
      }
    }, new BookRoom.Params(paymentDetail, bookerInfos, email, isBookForSomeone,
        params.priceCheckSummary().bookingLink(), format(params.checkIn().getDate()),
        format(params.checkOut().getDate()), params.propertyId(), bedGroup, params.bedTypes(),
        params.priceCheckSummary().skyDollar())));
  }

  private String format(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return simpleDateFormat.format(date);
  }

  @AutoValue
  public static abstract class Params implements Parcelable {

    @Nullable
    public abstract String checkInInstructions();

    @Nullable
    public abstract String specialCheckInInstructions();

    public abstract String propertyName();

    public abstract int roomCount();

    public abstract String nightcount();

    public abstract String nightrange();

    public abstract int adultCount();

    public abstract List<Child> children();

    public abstract String paymentOptionLink();

    public abstract PriceCheckSummary priceCheckSummary();

    public abstract String bedTypes();

    public abstract CalendarDay checkIn();

    public abstract CalendarDay checkOut();

    public abstract CancelPenalty cancelPenalty();

    public abstract String propertyId();

    public abstract List<TourismFee> fees();



    public static Params create(String checkInInstructions, String specialCheckInInstructions,
                                String propertyName, int roomCount,String nightcount,String nightrange, int adultCount, List<Child> children,
                                String paymentOptionLink, PriceCheckSummary priceCheckSummary,
                                String bedTypes, CalendarDay checkIn, CalendarDay checkOut,
                                CancelPenalty cancelPenalty, String propertyId,List<TourismFee> fees) {
      return builder()
          .checkInInstructions(checkInInstructions)
          .specialCheckInInstructions(specialCheckInInstructions)
          .propertyName(propertyName)
          .roomCount(roomCount)
          .adultCount(adultCount)
          .nightcount(nightcount)
          .nightrange(nightrange)
          .children(children)
          .paymentOptionLink(paymentOptionLink)
          .priceCheckSummary(priceCheckSummary)
          .bedTypes(bedTypes)
          .checkIn(checkIn)
          .checkOut(checkOut)
          .cancelPenalty(cancelPenalty)
          .propertyId(propertyId)
          .fees(fees)
          .fees(fees)
          .build();
    }


    public static Builder builder() {
      return new AutoValue_RoomCheckoutPresenter_Params.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder roomCount(int roomCount);

      public abstract Builder adultCount(int adultCount);

      public abstract Builder children(List<Child> children);

      public abstract Builder paymentOptionLink(String paymentOptionLink);

      public abstract Builder priceCheckSummary(PriceCheckSummary priceCheckSummary);

      public abstract Builder bedTypes(String bedTypes);

      public abstract Builder nightcount(String nightcount);

      public abstract Builder nightrange(String nightrange);

      public abstract Builder checkIn(CalendarDay checkIn);

      public abstract Builder checkOut(CalendarDay checkOut);

      public abstract Builder propertyId(String propertyId);

      public abstract Builder fees(List<TourismFee> fees);

      public abstract Builder propertyName(String propertyName);

      public abstract Builder cancelPenalty(CancelPenalty cancelPenalty);

      public abstract Builder checkInInstructions(String checkInInstructions);

      public abstract Builder specialCheckInInstructions(String specialCheckInInstructions);

      public abstract Params build();
    }
  }
}
