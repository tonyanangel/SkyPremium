package com.skypremiuminternational.app.app.utils.listener;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.List;

public interface SearchHistoryListener {

  void searchHistoryClicked(String areaOrHotel, List<CalendarDay> selectedDays, int roomCount,
                            int adultCount, List<Child> children);
}
