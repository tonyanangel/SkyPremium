package com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;

import java.util.Date;
import java.util.List;

public interface DateSinglePickerView<T extends Presentable> extends Viewable<T> {


  void render(String startDate, String endDate);

  void clearDateRange();

  void render(String startDate, CalendarDay startDay);

  void unselectedDate(Date date);

  void onDatesSorted(List<CalendarDay> days);

  void renderReservationTime(ReservationTimeResponse reservationTimeResponse);

  void renderReservationFailed();
}
