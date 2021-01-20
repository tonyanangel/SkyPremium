package com.skypremiuminternational.app.app.features.travel.ean.search;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.travel.ean.daterangepicker.DateRangePickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.occupancy.OccupancyPickerDialogFragment;
import com.skypremiuminternational.app.app.features.travel.ean.suggestion.SuggestionActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseDialogFragment;
import com.skypremiuminternational.app.app.utils.listener.SearchHistoryListener;
import com.skypremiuminternational.app.app.view.HotelEditText;
import com.skypremiuminternational.app.domain.models.ean.Child;

import dagger.android.support.AndroidSupportInjection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchHotelDialogFragment extends BaseDialogFragment<SearchHotelPresenter>
    implements SearchHotelView<SearchHotelPresenter> {

  private static final int REQUEST_CODE_SUGGESTION = 333;
  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.stiAreaName)
  HotelEditText edtAreaName;
  @BindView(R.id.tv_occupancy)
  TextView tvOccupancy;
  @BindView(R.id.tv_check_in_date)
  TextView tvCheckInDate;
  @BindView(R.id.tv_check_out_date)
  TextView tvCheckOutDate;

  private String areaOrHotel;
  private SearchHistoryListener listener;
  private int roomCount;
  private int adultCount;
  private List<Child> children;
  private List<CalendarDay> selectedDays;

  public static SearchHotelDialogFragment newInstance(String areaOrHotel,
                                                      List<CalendarDay> selectedDays, int roomCount,
                                                      int adultCount, List<Child> children) {
    SearchHotelDialogFragment fragment = new SearchHotelDialogFragment();
    fragment.areaOrHotel = areaOrHotel;
    fragment.roomCount = roomCount;
    fragment.adultCount = adultCount;
    fragment.children = children;
    fragment.selectedDays = selectedDays;
    return fragment;
  }

  public SearchHotelDialogFragment() {

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCancelable(true);
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
    edtAreaName.setText(areaOrHotel);
    presenter.setSearchValues(selectedDays, roomCount, adultCount, children);
  }

  @Inject
  @Override
  public void injectPresenter(SearchHotelPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_search_hotel;
  }

  private void showDateRangePicker() {
    DateRangePickerDialogFragment fragment = DateRangePickerDialogFragment.newInstance();
    fragment.setOnDateRangeSelectListener(
        dates -> presenter.changeDateRange(dates));
    fragment.show(getChildFragmentManager(), "DateRangePicker");
  }

  @OnClick(R.id.iv_close)
  public void onCloseClicked() {
    dismiss();
  }

  @OnClick(R.id.tv_check_in_date)
  public void onCheckInDateClicked() {
    showDateRangePicker();
  }

  @OnClick(R.id.tv_check_out_date)
  public void onCheckOutDateClicked() {
    showDateRangePicker();
  }

  @OnClick(R.id.btn_search_hotels)
  public void onSearchHotelsClicked() {
    presenter.collectSearchValues();
  }

  @OnClick(R.id.tv_occupancy)
  public void onOccupancyClicked() {
    presenter.collectOccupancyValues();
  }

  @Override
  public void render(@NonNull String startDate, @NonNull String endDate) {
    tvCheckInDate.setText(startDate);

    if (TextUtils.isEmpty(endDate)) {
      tvCheckOutDate.setText(R.string.all_not_selected_label);
    } else {
      tvCheckOutDate.setText(endDate);
    }
  }

  @Override
  public void clearDateRange() {
    tvCheckOutDate.setText(R.string.all_not_selected_label);
    tvCheckInDate.setText(R.string.all_not_selected_label);
  }

  @Override
  public void showOccupancyDialog(int roomCount, int adultCount, List<Child> children) {
    OccupancyPickerDialogFragment fragment =
        OccupancyPickerDialogFragment.newInstance(roomCount, adultCount,
            children);
    fragment.setOnDoneClickListener(
        (int roomCount1, int adultCount1, List<Child> children1) -> {
          presenter.setOccupancyValues(roomCount1,
              adultCount1, (ArrayList<Child>) children1);
        });
    fragment.show(getChildFragmentManager(), "OccupancyPicker");
  }

  @Override
  public void render(int roomCount, int adultCount, List<Child> children) {
    tvOccupancy.setText(formatOccupancy(roomCount, adultCount, children));
  }

  @Override
  public void notifyToStartSearching(List<CalendarDay> selectedDays, int roomCount, int adultCount,
                                     List<Child> children) {
    if (listener != null) {
      listener.searchHistoryClicked(edtAreaName.getText().trim(),
          selectedDays, roomCount, adultCount, children);
    }
    dismiss();
  }

  private String formatOccupancy(int roomCount, int adultCount, List<Child> children) {
    Resources resources = getResources();
    String occupancy = resources.getQuantityString(R.plurals.adults, adultCount, adultCount) + ", ";

    occupancy += resources.getQuantityString(R.plurals.children, children.size(), children.size())
        + ", ";

    occupancy += resources.getQuantityString(R.plurals.rooms, roomCount, roomCount);
    return occupancy;
  }

  public void setListener(SearchHistoryListener listener) {
    this.listener = listener;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == REQUEST_CODE_SUGGESTION) {
        if (data.hasExtra("suggestion")) {
          edtAreaName.setText(data.getStringExtra("suggestion"));
        }
      }
    }
  }

  @OnClick(R.id.hotel_name_overlay)
  void onHotelNameClicked() {
    SuggestionActivity.startForResult(this, REQUEST_CODE_SUGGESTION);
  }
}
