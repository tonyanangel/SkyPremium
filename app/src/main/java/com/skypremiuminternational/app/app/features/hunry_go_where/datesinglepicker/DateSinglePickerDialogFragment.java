package com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.domain.models.hunry_go_where.ReservationTimeResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.skypremiuminternational.app.app.features.hunry_go_where.datesinglepicker.DateSinglePickerPresenter.MAX_DAYS;

public class DateSinglePickerDialogFragment extends BaseDialogFragment<DateSinglePickerPresenter>
    implements DateSinglePickerView<DateSinglePickerPresenter> {

  public static final String RESERVATION_DATE_PARTNER = "yyyy-MM-dd";


  @BindView(R.id.view_transparent)
  View viewTransparent;
  @BindView(R.id.tv_done)
  TextView tvDone;
  @BindView(R.id.calendar_view)
  MaterialCalendarView calendarView;

  DateSinglePickerListener listener;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  CalendarDay selectedDate;

  public static DateSinglePickerDialogFragment newInstance(CalendarDay selectedDate , DateSinglePickerListener listener) {
    DateSinglePickerDialogFragment fragment = new DateSinglePickerDialogFragment();
    fragment.selectedDate = selectedDate;
    fragment.listener = listener;
    return fragment;
  }

 /* public static DateSinglePickerDialogFragment newInstance(ArrayList<CalendarDay> selectedDates, DateSinglePickerListener listener) {
    DateSinglePickerDialogFragment fragment = new DateSinglePickerDialogFragment();
    fragment.selectedDates = selectedDates;
    fragment.listener = listener;
    return fragment;
  }*/

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setCancelable(true);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return dialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.getWindow()
          .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    Calendar maxDate = Calendar.getInstance();
    maxDate.add(Calendar.DAY_OF_MONTH, MAX_DAYS);

    calendarView.state().edit()
        .setMinimumDate(Calendar.getInstance())
        .setMaximumDate(maxDate)
        .setCalendarDisplayMode(CalendarMode.MONTHS)
        .isCacheCalendarPositionEnabled(true)
        .commit();


    calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
    calendarView.setOnRangeSelectedListener(
        (materialCalendarView, list) -> {/*presenter.changeDateRange(list)*/});
    calendarView.setOnDateChangedListener(
        (materialCalendarView, calendarDay, selected) -> {/*presenter.changeDateRange(
            calendarView.getSelectedDates())*/});
    if(selectedDate!=null){
      calendarView.setDateSelected(selectedDate,true);
      calendarView.setCurrentDate(selectedDate);
    }
    triggerTouchEvent();
  }

  private void triggerTouchEvent() {
    viewTransparent.setOnTouchListener((v, event) -> {
      dismiss();
      return true;
    });
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
  }

  @OnClick(R.id.iv_close)
  void onClickClose() {
    dismiss();
  }

  @OnClick(R.id.tv_done)
  void onClickDone() {
    /*String s ="" + calendarView.getSelectedDate().getDate().getDate();
    Toast.makeText(getContext(),""+ s,Toast.LENGTH_SHORT).show();*/
    //presenter.sortDate(calendarView.getSelectedDates());

    String date = String.format("%d-%02d-%02d",
        calendarView.getSelectedDate().getYear(),
        calendarView.getSelectedDate().getMonth()+1,
        calendarView.getSelectedDate().getDay());
    listener.onSelectDateDone(date);
    dismiss();

  }



  @Inject
  @Override
  public void injectPresenter(DateSinglePickerPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_date_single_picker;
  }


  @Override
  public void render(String startDate, String endDate) {

  }


  @Override
  public void renderReservationTime(ReservationTimeResponse reservationTimeResponse) {

  }

  @Override
  public void renderReservationFailed() {

  }

  @Override
  public void clearDateRange() {

  }

  @Override
  public void render(String startDate, CalendarDay startDay) {

  }

  @Override
  public void unselectedDate(Date date) {

  }

  @Override
  public void onDatesSorted(List<CalendarDay> days) {

  }

  public interface DateSinglePickerListener{
    void onSelectDateDone(String date);
  }
}
