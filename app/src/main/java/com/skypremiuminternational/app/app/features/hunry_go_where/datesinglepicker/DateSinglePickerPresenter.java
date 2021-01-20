package com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker;

import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerView;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.hunry_go_where.GetReservationTime;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DateSinglePickerPresenter extends BaseFragmentPresenter<DateRangePickerView> {

  public static final int MAX_DAY_RANGE = 28;
  public static final int MAX_DAYS = 499;


  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  InternalStorageManager internalStorageManager;
  private final GetReservationTime getReservationTime;

  @Inject
  public DateSinglePickerPresenter(
      InternalStorageManager internalStorageManager, GetReservationTime getReservationTime
  ) {
    this.internalStorageManager = internalStorageManager;
    this.getReservationTime = getReservationTime;
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getReservationTime(String date , String restaurantId){
    add(getReservationTime.execute(new SingleSubscriber<ReservationTimeResponse>() {
      @Override
      public void onSuccess(ReservationTimeResponse value) {

      }

      @Override
      public void onError(Throwable error) {

      }
    } ));
  }




  /*public void changeDateRange(List<CalendarDay> list) {
    if (list != null && !list.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
          Locale.getDefault());
      *//*if (list.size() > MAX_DAY_RANGE) {
        int datesToBeUnselected = list.size() - MAX_DAY_RANGE;

        for (int i = 0; i < datesToBeUnselected; i++) {
          getView().unselectedDate(list.get(i).getDate());
        }

        CalendarDay startDay = list.get(datesToBeUnselected);
        String startDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = list.get(list.size() - 1);
        String endDate = dateFormat.format(endDay.getDate());
        getView().render(startDate, endDate);
      }*//*

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
  }*/

/*  public void sortDate(List<CalendarDay> selectedDates) {
    List<CalendarDay> days = new ArrayList<>(selectedDates);
    Collections.sort(days,
        (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    getView().onDatesSorted(days);
  }*/
}

