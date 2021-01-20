package com.skypremiuminternational.app.app.features.checkout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.features.checkout.stepone.CheckoutDeliveryFragment;
import com.skypremiuminternational.app.app.features.checkout.stepthree.CheckoutOrderPlacedFragment;
import com.skypremiuminternational.app.app.features.checkout.steptwo.CheckoutReviewFragment;
import com.skypremiuminternational.app.app.features.demo_activity.DemoActvity;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;

import java.util.List;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

/**
 * Created by aeindraaung on 1/29/18.
 */

public class CheckoutActivity extends BaseActivity<CheckoutPresenter>
    implements CheckoutView<CheckoutPresenter> {
  public static final int STEP_ONE = 0;
  public static final int STEP_TWO = 1;
  public static final int STEP_THREE = 2;
  private static final String EXTRA_KEY_CHECKOUT_TYPE = "EXTRA_KEY_CHECKOUT_TYPE";
  private static final String EXTRA_KEY_PAYMENT_TYPE = "EXTRA_KEY_PAYMENT_TYPE";
  private static final String EXTRA_KEY_CHECK_REDEMPTION_FULL = "EXTRA_KEY_CHECK_REDEMPTION_FULL";

  @BindView(R.id.tvTitle_toolbar)
  TextView toolbarTitle;
  @BindView(R.id.img_complete_one)
  ImageView imgCompleteOne;
  @BindView(R.id.img_complete_two)
  ImageView imgCompleteTwo;
  @BindView(R.id.img_complete_three)
  ImageView imgCompleteThree;
  @BindView(R.id.view_one)
  View lineOne;
  @BindView(R.id.view_two)
  View lineTwo;
  @BindView(R.id.view_three)
  View lineThree;
  @BindView(R.id.view_four)
  View lineFour;
  @BindView(R.id.txt_delivery)
  TextView txtDelivery;
  @BindView(R.id.txt_review)
  TextView txtReview;
  @BindView(R.id.txt_order)
  TextView txtOrder;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  private int position = STEP_ONE;

  private ProgressDialog progressDialog;

  private int checkoutType = ShoppingCartPresenter.CHECKOUT_TYPE_ESTORE;

  private String paymentType = "";

  private boolean isFullRedemption = false;

  private boolean isMembership = false;

  public static void startMe(Activity context, int requestCode, int checkoutType,
                             String paymentType, boolean isFullRedemption, boolean isMembership) {
    Intent intent = new Intent(context, CheckoutActivity.class);
    intent.putExtra(EXTRA_KEY_CHECKOUT_TYPE, checkoutType);
    intent.putExtra(EXTRA_KEY_PAYMENT_TYPE, paymentType);
    intent.putExtra(EXTRA_KEY_CHECK_REDEMPTION_FULL, isFullRedemption);
    intent.putExtra("ismembership", isMembership);
    context.startActivityForResult(intent, requestCode);
  }
  public static void startMe(Activity context, int requestCode, int checkoutType,
                             String paymentType, boolean isFullRedemption, boolean isMembership,String from) {
    Intent intent = new Intent(context, CheckoutActivity.class);
    intent.putExtra(EXTRA_KEY_CHECKOUT_TYPE, checkoutType);
    intent.putExtra(EXTRA_KEY_PAYMENT_TYPE, paymentType);
    intent.putExtra(EXTRA_KEY_CHECK_REDEMPTION_FULL, isFullRedemption);
    intent.putExtra("ismembership", isMembership);
    intent.putExtra(ShoppingCartActivity.FROM, from);
    context.startActivityForResult(intent, requestCode);
  }

/* 20201707 - WIKI Viet Nguyen - fix bug set default address*/
  private Address deliveryAddress;
  private BillingAddress billingAddress;
  private CreditCardResponse creditCard;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_checkout);
    ButterKnife.bind(this);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      checkoutType = bundle.getInt(EXTRA_KEY_CHECKOUT_TYPE);
      paymentType = bundle.getString(EXTRA_KEY_PAYMENT_TYPE);
      isFullRedemption = bundle.getBoolean(EXTRA_KEY_CHECK_REDEMPTION_FULL);
      isMembership = bundle.getBoolean("ismembership");
    }
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    if(isMembership){

    }
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
    toolbarTitle.setText(getResources().getString(R.string.checkout));
  }

  private void setStepIndicator() {
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
        txtDelivery.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        txtReview.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
        txtOrder.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
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
        txtDelivery.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        txtReview.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
        txtOrder.setTextColor(
            ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
        break;
    }
  }

  private void setFragment(Integer orderId) {
    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
    switch (position) {
      case STEP_ONE:
/*    20201707 - WIKI Viet Nguyen - fix bug set default address
      trans.replace(R.id.framelayout, CheckoutDeliveryFragment.newInstance(false,
                checkoutType, isFullRedemption,isMembership));*/
        trans.replace(R.id.framelayout, CheckoutDeliveryFragment.newInstance(false,
                checkoutType, isFullRedemption,isMembership));
        break;
      case STEP_TWO:
        trans.replace(R.id.framelayout, CheckoutReviewFragment.newInstance(checkoutType, paymentType,
                isMembership, deliveryAddress, billingAddress, creditCard, isFullRedemption));
        break;
      case STEP_THREE:
        if (orderId != null) {
          trans.replace(R.id.framelayout, CheckoutOrderPlacedFragment.newInstance(orderId,checkoutType,isMembership, isFullRedemption));
        } else {
          showToast("Order detail is null");
        }
        break;
    }
    trans.commitAllowingStateLoss();
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
        finish(); //wont happen as Next button is disappeared in Step one
        break;
      case STEP_TWO:
        gotoStep(STEP_ONE); // wont happen as Next button is disappeared in Step two
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
    setFragment(null);
    setStepIndicator();
  }

/*  20201707 - WIKI Viet Nguyen - fix bug set default address*/
  public void gotoStep(int position, Address deliveryAddress, BillingAddress billingAddress,
                       CreditCardResponse creditCard) {
    checkIfValidOrThrow(position);
    this.position = position;
    this.deliveryAddress = deliveryAddress;
    this.billingAddress = billingAddress;
    this.creditCard = creditCard;
    setFragment(null);
    setStepIndicator();
  }

  public void gotoStep(int position, Integer orderId) {
    checkIfValidOrThrow(position);
    this.position = position;
    setFragment(orderId);
    setStepIndicator();
  }

  @Inject
  @Override
  public void injectPresenter(CheckoutPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void showLoading(String message) {
    if (!isDestroyed()) {
      progressDialog.setMessage(message);
      progressDialog.show();
    }
  }

  @Override
  public void hideLoading() {
    if (!isDestroyed() && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void render(Throwable error) {
    if (isDestroyed()) return;
    new AlertDialog.Builder(this).setMessage(errorMessageFactory.getErrorMessage(error))
        .setCancelable(false)
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
        .show();
  }

  public void gotoStepOneAndScrollToEditCreditCard() {
    this.position = STEP_ONE;
    setStepIndicator();
    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
    trans.replace(R.id.framelayout, CheckoutDeliveryFragment.newInstance(true, checkoutType, isFullRedemption,isMembership))
        .commit();
  }
}
