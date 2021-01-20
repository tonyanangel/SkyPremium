package com.skypremiuminternational.app.app.features.checkout.room;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.room.stepone.CheckoutGuestDetailFragment;
import com.skypremiuminternational.app.app.features.checkout.room.stepthree.OrderPlacedFragment;
import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewFragment;
import com.skypremiuminternational.app.app.features.checkout.room.steptwo.CheckoutPaymentReviewPresenter;
import com.skypremiuminternational.app.app.features.checkout.steptwo.ProcessingOrderFragment;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.listener.PaymentDetailCollector;
import com.skypremiuminternational.app.data.model.ean.booking.history.Booking;
import com.skypremiuminternational.app.domain.models.booking.BookingDetail;
import com.skypremiuminternational.app.domain.models.booking.GuestDetail;
import com.skypremiuminternational.app.domain.models.booking.TourismFee;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import dagger.android.AndroidInjection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RoomCheckoutActivity extends BaseActivity<RoomCheckoutPresenter>
    implements RoomCheckoutView<RoomCheckoutPresenter> {

  private static final String TAG_PROCESSING_DIALOG = "ProcessOrder";

  public static final int STEP_ONE = 0;
  public static final int STEP_TWO = 1;
  public static final int STEP_THREE = 2;

  @BindView(R.id.view_one)
  View lineOne;
  @BindView(R.id.view_two)
  View lineTwo;
  @BindView(R.id.view_three)
  View lineThree;
  @BindView(R.id.view_four)
  View lineFour;
  @BindView(R.id.ivNavigation_toolbar)
  ImageView ivNavigationToolbar;
  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitleToolbar;
  @BindView(R.id.img_complete_one)
  ImageView imgCompleteOne;
  @BindView(R.id.img_complete_two)
  ImageView imgCompleteTwo;
  @BindView(R.id.img_complete_three)
  ImageView imgCompleteThree;
  @BindView(R.id.tv_guest_detail)
  TextView tvGuestDetail;
  @BindView(R.id.tv_payment)
  TextView tvPayment;
  @BindView(R.id.tv_order)
  TextView tvOrder;
  @BindView(R.id.ly_content)
  FrameLayout lyContent;

  @Inject
  ErrorMessageFactory errorMessageFactory;

  private int position = STEP_ONE;

  private PaymentDetailCollector paymentDetailCollector;

  private ProgressDialog progressDialog;
  private RoomCheckoutPresenter.Params paramsget;

  public static void start(Activity context, RoomCheckoutPresenter.Params params, int requestCode) {
    Intent starter = new Intent(context, RoomCheckoutActivity.class);
    starter.putExtra("params", params);
    context.startActivityForResult(starter, requestCode);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_room_checkout);
    ButterKnife.bind(this);

    Bundle extra = getIntent().getExtras();
    RoomCheckoutPresenter.Params params = extra.getParcelable("params");
    paramsget = extra.getParcelable("params");
    presenter.setValues(params);

    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(getString(R.string.loading));
    progressDialog.setCancelable(false);

    setToolbarTitle();
    setStepIndicator();
    gotoStep(STEP_ONE);
  }

  private void checkIfValidOrThrow(int position) {
    if (position < STEP_ONE || position > STEP_THREE) {
      throw new IllegalStateException("UnSupported step position " + position);
    }
  }

  private void setToolbarTitle() {
    tvTitleToolbar.setText(getResources().getString(R.string.checkout));
  }

  private void setStepIndicator() {
    if (position == STEP_THREE) {
      ivNavigationToolbar.setVisibility(View.INVISIBLE);
    } else {
      ivNavigationToolbar.setVisibility(View.VISIBLE);
    }
    switch (position) {
      case STEP_ONE:
        imgCompleteOne.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_one_complete));
        imgCompleteTwo.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_two));
        imgCompleteThree.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_three));
        lineOne.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineTwo.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
        lineThree.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
        lineFour.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
        break;
      case STEP_TWO:
        imgCompleteOne.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_one_success));
        imgCompleteTwo.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_two_complete));
        imgCompleteThree.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_three));
        lineOne.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineTwo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineThree.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineFour.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
        tvGuestDetail.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        tvPayment.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
        tvOrder.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        break;
      case STEP_THREE:
        imgCompleteOne.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_one_success));
        imgCompleteTwo.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_two_success));
        imgCompleteThree.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.icon_checkout_three_complete));
        lineOne.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineTwo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineThree.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        lineFour.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        tvGuestDetail.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        tvPayment.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        tvOrder.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
        break;
    }
  }

  private void collectDetails() {
    switch (position) {
      case STEP_ONE:
        presenter.collectGuestDetails();
        break;
      case STEP_TWO:
        presenter.collectPaymentDetails();
        break;
    }
  }

  private void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onBackPressed() {
    navigateBack();
  }

  private void navigateBack() {
    switch (position) {
      case STEP_ONE:
        finish();
        break;
      case STEP_TWO:
        gotoStep(STEP_ONE);
        break;
      case STEP_THREE:
        setResult(RESULT_OK);
        finish();
        break;
    }
  }

  @OnClick(R.id.ivNavigation_toolbar)
  public void OnClickHome() {
    navigateBack();
  }

  public void gotoStep(int position) {
    checkIfValidOrThrow(position);
    this.position = position;
    setStepIndicator();
    collectDetails();
  }

  @Inject
  @Override
  public void injectPresenter(RoomCheckoutPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void showGuestDetailFragment(int roomCount, int adultCount, List<Child> children,
                                      PhoneCode phoneCode,
                                      List<BookerInfo> bookerInfos, String email, boolean isBookForSomeone,
                                      UserDetailResponse userDetailResponse, String bedTypes, String bedGroup) {
    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();


    trans.replace(R.id.ly_content,
        CheckoutGuestDetailFragment.newInstance(
            new CheckoutGuestDetailFragment.Params(bookerInfos, phoneCode, roomCount, children,
                adultCount, isBookForSomeone, email, userDetailResponse, bedTypes, bedGroup)))
        .commit();
  }

  @Override
  public void render(Throwable error) {
    showToast(errorMessageFactory.getErrorMessage(error));
  }

  @Override
  public void goToPaymentReview(CheckoutPaymentReviewPresenter.Params params) {
    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

    CheckoutPaymentReviewFragment fragment =
        CheckoutPaymentReviewFragment.newInstance(params,paramsget);

    setPaymentDetailCollector(fragment);

    trans.replace(R.id.ly_content, fragment
    ).commit();
  }

  @Override
  public void goToStepThree(BookingDetail bookingDetail,
                            String email, String propertyName, List<TourismFee> fees) {

    this.position = STEP_THREE;
    setStepIndicator();

    Intent intent = getIntent();
    RoomCheckoutPresenter.Params params;
    params = intent.getParcelableExtra("params");



    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
    trans.replace(R.id.ly_content,
        OrderPlacedFragment.newInstance(bookingDetail, email, propertyName,params)).commit();
  }

  public void gotoStepTwo(boolean isBookForSomeone, String bedGroup, String email,
                          List<BookerInfo> bookerInfos) {

    presenter.setGuestDetail(isBookForSomeone, bedGroup, email, bookerInfos);
    gotoStep(STEP_TWO);
  }

  @Override
  public void showLoading() {
    if (isDestroyed()) return;
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    if (isDestroyed()) return;
    progressDialog.dismiss();
  }

  public void setPaymentDetailCollector(PaymentDetailCollector paymentDetailCollector) {
    this.paymentDetailCollector = paymentDetailCollector;
  }

  public void startBooking(PaymentDetail paymentDetail) {
    presenter.book(paymentDetail);
  }

  @Override
  public void showProcessingOrder() {
    dismissIfShowing();
    ProcessingOrderFragment dialog =
        ProcessingOrderFragment.newInstance(ProcessingOrderFragment.DIALOG_TYPE_PROCESSING_BOOKING);
    dialog.setCancelable(false);
    dialog.show(getSupportFragmentManager(), TAG_PROCESSING_DIALOG);
  }

  @Override
  public void hideProcessingOrder() {
    dismissIfShowing();
  }

  @Override
  public void renderBookingError(Exception error) {
    new AlertDialog.Builder(this)
        .setMessage(errorMessageFactory.getErrorMessage(error))
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> {
          dialog.dismiss();
          if (paymentDetailCollector != null) {
            paymentDetailCollector.scrollToTop();
          }
        }).show();
  }

  private void dismissIfShowing() {
    ProcessingOrderFragment fragment =
        (ProcessingOrderFragment) getSupportFragmentManager()
            .findFragmentByTag(TAG_PROCESSING_DIALOG);
    if (fragment != null && fragment.getDialog().isShowing()) {
      fragment.dismiss();
    }
  }
}
