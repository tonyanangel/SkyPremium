package com.skypremiuminternational.app.app.features.checkout.steptwo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartAdapter;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartPresenter;
import com.skypremiuminternational.app.app.features.checkout.CheckoutActivity;
import com.skypremiuminternational.app.app.features.checkout.stepone.CheckoutDeliveryFragment;
import com.skypremiuminternational.app.app.features.checkout.stepone.CheckoutDeliveryPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.CountryUtil;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.listener.AnimationListener;
import com.skypremiuminternational.app.app.view.SkyTextInputEditEventLayout;
import com.skypremiuminternational.app.app.view.SkyTextInputWithouLineLayout;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import timber.log.Timber;

import static com.skypremiuminternational.app.app.features.checkout.CheckoutActivity.STEP_ONE;
import static com.skypremiuminternational.app.app.features.checkout.CheckoutActivity.STEP_THREE;

/**
 * Created by aeindraaung on 2/2/18.
 */

public class CheckoutReviewFragment extends BaseFragment<CheckoutReviewPresenter>
    implements CheckoutReviewView<CheckoutReviewPresenter> {

  private static final String TAG_PROCESSING_DIALOG = "processing_dialog";

  @BindView(R.id.tv_error_message) TextView tvErrorMessage;
  @BindView(R.id.layout_shipping_fee) ViewGroup layoutShippingFee;
  @BindView(R.id.layout_delivery_fee) ViewGroup layoutDeliveryFee;
  @BindView(R.id.layout_tax) ViewGroup layoutTax;
  @BindView(R.id.tv_shipping_fee) TextView tvShippingFee;
  @BindView(R.id.tv_delivery_fee) TextView tvDeliveryFee;
  @BindView(R.id.tv_tax) TextView tvTax;
  @BindView(R.id.title_shopping_cart) TextView titleShoppingCart;
  @BindView(R.id.tv_item_count) TextView tvItemCount;
  @BindView(R.id.txt_title_edit) TextView txtTitleEdit;
  @BindView(R.id.layout_title_shopping_cart) RelativeLayout layoutTitleShoppingCart;
  @BindView(R.id.divider_subtitle) View dividerSubtitle;
  @BindView(R.id.txt_title_delivery_address) TextView txtTitleDeliveryAddress;
  @BindView(R.id.txt_address_edit) TextView txtAddressEdit;
  @BindView(R.id.txt_shipping_customer_name) TextView txtCustomerName;
  @BindView(R.id.txt_shipping_phone) TextView txtCustomerPhone;
  @BindView(R.id.txt_shipping_address) TextView txtCustomerAddress;
  @BindView(R.id.txt_billing_customer_name) TextView txtBillingCustomerName;
  @BindView(R.id.txt_billing_phone) TextView txtBillingCustomerPhone;
  @BindView(R.id.txt_billing_address) TextView txtBillingCustomerAddress;
  @BindView(R.id.layout_delivery_address) RelativeLayout layoutDeliveryAddress;
  @BindView(R.id.divider_delivery_address) View dividerDeliveryAddress;
  @BindView(R.id.txt_title_payment) TextView txtTitlePayment;
  @BindView(R.id.txt_visa_edit) TextView txtVisaEdit;
  @BindView(R.id.txt_credit_card) TextView txtCreditCard;
  @BindView(R.id.layout_visa_card) RelativeLayout layoutVisaCard;
  @BindView(R.id.divider_payment) View dividerPayment;
  @BindView(R.id.txt_subtotal) TextView txtSubtotal;
  @BindView(R.id.tv_subtotal_amount) TextView tvSubtotalAmount;
  @BindView(R.id.layout_subtotal) FrameLayout layoutSubtotal;
  @BindView(R.id.tv_discount_type) TextView tvDiscountType;
  @BindView(R.id.tv_discount_amount) TextView tvDiscountAmount;
  @BindView(R.id.tv_sky_dollar_earn) TextView tvSkyDollarEarn;
  @BindView(R.id.layout_discount_applied) FrameLayout layoutDiscountApplied;
  @BindView(R.id.layout_amt_review) ViewGroup layoutAmtReview;
  @BindView(R.id.divider_amount_review) View dividerAmountReview;
  @BindView(R.id.txt_grand_total) TextView txtGrandTotal;
  @BindView(R.id.tv_grand_total_amount) TextView tvGrandTotalAmount;
  @BindView(R.id.layout_amt_total) RelativeLayout layoutAmtTotal;
  @BindView(R.id.recycler_product) RecyclerView recyclerProduct;
  @BindView(R.id.txt_notice) TextView txtNotice;
  @BindView(R.id.img_card_brand) ImageView imgCardBrand;


  @Inject ErrorMessageFactory errorMessageFactory;

  private ShoppingCartAdapter adapter;
  private ProgressDialog progressDialog;
  private Unbinder unbinder;
  private List<CountryCode> countryCodes;
  private int checkoutType;
  private String paymentType;
  private boolean userHasRoyaltyPoints = false;
  private boolean isMembership = false;
  private boolean isFullRedemption = false;
  private CreditCardResponse creditCard;
//  Viet edit
//  public static CheckoutReviewFragment newInstance(int checkoutType, String paymentType,boolean isMembership) {
//    CheckoutReviewFragment fragment = new CheckoutReviewFragment();
//    fragment.checkoutType = checkoutType;
//    fragment.paymentType = paymentType;
//    fragment.isMembership = isMembership;
//    return fragment;
//  }
  private Address deliveryAddress;
  private BillingAddress billingAddress;
  public static CheckoutReviewFragment newInstance(int checkoutType, String paymentType, boolean isMembership,
                                                   Address deliveryAddress, BillingAddress billingAddress,
                                                   CreditCardResponse creditCard, boolean isFullRedemption) {
    CheckoutReviewFragment fragment = new CheckoutReviewFragment();
    fragment.checkoutType = checkoutType;
    fragment.paymentType = paymentType;
    fragment.isMembership = isMembership;
    fragment.deliveryAddress = deliveryAddress;
    fragment.billingAddress = billingAddress;
    fragment.creditCard = creditCard;
    fragment.isFullRedemption = isFullRedemption;
    return fragment;
  }

  @Override public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);

    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);

    setNoticeTextBefroeSubmit();
    setupRecyclerView();

    if(checkoutType==ShoppingCartPresenter.CHECKOUT_TYPE_BUY_NOW){
      presenter.getCountryCodesBuyNow();
    }else {
      presenter.getCountryCodes();
    }


    //presenter.getCartDetail();
    render(creditCard);
  }

  private void setNoticeTextBefroeSubmit() {
    String notice =
        "<html>By clicking on “Submit Order”, I acknowledge that I have read and agree to the terms & conditions on shipping, delivery, cancellations, refunds and returns, as per stipulated under the <a href=\"https://www.skypremium.com.sg/sg/legal#clubrules&termsconditions\">club rules and terms and conditions</a>.</html>";
    Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
        Html.fromHtml(notice));
    Spannable processedText = removeUnderlines(spannedText);
    txtNotice.setText(processedText);
    txtNotice.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private static Spannable removeUnderlines(Spannable p_Text) {
    URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);
    for (URLSpan span : spans) {
      int start = p_Text.getSpanStart(span);
      int end = p_Text.getSpanEnd(span);
      p_Text.removeSpan(span);
      span = new URLSpanNoUnderline(span.getURL());
      p_Text.setSpan(span, start, end, 0);
    }
    return p_Text;
  }

  public static class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String p_Url) {
      super(p_Url);
    }

    public void updateDrawState(TextPaint p_DrawState) {
      super.updateDrawState(p_DrawState);
      p_DrawState.setUnderlineText(false);
    }
  }

  private void setupRecyclerView() {
    recyclerProduct.setNestedScrollingEnabled(false);
    recyclerProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new ShoppingCartAdapter();
    recyclerProduct.setAdapter(adapter);
    recyclerProduct.setHasFixedSize(true);
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_checkout_review;
  }

  @Inject @Override public void injectPresenter(CheckoutReviewPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.txt_title_edit) public void onClickTitleEdit() {
    getActivity().finish();
  }

  @OnClick({R.id.txt_address_edit,R.id.txt_billing_address_edit}) public void onClickAddEdit() {
    ((CheckoutActivity) getActivity()).gotoStep(STEP_ONE);
  }

  @OnClick(R.id.txt_visa_edit) public void onClickVisaEdit() {
    ((CheckoutActivity) getActivity()).gotoStepOneAndScrollToEditCreditCard();
  }

  @Override public void showLoading(String message) {
    hideProcessingOrder();
    if (getActivity().isDestroyed()) return;
    progressDialog.setMessage(message);
    progressDialog.show();
  }

  @Override public void hideLoading() {
    if (!getActivity().isDestroyed() && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override public void render(Throwable error) {
    Timber.e(error);
    if (!getActivity().isDestroyed()) {
      new AlertDialog.Builder(getContext()).setMessage(errorMessageFactory.getErrorMessage(error))
          .setCancelable(false)
          .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss())
          .show();
    }
  }

  @Override public void render(List<CartDetailItem> cartDetails) {
    tvItemCount.setText(String.format(Locale.getDefault(), "(%d)", cartDetails.size()));
    if(checkoutType != ShoppingCartPresenter.CHECKOUT_TYPE_ESTORE
        ||checkoutType != ShoppingCartPresenter.CHECKOUT_TYPE_BUY_NOW){
      adapter.setDataList(cartDetails, true);
    }else {
      adapter.setDataList(cartDetails, false);
    }

  }

  @Override public void render(CartAllInformationResponse response) {
    if (response.getSubtotal() != null && !TextUtils.isEmpty(response.getSubtotal())) {
      tvSubtotalAmount.setText(
          String.format("S$%s",
              DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getSubTotal()))));
    } else {
      tvSubtotalAmount.setText(
          String.format("S$%s", DecimalUtil.roundTwoDecimals(0.0)));
    }
    if (response.getGrandTotal() != null && !TextUtils.isEmpty(response.getGrandTotal())) {
      tvGrandTotalAmount.setText(
          String.format("S$%s",
              DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getGrandTotal()))));
    } else {
      tvGrandTotalAmount.setText(
          String.format("S$%s", DecimalUtil.roundTwoDecimals(0.0)));
    }
    if (response.getShippingFee() != null && !response.getShippingFee().equalsIgnoreCase("")) {
      layoutShippingFee.setVisibility(View.GONE);
      tvShippingFee.setText(
          String.format("S$%s",
              DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getShippingFee()))));
    } else {
      layoutShippingFee.setVisibility(View.GONE);
    }

    if (response.getDeliveryFee() != null && !response.getDeliveryFee().equalsIgnoreCase("")) {
      layoutDeliveryFee.setVisibility(View.GONE);
      tvDeliveryFee.setText(
          String.format("S$%s",
              DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getDeliveryFee()))));
    } else {
      layoutDeliveryFee.setVisibility(View.GONE);
    }

    if (response.getTax() != null && !response.getTax().equalsIgnoreCase("")) {
      layoutTax.setVisibility(View.VISIBLE);
      Log.i("Aeindra", "tax : " + response.getTax());
      tvTax.setText(String.format("S$%s",
          DecimalUtil.roundTwoDecimals(Double.parseDouble(response.getTax()))));
    } else {
      layoutTax.setVisibility(View.GONE);
    }

    String redeemedDollars = response.getRedeemedSkyDollars();
    if (redeemedDollars != null
        && !redeemedDollars.equalsIgnoreCase("0")
        && !redeemedDollars.equalsIgnoreCase("")) {
      layoutDiscountApplied.setVisibility(View.VISIBLE);
      tvDiscountType.setText(R.string.hint_loyalty_sky_dollars_redeem);
      tvDiscountAmount.setText(
          String.format("S$%s",
              DecimalUtil.roundTwoDecimals(Double.parseDouble(redeemedDollars))));
    } else if (response.getDiscountAmount() != null && !response.getDiscountAmount()
        .equalsIgnoreCase("0") && response.getDiscountAmount().length() > 0) {
      layoutDiscountApplied.setVisibility(View.VISIBLE);
      tvDiscountType.setText(R.string.hint_promocode);
      String discount = response.getDiscountAmount();
      if (response.getDiscountAmount().startsWith("-")) {
        discount = response.getDiscountAmount().substring(1);
      }

      if(!discount.equalsIgnoreCase("0")){
        layoutDiscountApplied.setVisibility(View.VISIBLE);
      }else {
        layoutDiscountApplied.setVisibility(View.GONE);
      }

      tvDiscountAmount.setText(
          String.format("-S$%s", DecimalUtil.roundTwoDecimals(Double.parseDouble(discount))));
    } else {
      layoutDiscountApplied.setVisibility(View.GONE);
    }
    double skyDollarEarn = response.getExtensionAttributes().getTotalSkyDollarEarn();
    tvSkyDollarEarn.setText(
        String.format("S$%s", DecimalUtil.roundTwoDecimals(skyDollarEarn)));
  }

  @Override public void proceedToNextStep(Integer orderId) {
    ((CheckoutActivity) getActivity()).gotoStep(STEP_THREE, orderId);
  }

  @Override public void notifyCartItemStatusChanged() {
    adapter.notifyDataSetChanged();
  }

  @Override public void renderErrorMessage(String errorMessage) {
    tvErrorMessage.setText(errorMessage);
    tvErrorMessage.setVisibility(View.VISIBLE);
    AnimationUtil.expand(tvErrorMessage, new AnimationListener() {
      @Override public void onAnimationEnd(Animation animation) {
        //do nothing
      }
    });

    new Handler().postDelayed(() -> {
      if (!getActivity().isDestroyed()) {
        AnimationUtil.collapse(tvErrorMessage, new AnimationListener() {
          @Override public void onAnimationEnd(Animation animation) {
            tvErrorMessage.setVisibility(View.GONE);
          }
        });
      }
    }, 5000);
  }

  @Override public void render(Address address,BillingAddress billingAddress) {
    String mAddress="";
    String unitNumber = "";
    String company = "";
    String telephone = "";
    String streetAddress = "";
    String city = "";
    String state = "";
    String postCode = "";
    String countryName = "";

    txtCustomerName.setText(String.format("%s. %s %s", address.getPrefix(),address.getFirstname(), address.getLastname()));
    txtBillingCustomerName.setText(String.format("%s. %s %s",billingAddress.getSalutation() , billingAddress.getFirstname(), billingAddress.getLastname()));
    /*txtCustomerPhone.setText(
        //String.format("+%s %s", address.getCountryId(), address.getTelephone()));
        String.format("%s", address.getTelephone()));*/
    txtCustomerPhone.setVisibility(View.GONE);
    /*txtBillingCustomerPhone.setText(
        String.format("%s", billingAddress.getTelephone()));*/
    txtBillingCustomerPhone.setVisibility(View.GONE);

    // delivery address
//       mAddress = address.getStreet() != null && address.getStreet().size() > 0 ? address.getStreet().get(0) : "";

    city = address.getCity() ;
    postCode = address.getPostcode();
    telephone = address.getTelephone();
    company = address.getCompany();
    if (address.getCustomAttributes() != null && address.getCustomAttributes().size() > 0) {
      for (CustomAttribute customAttribute : address.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase(Constants.UNIT_NUMBER)) {
          unitNumber = customAttribute.getValue();
        }
      }
    }
    if(address.getStreet()!=null
        && address.getStreet().size()>0){
      streetAddress = address.getStreet().get(0);

    }
    if (address.getRegion()!= null) {
      state = (String) address.getRegion().getRegion();
      if (!TextUtils.isEmpty(state)) {
        state = state;
      }
    }
    if (countryCodes != null) {
      if (countryCodes.size() > 0) {
        for (CountryCode countryCode : countryCodes) {
          Log.e("CHECKOUT_COUNTRY","countryCode"+countryCode.getId());
          Log.e("CHECKOUT_COUNTRY","getCountryId"+address.getCountryId());
          if (countryCode.getId().equalsIgnoreCase(address.getCountryId())) {
              countryName = countryCode.getName();
            break;
          }
        }
      }
    }
    if(company!=null){
      mAddress += company ;
    }
    if(unitNumber!=null){
      if(TextUtils.isEmpty(company)){
        mAddress += ""+unitNumber ;
      }else {
        mAddress += "\n"+unitNumber ;
      }
    }
    if(streetAddress!=null){
      if(TextUtils.isEmpty(unitNumber)){
        mAddress += ""+streetAddress;
      }else {
        mAddress += " "+streetAddress;
      }
    }
    if(city!=null){
        mAddress += "\n"+city;
    }
    if(state!=null){
      if(TextUtils.isEmpty(city)){
        mAddress += ""+state;
      }else {
        mAddress += " "+state;
      }
    }
    if(countryName!=null){
        mAddress += "\n"+countryName;
    }
    if(postCode!=null){
      if(TextUtils.isEmpty(countryName)){
        mAddress += ""+postCode;
      }else {
        mAddress += " "+postCode;
      }
    }
    if(telephone!=null){
        mAddress += "\n"+telephone;
    }
    txtCustomerAddress.setText(mAddress);



    //billing address
    String mBillingAddress="";


    String unitNumberBill = "";
    String companyBill = "";
    String telephoneBill = "";
    String streetAddressBill = "";
    String cityBill = "";
    String stateBill = "";
    String postCodeBill = "";
    String countryNameBill = "";
    //mBillingAddress = billingAddress.getStreet() != null && billingAddress.getStreet().size() > 0 ? billingAddress.getStreet().get(0): "";


    companyBill = billingAddress.getCompany();
    postCodeBill = billingAddress.getPostcode();
    telephoneBill = billingAddress.getTelephone();
    unitNumberBill = billingAddress.getUnitNumber();
    cityBill = billingAddress.getCity();
    if(billingAddress.getStreet()!=null
        && billingAddress.getStreet().size()>0){
      streetAddressBill = billingAddress.getStreet().get(0);

    }
    if (billingAddress.getRegion()!= null) {
      stateBill = (String) billingAddress.getRegion().get(0).getRegion();
      if (!TextUtils.isEmpty(stateBill)) {
        stateBill = stateBill;
      }
    }
    if (countryCodes != null) {
      if (countryCodes.size() > 0) {
        for (CountryCode countryCode : countryCodes) {
          if (countryCode.getId().equalsIgnoreCase(billingAddress.getCountryId())) {

              countryNameBill = countryCode.getName();

            break;
          }
        }
      }
    }
    if(companyBill!=null){
      mBillingAddress += companyBill ;
    }
    if(unitNumberBill!=null){
      if(TextUtils.isEmpty(companyBill)){
        mBillingAddress += ""+unitNumberBill ;
      }else {
        mBillingAddress += "\n"+unitNumberBill ;
      }
    }
    if(streetAddressBill!=null){
      if(TextUtils.isEmpty(unitNumberBill)){
        mBillingAddress += ""+streetAddressBill;
      }else {
        mBillingAddress += " "+streetAddressBill;
      }
    }
    if(cityBill!=null){
      mBillingAddress += "\n"+cityBill;
    }
    if(stateBill!=null){
      if(TextUtils.isEmpty(cityBill)){
        mBillingAddress += ""+stateBill;
      }else {
        mBillingAddress += " "+stateBill;
      }
    }
    if(countryNameBill!=null){
      mBillingAddress += "\n"+countryNameBill;
    }
    if(postCodeBill!=null){
      if(TextUtils.isEmpty(countryNameBill)){
        mBillingAddress += ""+postCodeBill;
      }else {
        mBillingAddress += " "+postCodeBill;
      }
    }
    if(telephoneBill!=null){
      mBillingAddress += "\n"+telephoneBill;
    }
    txtBillingCustomerAddress.setText(mBillingAddress);
  }

  @Override public void render(CreditCardResponse card) {
    if (card == null) {
      if(isFullRedemption){
        txtCreditCard.setText(getString(R.string.card_selection_not_required_title));
        txtCreditCard.setTextSize(13);
        imgCardBrand.setVisibility(View.INVISIBLE);
      }
      return;
    }
    if (checkoutType == ShoppingCartPresenter.CHECKOUT_TYPE_RENEWAL_WITH_POINTS){
      layoutVisaCard.setVisibility(View.GONE);
    }else{
      layoutVisaCard.setVisibility(View.VISIBLE);
      if(isFullRedemption){
        txtCreditCard.setText(getString(R.string.card_selection_not_required_title));
        txtCreditCard.setTextSize(13);
        imgCardBrand.setVisibility(View.INVISIBLE);
      }else {
        String number;
        if (card.getLast4() != null) {
          if (!TextUtils.isEmpty(card.getLast4())) {
            number =
                getString(R.string.txt_first_sign_creditcard).toUpperCase() + " " + card.getLast4();
          } else {
            number = getString(R.string.txt_first_sign_creditcard).toUpperCase() + " " + getString(
                R.string.txt_null_creditcard).toUpperCase();
          }
        } else {
          number = getString(R.string.txt_first_sign_creditcard).toUpperCase() + " " + getString(
              R.string.txt_null_creditcard).toUpperCase();
        }
        txtCreditCard.setText(number);
        txtCreditCard.setVisibility(View.VISIBLE);
        imgCardBrand.setVisibility(View.VISIBLE);
      }
      if (card.getBrand() != null) {
        if (!TextUtils.isEmpty(card.getBrand())) {
          if (card.getBrand().equalsIgnoreCase(Constants.VISA_BRAND)) {
            imgCardBrand.setImageResource(R.drawable.ic_visa_blue_background);
          } else if (card.getBrand().equalsIgnoreCase(Constants.MASTER_BRAND)) {
            imgCardBrand.setImageResource(R.drawable.ic_master_pay);
          } else {
            imgCardBrand.setImageResource(R.drawable.ic_american_express_blue_background);
          }
        } else {
          imgCardBrand.setVisibility(View.INVISIBLE);
        }
      } else {
        imgCardBrand.setVisibility(View.INVISIBLE);
      }
    }

  }

  @Override public void hideProcessingOrder() {
    dismissIfShowing();
  }

  @Override public void showProcessingOrder() {
    dismissIfShowing();
    ProcessingOrderFragment dialog =
        ProcessingOrderFragment.newInstance(ProcessingOrderFragment.DIALOG_TYPE_PROCESSING_ORDER);
    dialog.setCancelable(false);
    dialog.show(getActivity().getSupportFragmentManager(), TAG_PROCESSING_DIALOG);
  }

  @Override public void showProcessingOrderError() {
    dismissIfShowing();
    ProcessingOrderFragment dialog =
        ProcessingOrderFragment.newInstance(ProcessingOrderFragment.DIALOG_TYPE_ERROR);
    dialog.setCancelable(false);
    if(checkoutType==ShoppingCartPresenter.CHECKOUT_TYPE_BUY_NOW){
      dialog.setActionListener(() -> presenter.placeOrderBuyNow(paymentType, billingAddress, creditCard));
    }else {
      dialog.setActionListener(() -> presenter.placeOrder(paymentType, billingAddress, creditCard));
    }
    dialog.show(getActivity().getSupportFragmentManager(), TAG_PROCESSING_DIALOG);
  }

  @Override
  public void renderCountryCode(List<CountryCode> countryList) {
    countryCodes = countryList;
    /*  20201707 - WIKI Viet Nguyen - fix bug set default delivery address*/
    if (!ObjectUtil.isNull(deliveryAddress)){
      render(deliveryAddress,billingAddress);
    }
  }


  private void dismissIfShowing() {
    ProcessingOrderFragment fragment =
        (ProcessingOrderFragment) getActivity().getSupportFragmentManager()
            .findFragmentByTag(TAG_PROCESSING_DIALOG);
    if (fragment != null && fragment.getDialog().isShowing()) {
      fragment.dismiss();
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.tv_next) void onClickNext() {
/*  20201707 - WIKI Viet Nguyen - fix bug set default address
    presenter.checkLimit(checkoutType, paymentType);
    */
    if(checkoutType==ShoppingCartPresenter.CHECKOUT_TYPE_BUY_NOW){
      presenter.checkLimitBuyNow(checkoutType, paymentType, deliveryAddress, billingAddress, creditCard);
    }else {
      presenter.checkLimit(checkoutType, paymentType, deliveryAddress, billingAddress, creditCard);
    }
  }

}
