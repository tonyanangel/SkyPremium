package com.skypremiuminternational.app.app.features.travel.ean.daterangepicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;

import java.util.Date;
import java.util.List;

/**
 * Created by johnsonmaung on 10/7/17.
 */

public interface DateRangePickerView<T extends Presentable> extends Viewable<T> {

  void render(String startDate, String endDate);

  void clearDateRange();

  void render(String startDate, CalendarDay startDay);

  void unselectedDate(Date date);

  void onDatesSorted(List<CalendarDay> days);
}
