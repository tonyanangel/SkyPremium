package com.skypremiuminternational.app.app.features.travel.ean.detail.property;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codigo on 23/4/18.
 */

public interface PropertyDetailView<T extends Presentable> extends Viewable<T> {

  void render(@NonNull String startDate, @NonNull String endDate);

  void clearDateRange();

  void showOccupancyPickerDialog(int roomCount, int adultCount, List<Child> children);

  void render(int roomCount, int adultCount, List<Child> children);

  void render(Property property, int roomCount, int adultCount, CalendarDay checkInDate,
              CalendarDay checkOutDate, int totalDays, List<Child> children);

  void goToMap(Double lat, Double lng, String address);

  void goToBookingSummary(Property property, Room room, int roomCount, int adultCount,
                          ArrayList<Child> children, CalendarDay checkInDate,
                          CalendarDay checkOutDate, int totalDays);

  void showDateRangePicker(List<CalendarDay> selectedDates);

  void renderRoomSearchError(Throwable error);

  void updateCartCount(String cartCount);

  void renderMap(String address, double lat, double lng);

  void showCancellationPolicy(CancelPenalty cancelPenalty, String roomAndType, String propertyName);
}
