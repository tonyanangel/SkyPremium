package com.skypremiuminternational.app.app.features.travel.ean.daterangepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerPresenter.MAX_DAYS;

/**
 * Created by johnsonmaung on 10/6/17.
 */

public class DateRangePickerDialogFragment extends BaseDialogFragment<DateRangePickerPresenter>
    implements DateRangePickerView<DateRangePickerPresenter> {

  @BindView(R.id.view_transparent)
  View viewTransparent;
  @BindView(R.id.tv_done)
  TextView tvDone;
  @BindView(R.id.calendar_view)
  MaterialCalendarView calendarView;
  @BindView(R.id.tv_check_in_date)
  TextView tvCheckInDate;
  @BindView(R.id.tv_check_out_date)
  TextView tvCheckOutDate;
  @BindView(R.id.tv_check_in_date_label)
  TextView tvCheckInDateLabel;
  @BindView(R.id.tv_check_out_date_label)
  TextView tvCheckOutDateLabel;
  @BindView(R.id.tv_check_in_date_hint)
  TextView tvCheckInDateHint;
  @BindView(R.id.tv_check_out_date_hint)
  TextView tvCheckOutDateHint;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private OnDateRangeSelectListener onDateRangeSelectListener;
  private List<CalendarDay> selectedDates = new ArrayList<>();

  public static DateRangePickerDialogFragment newInstance() {
    DateRangePickerDialogFragment fragment = new DateRangePickerDialogFragment();
    return fragment;
  }

  public static DateRangePickerDialogFragment newInstance(ArrayList<CalendarDay> selectedDates) {
    DateRangePickerDialogFragment fragment = new DateRangePickerDialogFragment();
    fragment.selectedDates = selectedDates;
    return fragment;
  }

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
    if (selectedDates != null && selectedDates.size() > 0) {
      for (CalendarDay calendarDay : selectedDates) {
        calendarView.setDateSelected(calendarDay.getDate(), true);
      }
    }

    calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
    calendarView.setOnRangeSelectedListener(
        (materialCalendarView, list) -> presenter.changeDateRange(list));
    calendarView.setOnDateChangedListener(
        (materialCalendarView, calendarDay, selected) -> presenter.changeDateRange(
            calendarView.getSelectedDates()));

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
    presenter.sortDate(calendarView.getSelectedDates());
  }

  @OnClick(R.id.txt_clear)
  void onClickClear() {
    showCheckInHint(true);
    showCheckOutHint(true);
    calendarView.clearSelection();

    Calendar maxDate = Calendar.getInstance();
    maxDate.add(Calendar.DAY_OF_MONTH, MAX_DAYS);

    calendarView.state().edit()
        .setMinimumDate(Calendar.getInstance())
        .setMaximumDate(maxDate)
        .setCalendarDisplayMode(CalendarMode.MONTHS)
        .isCacheCalendarPositionEnabled(true)
        .commit();
  }

  @Inject
  @Override
  public void injectPresenter(DateRangePickerPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_fragment_date_range_picker;
  }

  @Override
  public void render(String startDate, String endDate) {
    showCheckInHint(false);
    showCheckOutHint(false);

    tvDone.setEnabled(true);

    tvCheckInDate.setText(startDate);
    tvCheckOutDate.setText(endDate);
  }

  public void setOnDateRangeSelectListener(OnDateRangeSelectListener onDateRangeSelectListener) {
    this.onDateRangeSelectListener = onDateRangeSelectListener;
  }

  private void showCheckInHint(boolean visible) {
    if (visible) {
      tvCheckInDateHint.setVisibility(View.VISIBLE);
      tvCheckInDateLabel.setVisibility(View.GONE);
      tvCheckInDate.setVisibility(View.GONE);
    } else {
      tvCheckInDateHint.setVisibility(View.GONE);
      tvCheckInDateLabel.setVisibility(View.VISIBLE);
      tvCheckInDate.setVisibility(View.VISIBLE);
    }
  }

  private void showCheckOutHint(boolean visible) {
    if (visible) {
      tvCheckOutDateHint.setVisibility(View.VISIBLE);
      tvCheckOutDateLabel.setVisibility(View.GONE);
      tvCheckOutDate.setVisibility(View.GONE);
    } else {
      tvCheckOutDateHint.setVisibility(View.GONE);
      tvCheckOutDateLabel.setVisibility(View.VISIBLE);
      tvCheckOutDate.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void clearDateRange() {
    showCheckInHint(true);
    showCheckOutHint(true);
    tvDone.setEnabled(false);

    Calendar maxDate = Calendar.getInstance();
    maxDate.add(Calendar.DAY_OF_MONTH, MAX_DAYS);

    calendarView.state().edit()
        .setMinimumDate(Calendar.getInstance())
        .setMaximumDate(maxDate)
        .setCalendarDisplayMode(CalendarMode.MONTHS)
        .isCacheCalendarPositionEnabled(true)
        .commit();
  }

  @Override
  public void render(String startDate, CalendarDay startDay) {
    showCheckInHint(false);
    showCheckOutHint(true);
    tvDone.setEnabled(false);
    tvCheckInDate.setText(startDate);
    Calendar maxDate = Calendar.getInstance();
    maxDate.setTime(startDay.getDate());
    maxDate.add(Calendar.DAY_OF_MONTH, DateRangePickerPresenter.MAX_DAY_RANGE);

    Calendar minDate = Calendar.getInstance();
    minDate.setTime(startDay.getDate());

    calendarView.state().edit()
        .setMinimumDate(minDate)
        .setMaximumDate(maxDate)
        .setCalendarDisplayMode(CalendarMode.MONTHS)
        .isCacheCalendarPositionEnabled(true)
        .commit();
  }

  @Override
  public void unselectedDate(Date date) {
    calendarView.setDateSelected(date, false);
  }

  @Override
  public void onDatesSorted(List<CalendarDay> days) {
    onDateRangeSelectListener.onDateRangeSelect(days);
    dismiss();
  }

  public interface OnDateRangeSelectListener {
    void onDateRangeSelect(List<CalendarDay> dates);
  }
}
