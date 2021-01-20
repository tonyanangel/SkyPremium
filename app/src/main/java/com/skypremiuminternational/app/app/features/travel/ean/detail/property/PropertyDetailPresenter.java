package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.ean.SearchRooms;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.skypremiuminternational.app.app.features.travel.ProductListPresenter.DEFAULT_ADULT_COUNT;
import static com.skypremiuminternational.app.app.features.travel.ProductListPresenter.DEFAULT_ROOM_COUNT;

/**
 * Created by codigo on 23/4/18.
 */

public class PropertyDetailPresenter extends BasePresenter<PropertyDetailView> {

  private final SearchRooms searchRooms;
  private final GetCartCount getCartCount;
  private List<CalendarDay> selectedDates;
  private int roomCount = DEFAULT_ROOM_COUNT;
  private int adultCount = DEFAULT_ADULT_COUNT;
  private List<Child> children = new ArrayList<>();
  private Property property;
  private CompositeSubscription compositeSubscription;
  private CalendarDay checkInDate;
  private CalendarDay checkOutDate;
  private int totalDays;
  private boolean isMapReady = false;
  private String propertyId;

  @Inject
  public PropertyDetailPresenter(GetAppVersion getAppVersion,
                                 InternalStorageManager internalStorageManager,
                                 SearchRooms searchRooms, GetCartCount getCartCount) {
    super(getAppVersion, internalStorageManager);
    this.getCartCount = getCartCount;
    compositeSubscription = new CompositeSubscription();
    this.searchRooms = searchRooms;
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(cartCount -> getView().updateCartCount(cartCount), ignored -> {

        }));
  }

  public void changeDateRange(List<CalendarDay> selectedDates) {
    this.selectedDates = selectedDates;
    if (selectedDates != null && !selectedDates.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
          Locale.getDefault());
      if (selectedDates.size() > 1) {
        CalendarDay startDay = selectedDates.get(0);
        String startDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = selectedDates.get(selectedDates.size() - 1);
        String endDate = dateFormat.format(endDay.getDate());
        getView().render(startDate, endDate);
      } else {
        String startDate =
            dateFormat.format(selectedDates.get(0).getDate());
        getView().render(startDate, "");
      }
    } else {
      getView().clearDateRange();
    }
  }

  public void collectOccupancyValues() {
    getView().showOccupancyPickerDialog(roomCount, adultCount, children);
  }

  public void setOccupancyValues(int roomCount, int adultCount, ArrayList<Child> children) {
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.children = children;

    getView().render(roomCount, adultCount, children);
  }

  /**
   * Set the initial values for this presenter.
   * For the sake of consistency, all data must come from the presenter
   */
  public void setInitValues(String propertyId, int roomCount, int adultCount, List<Child> children,
                            List<CalendarDay> selectedDates) {
    this.propertyId = propertyId;
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.selectedDates = selectedDates;
    this.children = children;
  }

  public void collectLatLng() {
    if (property != null) {
      getView().goToMap(property.lat(), property.lng(), property.address());
    }
  }

  public void collectBookingValues(Room room) {
    if (property == null) return;
    Calendar tomorrow = Calendar.getInstance();
    tomorrow.add(Calendar.DAY_OF_MONTH, 1);

    refreshDataRange();

    getView().goToBookingSummary(property, room, roomCount,
        adultCount, (ArrayList<Child>) children, checkInDate, checkOutDate, totalDays);
  }

  private void refreshDataRange() {
    Calendar tomorrow = Calendar.getInstance();
    checkInDate = CalendarDay.from(Calendar.getInstance());
    checkOutDate = CalendarDay.from(tomorrow);
    totalDays = 2;
    if (selectedDates != null && selectedDates.size() > 1) {
      checkInDate = selectedDates.get(0);
      checkOutDate = selectedDates.get(selectedDates.size() - 1);
      totalDays = selectedDates.size();
    }
  }

  public void collectSelectedDates() {
    getView().showDateRangePicker(this.selectedDates);
  }

  public void searchRooms() {
    getView().showLoading();
    add(searchRooms.execute(new SingleSubscriber<Property>() {
      @Override
      public void onSuccess(Property property) {
        PropertyDetailPresenter.this.property = property;
        getView().hideLoading();

        refreshDataRange();

        getView().render(property, roomCount, adultCount, checkInDate, checkOutDate, totalDays,
            children);

        if (isMapReady) {
          getView().renderMap(property.address(), property.lat(), property.lng());
        }
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().renderRoomSearchError(error);
      }
    }, buildParams()));
  }

  private SearchRooms.Params buildParams() {
    refreshDataRange();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    return new SearchRooms.Params(propertyId, df.format(checkInDate.getDate()),
        df.format(checkOutDate.getDate()), "SG", roomCount, adultCount, children);
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void onMapReady() {
    isMapReady = true;
    if (property != null) {
      getView().renderMap(property.address(), property.lat(), property.lng());
    }
  }

  public void collectNamesToShowCancellationPolicy(Room room) {
    if (property == null) return;
    String roomAndType = "";
    if (room.name() != null) {
      roomAndType = room.name() + " - ";
    }
    roomAndType += room.type();

    getView().showCancellationPolicy(room.cancelPenalty(), roomAndType, property.name());
  }
}
