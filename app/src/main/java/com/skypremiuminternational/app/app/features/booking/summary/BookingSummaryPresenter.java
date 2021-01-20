package com.skypremiuminternational.app.app.features.booking.summary;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.model.ean.booking.history.Cancel;
import com.skypremiuminternational.app.data.utl.OccupancyArranger;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.ean.CheckPrice;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckResult;
import com.skypremiuminternational.app.domain.models.booking.PriceCheckSummary;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BookingSummaryPresenter extends BasePresenter<BookingSummaryView> {

  private final CheckPrice checkPrice;
  private final OccupancyArranger occupancyArranger;
  private CompositeSubscription compositeSubscription;
  private List<String> priceCheckLinks;
  private int roomCount;
  private int adultCount;
  private List<Child> children;
  private String paymentOptionLink;
  private String bedTypes;
  private PriceCheckResult result;
  private Property property;
  private CalendarDay checkInDate;
  private CalendarDay checkOutDate;
  private CancelPenalty cancelPenalty;
  private String specialCheckInInstructions;
  private String checkInInstructions;

  @Inject
  public BookingSummaryPresenter(
      OccupancyArranger occupancyArranger,
      GetAppVersion getAppVersion,
      InternalStorageManager internalStorageManager, CheckPrice checkPrice) {
    super(getAppVersion, internalStorageManager);
    this.occupancyArranger = occupancyArranger;
    compositeSubscription = new CompositeSubscription();
    this.checkPrice = checkPrice;
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

  public void checkPrice() {
    if (priceCheckLinks != null && priceCheckLinks.size() > 0) {
      getView().showLoading();
      checkPrice.execute(new SingleSubscriber<PriceCheckResult>() {
        @Override
        public void onSuccess(PriceCheckResult value) {
          BookingSummaryPresenter.this.result = value;
          getView().hideLoading();
          List<String> groupedOccupancies =
              occupancyArranger.groupOccupancies(roomCount, adultCount, children);
          getView().render(value,
              occupancyArranger.arrangeChildAges(roomCount, adultCount, children),
              groupedOccupancies);
        }

        @Override
        public void onError(Throwable error) {
          getView().hideLoading();
          getView().render(error);
        }
      }, new CheckPrice.Params(roomCount, adultCount, children, priceCheckLinks.get(0)));
    }
  }

  public void collectValues(String dayrange, String daycount, List<TourismFee> fees) {
    if (result == null) {
      getView().render(new Exception("Filed to check for prices. Try again later"));
    } else {
      RoomCheckoutPresenter.Params params = RoomCheckoutPresenter.Params.builder()
          .roomCount(roomCount)
          .nightrange(dayrange)
          .nightcount(daycount)
          .adultCount(adultCount)
          .children(children)
          .propertyName(property.name())
          .paymentOptionLink(paymentOptionLink)
          .priceCheckSummary(result.summary)
          .bedTypes(bedTypes)
          .checkIn(checkInDate)
          .checkOut(checkOutDate)
          .propertyId(property.id())
          .cancelPenalty(cancelPenalty)
          .checkInInstructions(checkInInstructions)
          .specialCheckInInstructions(specialCheckInInstructions)
          .fees(fees)
          .build();
      getView().goToRoomCheckout(params);
    }
  }

  public void setValues(Room room, int roomCount,
                        int adultCount,
                        List<Child> children, Property property,
                        CalendarDay checkInDate,
                        CalendarDay checkOutDate,String range,String totalnight) {
    this.checkInInstructions = room.checkInInstructions();
    this.specialCheckInInstructions = room.specialCheckInInstructions();
    this.cancelPenalty = room.cancelPenalty();
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.bedTypes = room.type();
    this.property = property;
    this.paymentOptionLink = room.paymentOptionLink();
    this.priceCheckLinks = room.priceCheckLinks();
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.children = children;
  }
}
