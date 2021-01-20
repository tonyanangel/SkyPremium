package com.skypremiuminternational.app.app.features.travel.ean.search;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.travel.ProductListPresenter.DEFAULT_ADULT_COUNT;
import static com.skypremiuminternational.app.app.features.travel.ProductListPresenter.DEFAULT_ROOM_COUNT;

public class SearchHotelPresenter extends BaseFragmentPresenter<SearchHotelView> {
  private List<CalendarDay> selectedDays;
  private int roomCount = DEFAULT_ROOM_COUNT;
  private int adultCount = DEFAULT_ADULT_COUNT;
  private List<Child> children = new ArrayList<>();

  @Inject
  public SearchHotelPresenter() {

  }

  public void changeDateRange(List<CalendarDay> list) {
    this.selectedDays = list;
    if (list != null && !list.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
          Locale.getDefault());
      if (list.size() > 1) {
        CalendarDay startDay = list.get(0);
        String startDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = list.get(list.size() - 1);
        String endDate = dateFormat.format(endDay.getDate());
        getView().render(startDate, endDate);
      } else {
        String startDate =
            dateFormat.format(list.get(0).getDate());
        getView().render(startDate, "");
      }
    } else {
      getView().clearDateRange();
    }
  }

  public void collectOccupancyValues() {
    getView().showOccupancyDialog(roomCount, adultCount, children);
  }

  public void setOccupancyValues(int roomCount, int adultCount, ArrayList<Child> children) {
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.children = children;

    getView().render(roomCount, adultCount, children);
  }

  public void setSearchValues(List<CalendarDay> selectedDays, int roomCount,
                              int adultCount, List<Child> children) {

    setOccupancyValues(roomCount, adultCount, (ArrayList<Child>) children);

    changeDateRange(selectedDays);
  }

  public void collectSearchValues() {
    getView().notifyToStartSearching(selectedDays, roomCount, adultCount, children);
  }
}
