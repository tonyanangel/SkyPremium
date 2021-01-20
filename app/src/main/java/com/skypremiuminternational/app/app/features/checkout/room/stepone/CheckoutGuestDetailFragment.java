package com.skypremiuminternational.app.app.features.checkout.room.stepone;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutActivity;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import dagger.android.support.AndroidSupportInjection;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class CheckoutGuestDetailFragment extends BaseFragment<CheckoutGuestDetailPresenter>
    implements CheckoutGuestDetailView<CheckoutGuestDetailPresenter> {

  @BindView(R.id.edt_bed_preferences)
  SkyTextInputSignLayout edtBedPreferences;
  @BindView(R.id.edt_email)
  SkyTextInputSignLayout edtEmail;
  @BindView(R.id.cb_book_for_someone)
  CheckBox cbBookForSomeone;
  @BindView(R.id.rv_booker_info)
  RecyclerView rvBookerInfo;

  private BookerInfoAdapter adapter;

  private Params params;
  private String[] bedGroups;

  public static CheckoutGuestDetailFragment newInstance(Params params) {
    CheckoutGuestDetailFragment fragment = new CheckoutGuestDetailFragment();
    fragment.params = params;
    return fragment;
  }

  public CheckoutGuestDetailFragment() {

  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUpRecyclerView();

    if (params.bedTypes != null && params.bedTypes.length() > 0) {
      Observable.from(params.bedTypes.split("or"))
          .filter(Validator::isTextValid)
          .toList()
          .map(filteredBedGroups -> {
            String[] array = new String[filteredBedGroups.size()];
            return filteredBedGroups.toArray(array);
          }).subscribe(result -> bedGroups = result);
    }

    if (Validator.isTextValid(params.bedGroup)) {
      edtBedPreferences.setText(params.bedGroup);
    }
    cbBookForSomeone.setChecked(params.isBookForSomeone);
    if (params.email != null && !TextUtils.isEmpty(params.email)) {
      edtEmail.setText(params.email);
    }
    cbBookForSomeone.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        presenter.clearDefaultEntries();
      } else {
        presenter.populateDefaultEntries();
      }
    });
    presenter.setValues(params);
  }

  private void setUpRecyclerView() {
    adapter = new BookerInfoAdapter(params.phoneCode.getPhoneCodes());
    rvBookerInfo.setNestedScrollingEnabled(false);
    rvBookerInfo.setHasFixedSize(true);
    rvBookerInfo.setAdapter(adapter);
    rvBookerInfo.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
  }

  @Inject
  @Override
  public void injectPresenter(CheckoutGuestDetailPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_checkout_guest_detail;
  }

  @OnClick(R.id.tv_next)
  public void onNextClicked() {
    presenter.validateGuestDetail(edtEmail.getText(), adapter.getItems(),
        edtBedPreferences.getText());
  }

  @Override
  public void render(List<BookerInfo> bookerInfos) {
    adapter.setBookerInfos(bookerInfos);
  }

  @Override
  public void renderEmailValidationError(boolean withDialog) {
    if (withDialog) {
      showErrorDialog(getString(R.string.error_invalid_email));
    } else {
      edtEmail.showError(getString(R.string.error_invalid_email));
    }
  }

  @Override
  public void renderBookerValidationError() {
    showValidationErrorDialog();
    adapter.notifyDataSetChanged();
  }

  private void showValidationErrorDialog() {
    showErrorDialog(getString(R.string.forms_validation_error_message));
  }

  private void showErrorDialog(String errorMessage) {
    new AlertDialog.Builder(getContext()).setCancelable(false)
        .setMessage(errorMessage)
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss()).show();
  }

  @Override
  public void goToStepTwo(String emailAddress, String bedGroup) {
    ((RoomCheckoutActivity) getActivity()).gotoStepTwo(cbBookForSomeone.isChecked(), bedGroup,
        emailAddress, adapter.getItems());
  }

  @Override
  public void renderBedGroupValidationError() {
    edtBedPreferences.showError(getString(R.string.error_invalid_bed_group));
  }

  @OnClick(R.id.bed_preferences_overlay)
  void onBedPrefOverlayClicked() {
    if (bedGroups != null && bedGroups.length > 0) {
      new AlertDialog.Builder(getContext())
          .setSingleChoiceItems(bedGroups, -1, (dialog, which) -> {
            edtBedPreferences.setText(bedGroups[which]);
            dialog.dismiss();
          }).show();
    }
  }

  public static final class Params {
    final List<BookerInfo> bookerInfos;
    final PhoneCode phoneCode;
    final int roomCount;
    final List<Child> children;
    final int adultCount;
    final boolean isBookForSomeone;
    final String email;
    final UserDetailResponse userDetailResponse;
    final String bedTypes;
    final String bedGroup;

    public Params(List<BookerInfo> bookerInfos,
                  PhoneCode phoneCode, int roomCount,
                  List<Child> children, int adultCount, boolean isBookForSomeone, String email,
                  UserDetailResponse userDetailResponse, String bedTypes, String bedGroup) {
      this.bedGroup = bedGroup;
      this.bookerInfos = bookerInfos;
      this.phoneCode = phoneCode;
      this.roomCount = roomCount;
      this.children = children;
      this.adultCount = adultCount;
      this.isBookForSomeone = isBookForSomeone;
      this.email = email;
      this.userDetailResponse = userDetailResponse;
      this.bedTypes = bedTypes;
    }
  }
}
