package com.skypremiuminternational.app.app.features.travel.ean.search;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.List;

public interface SearchHotelView<T extends Presentable> extends Viewable<T> {

  void render(@NonNull String startDate, @NonNull String endDate);

  void clearDateRange();

  void showOccupancyDialog(int roomCount, int adultCount, List<Child> children);

  void render(int roomCount, int adultCount, List<Child> children);

  void notifyToStartSearching(List<CalendarDay> selectedDays, int roomCount, int adultCount,
                              List<Child> children);
}
