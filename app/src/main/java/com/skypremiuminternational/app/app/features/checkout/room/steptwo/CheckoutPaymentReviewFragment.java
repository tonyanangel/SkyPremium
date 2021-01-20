package com.skypremiuminternational.app.app.features.checkout.room.steptwo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutActivity;
import com.skypremiuminternational.app.app.features.checkout.room.RoomCheckoutPresenter;
import com.skypremiuminternational.app.app.features.travel.ean.cancellationpolicy.CancellationPolicyBuilder;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragment;
import com.skypremiuminternational.app.app.model.PaymentDetail;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.CustomNoUnderLineLinkUtils;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.utils.listener.PaymentDetailCollector;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.data.model.ean.payment.CardOption;
import com.skypremiuminternational.app.domain.interactor.ean.GetPaymentOptions;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;

import dagger.android.support.AndroidSupportInjection;

import java.util.Calendar;

import javax.inject.Inject;

public class CheckoutPaymentReviewFragment extends BaseFragment<CheckoutPaymentReviewPresenter>
    implements CheckoutPaymentReviewView<CheckoutPaymentReviewPresenter>, PaymentDetailCollector {
  @BindView(R.id.tv_phone_code_error)
  TextView tvPhoneCodeError;
  @BindView(R.id.tv_summit_booking)
  TextView tvSummit;
  @BindView(R.id.tv_instruction_title)
  TextView tvInstructionTitle;
  @BindView(R.id.tv_instruction)
  TextView tvInstruction;
  @BindView(R.id.tv_special_instruction_title)
  TextView tvSpecialInstructionTitle;
  @BindView(R.id.tv_special_instruction)
  TextView tvSpecialInstruction;
  @BindView(R.id.tv_processing_country)
  TextView tvProcessingCountry;
  @BindView(R.id.root_layout)
  NestedScrollView nestedScrollView;
  @BindView(R.id.tv_payment_method_notice)
  TextView tvPaymentMethodNotice;
  @BindView(R.id.tv_refundability_notice)
  TextView tvRefundabilityNotice;
  @BindView(R.id.tv_grand_total)
  TextView tvGrandTotal;
  @BindView(R.id.tv_acknowledge)
  TextView tvAcknowledge;
  @BindView(R.id.tv_payment_review)
  TextView tvPaymentReview;
  @BindView(R.id.edt_card_type)
  SkyTextInputSignLayout edtCardType;
  @BindView(R.id.layout_credit_card_type)
  RelativeLayout layoutCreditCardType;
  @BindView(R.id.edt_card_number)
  SkyTextInputSignLayout edtCardNumber;
  @BindView(R.id.edt_exp_month)
  SkyTextInputSignLayout edtExpMonth;
  @BindView(R.id.edt_exp_year)
  SkyTextInputSignLayout edtExpYear;
  @BindView(R.id.edt_cvc)
  SkyTextInputSignLayout edtCvc;
  @BindView(R.id.layout_security)
  LinearLayout layoutSecurity;
  @BindView(R.id.edt_first_name)
  SkyTextInputSignLayout edtFirstName;
  @BindView(R.id.edt_last_name)
  SkyTextInputSignLayout edtLastName;
  @BindView(R.id.edt_email)
  SkyTextInputSignLayout edtEmail;
  @BindView(R.id.tv_phone_code)
  TextView tvPhoneCode;
  @BindView(R.id.edt_phone_number)
  SkyTextInputSignLayout edtPhoneNumber;
  @BindView(R.id.edt_street_address)
  SkyTextInputSignLayout edtStreetAddress;
  @BindView(R.id.edt_unit_number)
  SkyTextInputSignLayout edtUnitNumber;
  @BindView(R.id.edt_postal_code)
  SkyTextInputSignLayout edtPostalCode;
  @BindView(R.id.edt_city)
  SkyTextInputSignLayout edtCity;
  @BindView(R.id.edt_state)
  SkyTextInputSignLayout edtState;
  @BindView(R.id.edt_country)
  SkyTextInputSignLayout edtCountry;
  @BindView(R.id.cb_acknowledge)
  CheckBox cbAcknowledge;

  private String[] phones;
  private String[] codes;
  private String[] cardTypes;
  private int cardPosition = 0;
  private int phoneCodePosition = -1;
  private int countryPosition = -1;
  private String[] countries;


  static String VISA_CARD ="Visa";
  static String AMERICAN_CARD ="AmericanExpress";
  static String MASTER_CARD ="MasterCard";

  private RoomCheckoutPresenter.Params paramsget;

  @Inject
  CancellationPolicyBuilder cancellationPolicyBuilder;

  private CheckoutPaymentReviewPresenter.Params params;

  public static CheckoutPaymentReviewFragment newInstance(
          CheckoutPaymentReviewPresenter.Params params, RoomCheckoutPresenter.Params paramsget) {
    CheckoutPaymentReviewFragment fragment = new CheckoutPaymentReviewFragment();
    fragment.params = params;
    fragment.paramsget = paramsget;
    return fragment;
  }

  public CheckoutPaymentReviewFragment() {

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
    phones = new String[params.phoneCode().getPhoneCodes().size()];
    codes = new String[params.phoneCode().getPhoneCodes().size()];
    for (int i = 0; i < params.phoneCode().getPhoneCodes().size(); i++) {
      PhoneCode.PhoneCode_ phoneCode_ = params.phoneCode().getPhoneCodes().get(i);
      phones[i] = phoneCode_.getCountryName();
      codes[i] = phoneCode_.getDiallingCode();
      if(codes[i].equalsIgnoreCase(Constants.PHONE_CODE_DEFAULT)) {
        phoneCodePosition = i;
      }
      Log.d("TOANNO-PHONE",codes[i] );
    }

    String[] listcart = new String[params.cardOptions().size()];

    int j = 0;
    for (int i = 0; i < params.cardOptions().size(); i++) {

      CardOption cardOption = params.cardOptions().get(i);
      if(cardOption.getName().compareTo(AMERICAN_CARD)==0||
              cardOption.getName().compareTo(MASTER_CARD)==0||
              cardOption.getName().compareTo(VISA_CARD)==0){
        listcart[j] = cardOption.getName();
        j++;
      }

    }

    cardTypes = new String[j];
    for(int i=0;i<cardTypes.length;i++){
      cardTypes[i] = listcart[i];
    }


    countries = new String[params.countryCodes().size()];
    for (int i = 0; i < params.countryCodes().size(); i++) {
      countries[i] = StringUtil.toTitleCase(params.countryCodes().get(i).country_name.split(" "));
      String s  =  countries[i];
      if(s.equals(Constants.COUNTRY_DEFAULT)){
        countryPosition = i;
      }
      Log.d("TOANNO-COUNTRY",countries[i] );
    }



  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    actions();
    setNoticeTextBeforeSubmit();

    String formattedGrandTotal = DecimalUtil.roundTwoDecimals(this.params.grandTotal());

    tvRefundabilityNotice.setText(
        cancellationPolicyBuilder.build(params.cancelPenalty(), params.propertyName(),null,null));

    String paymentNotice =
        getString(R.string.payment_review_payment_method_notice, formattedGrandTotal);
    SpannableString spannablePayment = new SpannableString(paymentNotice);
    spannablePayment.setSpan(new StyleSpan(Typeface.BOLD),
        paymentNotice.indexOf(formattedGrandTotal) - 4,
        paymentNotice.indexOf(formattedGrandTotal) + formattedGrandTotal.length() + 1, 0);

    tvPaymentMethodNotice.setText(spannablePayment);

    tvSummit.setEnabled(cbAcknowledge.isChecked());

    edtState.setMandatory(false);

    tvGrandTotal.setText(String.format("S$%s", DecimalUtil.roundTwoDecimals(params.grandTotal())));

    edtCardNumber.setOnEditorActionListener((tv, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_NEXT) {
        hideKeyboard();
        onMonthClicked();
      }
      return true;
    });

    edtState.setOnEditorActionListener((tv, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_NEXT) {
        hideKeyboard();
        onCountryClicked();
      }
      return true;
    });

    renderInstructions();
    /*Set Default Country <ToanTran>*/
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    tvPhoneCode.setText(String.format("+%s",codes[phoneCodePosition]));
    edtCountry.setText(countries[countryPosition]);

    checkIfUnitedStates(countryPosition);
  }

  private void renderInstructions() {
    if (Validator.isTextValid(params.checkInInstructions())) {
      tvInstructionTitle.setVisibility(View.VISIBLE);
      tvInstruction.setVisibility(View.VISIBLE);
      tvInstruction.setText(Html.fromHtml(params.checkInInstructions()));
    } else {
      tvInstructionTitle.setVisibility(View.GONE);
      tvInstruction.setVisibility(View.GONE);
    }
    if (Validator.isTextValid(params.specialCheckInInStructions())) {
      tvSpecialInstructionTitle.setVisibility(View.VISIBLE);
      tvSpecialInstruction.setVisibility(View.VISIBLE);
      tvSpecialInstruction.setText(Html.fromHtml(params.specialCheckInInStructions()));
    } else {
      tvSpecialInstructionTitle.setVisibility(View.GONE);
      tvSpecialInstruction.setVisibility(View.GONE);
    }
  }

  private void actions() {
    cbAcknowledge.setOnCheckedChangeListener((compoundButton, b) -> {
      if (b) {
        setNoticeTextBeforeSubmit();
      }
      tvSummit.setEnabled(b);
    });
  }

  private void setNoticeTextBeforeSubmit() {
    Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
        Html.fromHtml(getString(R.string.payment_review_rules_acknowledgement_notice)));
    Spannable processedText = CustomNoUnderLineLinkUtils.removeUnderlines(spannedText);
    tvAcknowledge.setText(processedText);
    tvAcknowledge.setTextColor(ContextCompat.getColor(getContext(), R.color.textColorSecondary));
    tvAcknowledge.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void setNoticeErrorTextBeforeSubmit() {
    Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
        Html.fromHtml(getString(R.string.payment_review_rules_acknowledgement_notice_error)));
    Spannable processedText = CustomNoUnderLineLinkUtils.removeUnderlines(spannedText);
    tvAcknowledge.setText(processedText);
    tvAcknowledge.setTextColor(ContextCompat.getColor(getContext(), R.color.wineRed));
    tvAcknowledge.setMovementMethod(LinkMovementMethod.getInstance());
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_checkout_payment_review;
  }

  @Inject
  @Override
  public void injectPresenter(CheckoutPaymentReviewPresenter presenter) {
    super.injectPresenter(presenter);
  }

  @OnClick(R.id.tv_summit_booking)
  void onSummitClicked() {
    presenter.validate(collect());
  }

  @OnClick(R.id.card_type_overlay)
  public void onCreditCardTypeClicked() {
   /* new AlertDialog.Builder(getContext())
        .setSingleChoiceItems(cardTypes, -1,
            (dialog, which) -> {
              renderCardType(which);
              dialog.dismiss();
            }).show(); */

 /** <<START>> Nhat Nguyen-27032020 : fix Issue Completing Live (Expedia) Booking (crash when select cardtype on room checkout) <<START>> **/
    // setup the alert builder
    if(cardTypes.length >0) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
      builder.setTitle("--Please Select--");
      builder.setItems(cardTypes, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          renderCardType(which);
          dialog.dismiss();
        }
      });
      AlertDialog dialog = builder.create();
      dialog.show();
    }else{
      Toast.makeText(getContext(),"Failed to get Cart type. Please try again later.",Toast.LENGTH_SHORT).show();
    }
 /** <<END>> Nhat Nguyen-27032020 : fix Issue Completing Live (Expedia) Booking (crash when select cardtype on room checkout) <<END>>  **/
  }

  private void renderCardType(int which) {
    cardPosition = which;
    edtCardType.setText(cardTypes[which]);
    CardOption cardOption = params.cardOptions().get(which);
    if (Validator.isTextValid(cardOption.getProcessingCountry())) {
      tvProcessingCountry.setVisibility(View.VISIBLE);
      String processingCountry = searchProcessingCountry(cardOption.getProcessingCountry());
      if (Validator.isTextValid(processingCountry)) {
        tvProcessingCountry.setText(
            String.format(getString(R.string.payment_review_processing_country),
                processingCountry));
      } else {
        tvProcessingCountry.setVisibility(View.INVISIBLE);
      }
    } else {
      tvProcessingCountry.setVisibility(View.INVISIBLE);
    }

   /* if (cardOption.getCardType().equalsIgnoreCase(GetPaymentOptions.CARD_TYPE_MASTER_CARD) ||
        cardOption.getCardType().equalsIgnoreCase(GetPaymentOptions.CARD_TYPE_VISA)) {
      edtCvc.setMaxLength(3);
    } else { */
      edtCvc.setMaxLength(4);
   // }
  }

  @Nullable
  private String searchProcessingCountry(String processingCountry) {
    for (ISOCountry isoCountry : params.countryCodes()) {
      if (isoCountry.country_code.equalsIgnoreCase(processingCountry)) {
        return isoCountry.country_name;
      }
    }
    return null;
  }

  @OnClick(R.id.month_overlay)
  void onMonthClicked() {
    final String[] months =
        {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    new AlertDialog.Builder(getContext()).setTitle("Choose Month")
        .setItems(months, (dialog, item) -> edtExpMonth.setText(months[item]))
        .show();
  }

  @OnClick(R.id.year_overlay)
  void onYearClicked() {
    final String[] years = getYears();
    new AlertDialog.Builder(getContext()).setTitle("Choose Year")
        .setItems(years, (dialog, item) -> edtExpYear.setText(years[item]))
        .show();
  }

  @OnClick(R.id.tv_phone_code)
  void onPhoneCodeClicked() {
    new AlertDialog.Builder(getContext()).setTitle("Choose dial code")
        .setSingleChoiceItems(phones, phoneCodePosition, (dialog, position) -> {
          if (tvPhoneCodeError.getVisibility() == View.VISIBLE) {
            tvPhoneCodeError.setVisibility(View.INVISIBLE);
          }
          tvPhoneCode.setText(String.format("+%s", codes[position]));
          phoneCodePosition = position;
          dialog.dismiss();
        })
        .setPositiveButton(R.string.btn_label_cancel, null)
        .show();
  }

  @OnClick(R.id.country_overlay)
  void onCountryClicked() {
    new AlertDialog.Builder(getContext()).setTitle("Choose country")
        .setSingleChoiceItems(countries, countryPosition, (dialog, item) -> {
          edtCountry.setText(countries[item]);
          countryPosition = item;
          checkIfUnitedStates(item);
          dialog.dismiss();
        })
        .setPositiveButton(R.string.btn_label_cancel, null)
        .show();
  }

  private void checkIfUnitedStates(int item) {
    if (countries[item].trim()
        .equalsIgnoreCase(CheckoutPaymentReviewPresenter.COUNTRY_UNITED_STATES)) {
      edtState.setHint(getString(R.string.state_with_sign));
      edtState.setMandatory(true);
    } else {
      edtState.setHint(getString(R.string.state));
      edtState.setMandatory(false);
    }
  }

  private String[] getYears() {
    String[] years = new String[10];
    final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    for (int i = 0; i < years.length; i++) {
      years[i] = "" + (currentYear + i);
    }
    return years;
  }

  @Override
  public PaymentDetail collect() {
    PaymentDetail paymentDetail = new PaymentDetail();
    paymentDetail.cardType = edtCardType.getText();
    if (cardPosition != -1) {
     // paymentDetail.cardTypeAbbr = params.cardOptions().get(cardPosition).getCardType();
    }
    paymentDetail.phoneCode = tvPhoneCode.getText().toString();
    paymentDetail.country = edtCountry.getText();
    paymentDetail.cardNumber = edtCardNumber.getText();
    paymentDetail.expiryMonth = edtExpMonth.getText();
    paymentDetail.expiryYear = edtExpYear.getText();
    paymentDetail.securityCode = edtCvc.getText();
    paymentDetail.firstName = edtFirstName.getText();
    paymentDetail.lastName = edtLastName.getText();
    paymentDetail.emailAddress = edtEmail.getText();
    paymentDetail.phoneNumber = edtPhoneNumber.getText();
    paymentDetail.streetAddress = edtStreetAddress.getText();
    paymentDetail.unitNumber = edtUnitNumber.getText();
    paymentDetail.postalCode = edtPostalCode.getText();
    paymentDetail.city = edtCity.getText();
    paymentDetail.state = edtState.getText();
    paymentDetail.countryPosition = countryPosition;
    paymentDetail.phoneCodePosition = phoneCodePosition;
    paymentDetail.cardPosition = cardPosition;
    paymentDetail.hasReadAndAcceptRules = cbAcknowledge.isChecked();
    if (countryPosition != -1) {
      paymentDetail.countryCode = params.countryCodes().get(countryPosition).country_code + "";
    }
    paymentDetail.processingCountry = tvProcessingCountry.getText().toString();
    paymentDetail.cancellationPolicy = tvRefundabilityNotice.getText().toString();
    paymentDetail.fees = params.fees();
    return paymentDetail;
  }

  @Override
  public void scrollToTop() {
    nestedScrollView.scrollTo(0, 0);
  }

  @Override
  public void renderError(CheckoutPaymentReviewPresenter.ValidationResult result,
                          boolean showDialog) {
    String errorMessage = "";
    String errorMessage2 = "";
    if (result.isCardTypeHasError()) {
      edtCardType.showError(getString(R.string.payment_review_card_type_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_card_type);
      }
    }

    if (result.isCardNumberHasError()) {
      edtCardNumber.showError(getString(R.string.payment_review_card_number_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_card_error);
      }
    }
    if (result.isExpMonthHasError()) {
      edtExpMonth.showError(getString(R.string.payment_review_expiry_month_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_exp_month_error);
      }
    } else {
      edtExpMonth.setText(edtExpMonth.getText());
    }
    if (result.isExpYearHasError()) {
      edtExpYear.showError(getString(R.string.payment_review_expiry_year_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_exp_year_error);
      }
    } else {
      edtExpYear.setText(edtExpYear.getText());
    }
    if (result.isSecurityCodeHasError()) {
      edtCvc.showError(getString(R.string.payment_review_security_code_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_cvv_error);
      }
    }
    if (result.isFirstNameHasError()) {
      edtFirstName.showError(getString(R.string.payment_review_first_name_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_first_name_error);
      }
    }
    if (result.isLastNameHasError()) {
      edtLastName.showError(getString(R.string.payment_review_last_name_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_last_name_error);
      }
    }

    if (result.isEmailHasError()) {
      edtEmail.showError(getString(R.string.payment_review_email_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_email_error);
      }
    }

    if (result.isPhoneCodeHasError()) {
      tvPhoneCodeError.setVisibility(View.VISIBLE);
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_phone_code_error);
      }
    } else {
      tvPhoneCodeError.setVisibility(View.INVISIBLE);
    }

    if (result.isPhoneNumberHasError()) {
      edtPhoneNumber.showError(getString(R.string.payment_review_phone_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_phone_error);
      }
    }
    if (result.isStreetHasError()) {
      edtStreetAddress.showError(getString(R.string.payment_review_street_error));
      errorMessage = getString(R.string.payment_review_unit_number_limit_error);
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_street_error);
      }
    }
    if (result.isPostalCodeHasError()) {
      edtPostalCode.showError(getString(R.string.payment_review_postcode_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_post_code_error);
      }
    }

    if (result.isUnitNumberHasError()) {
      edtUnitNumber.showError(getString(R.string.payment_review_unit_number_error));
      errorMessage = getString(R.string.payment_review_unit_number_limit_error);
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_unit_number_error);
      }
    }

    if (result.isCityHasError()) {
      edtCity.showError(getString(R.string.payment_review_city_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_city_error);
      }
    }
    if (result.isStateHasError()) {
      edtState.showError(getString(R.string.payment_review_state_error));
      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_invalid_state_error);
      }
    }
    if (result.isCountryHasError()) {
      edtCountry.showError(getString(R.string.payment_review_country_error));
    }

    if (!result.isAcknowledged()) {
      setNoticeErrorTextBeforeSubmit();
      Animation shakeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
      tvAcknowledge.startAnimation(shakeAnimation);

      if (!Validator.isTextValid(errorMessage)) {
        errorMessage = getString(R.string.payment_review_not_acknowledged_error);
      }
    }
    if (showDialog) {
      showValidationErrorDialog(errorMessage);
    }
  }

  private void showValidationErrorDialog(String errorMessage) {
    new AlertDialog.Builder(getContext()).setCancelable(false)
        .setMessage(errorMessage)
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss()).show();
  }

  @Override
  public void startBooking(PaymentDetail paymentDetail) {
    ((RoomCheckoutActivity) (getActivity())).startBooking(paymentDetail);
  }

  @Override
  public void showMandatoryNotFilledError() {
    new AlertDialog.Builder(getContext()).setCancelable(false)
        .setMessage(R.string.forms_validation_error_message)
        .setPositiveButton(R.string.btn_label_ok, (dialog, which) -> dialog.dismiss()).show();
  }

  private void hideKeyboard() {
    View view = getActivity().getCurrentFocus();
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      if (imm != null) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
    }
  }
}
