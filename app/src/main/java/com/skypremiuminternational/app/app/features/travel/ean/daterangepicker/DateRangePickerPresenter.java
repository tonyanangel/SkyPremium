package com.skypremiuminternational.app.app.features.travel.ean.daterangepicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 10/6/17.
 */

public class DateRangePickerPresenter extends BaseFragmentPresenter<DateRangePickerView> {

  public static final int MAX_DAY_RANGE = 28;
  public static final int MAX_DAYS = 499;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public DateRangePickerPresenter() {

  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void changeDateRange(List<CalendarDay> list) {
    if (list != null && !list.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
          Locale.getDefault());
      /*if (list.size() > MAX_DAY_RANGE) {
        int datesToBeUnselected = list.size() - MAX_DAY_RANGE;

        for (int i = 0; i < datesToBeUnselected; i++) {
          getView().unselectedDate(list.get(i).getDate());
        }

        CalendarDay startDay = list.get(datesToBeUnselected);
        String startDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = list.get(list.size() - 1);
        String endDate = dateFormat.format(endDay.getDate());
        getView().render(startDate, endDate);
      }*/

      if (list.size() > 1) {
        CalendarDay startDay = list.get(0);
        String startDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = list.get(list.size() - 1);
        String endDate = dateFormat.format(endDay.getDate());
        getView().render(startDate, endDate);
      } else {
        String startDate =
            dateFormat.format(list.get(0).getDate());
        getView().render(startDate, list.get(0));
      }
    } else {
      getView().clearDateRange();
    }
  }

  public void sortDate(List<CalendarDay> selectedDates) {
    List<CalendarDay> days = new ArrayList<>(selectedDates);
    Collections.sort(days,
        (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    getView().onDatesSorted(days);
  }
}
