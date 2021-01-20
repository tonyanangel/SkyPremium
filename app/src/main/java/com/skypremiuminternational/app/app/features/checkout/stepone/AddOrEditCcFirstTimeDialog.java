package com.skypremiuminternational.app.app.features.checkout.stepone;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.KBUtil;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.app.view.SkyTextInputWithoutSignLayout;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.billingaddress.RegionItem;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.country_code.CountryCodeCC;
import com.skypremiuminternational.app.domain.models.user.CreditCardResponse;
import com.stripe.android.model.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import static com.skypremiuminternational.app.app.features.checkout.stepone.AddOrEditAddressDialog.EXTRA_KEY_COUNTRY;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class AddOrEditCcFirstTimeDialog extends BottomSheetDialogFragment {

  public static final String KEY_DIALOG_TYPE = "key";
  public static final String EXTRA_KEY = "visa_key";
  public static final int ADD_CARD = 0;
  public static final int EDIT_CARD = 1;

  @BindView(R.id.img_checkbox)
  ImageView imgCheck;
  @BindView(R.id.img_close)
  ImageView imgClose;
  @BindView(R.id.img_delete)
  ImageView imgDelete;
  @BindView(R.id.tv_save)
  TextView tvSave;
  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.v_exp_month)
  View vExpiryMonth;
  @BindView(R.id.v_exp_year)
  View vExpiryYear;
  @BindView(R.id.input_exp_month)
  SkyTextInputSignLayout inputExpMonth;
  @BindView(R.id.input_exp_year)
  SkyTextInputSignLayout inputExpYear;
  @BindView(R.id.input_cvc)
  SkyTextInputSignLayout inputCVC;
  @BindView(R.id.input_card_number)
  SkyTextInputSignLayout inputCardNumber;
  @BindView(R.id.txt_set_primary_credit)
  TextView txtSetPrimaryCredit;
  @BindView(R.id.img_card_brand)
  ImageView imgCardBrand;
  @BindView(R.id.root_layout)
  ViewGroup rootLayout;
  @BindView(R.id.layout_checkbox)
  ConstraintLayout layoutCheckBox;
  @BindView(R.id.layout_disable_checked)
  FrameLayout layoutDisableChecked;

  //

  @BindView(R.id.input_owner_name)
  SkyTextInputSignLayout inputOwnerName;
  @BindView(R.id.input_phone_number)
  SkyTextInputSignLayout inputPhoneNumber;
  @BindView(R.id.input_post_code)
  SkyTextInputSignLayout inputPostCode;
  @BindView(R.id.input_address)
  SkyTextInputSignLayout inputAddress;
  @BindView(R.id.input_city)
  SkyTextInputSignLayout inputCity;
  @BindView(R.id.input_state)
  SkyTextInputLayout inputState;
  @BindView(R.id.input_country)
  SkyTextInputSignLayout inputCountry;


  private Unbinder unbinder;
  private boolean isChecked;
  private ActionListener actionListener;
  private CreditCardResponse creditCard;
  private View contentView;
  private List<CountryCodeCC> countryCodes;
  private List<CountryCodeCC> finalCountryCodes;
  private String selectedCountryValue = "";

  int countryItem = 0;

  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(Dialog dialog, int style) {
    super.setupDialog(dialog, style);

    contentView = View.inflate(getContext(), R.layout.dialog_edit_visa_bottomsheet_first_time, null);
    unbinder = ButterKnife.bind(this, contentView);

    Bundle bundle = getArguments();
    int key = bundle.getInt(KEY_DIALOG_TYPE, ADD_CARD);
    try {
      creditCard = (CreditCardResponse) bundle.getSerializable(EXTRA_KEY);
      countryCodes = (List<CountryCodeCC>) bundle.getSerializable(EXTRA_KEY_COUNTRY);

    } catch (NullPointerException ex) {

    }

    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
    int height = displayMetrics.heightPixels;
    int maxHeight = (int) (height * 0.88);
    dialog.setContentView(contentView);

    BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) contentView.getParent());
    mBehavior.setPeekHeight(maxHeight);

    ((View) contentView.getParent()).setBackgroundColor(
        getResources().getColor(R.color.transparent));

    if (key == ADD_CARD) {
      imgCheck.setEnabled(false);
      tvTitle.setText(getString(R.string.txt_add_credit_card));
      imgDelete.setVisibility(View.GONE);
      layoutCheckBox.setVisibility(View.GONE);
    } else {
      imgCheck.setEnabled(true);
      tvTitle.setText(getString(R.string.txt_edit_credit_card));
      imgDelete.setVisibility(View.VISIBLE);
      if (creditCard.getBrand().equalsIgnoreCase(Constants.AMERICAN_EXPRESS_BRAND)) {
        inputCVC.setText("****");
      } else {
        inputCVC.setText("***");
      }
      inputCVC.setEnabled(false);
    }

    if (creditCard != null) {
      renderCreditCard(creditCard);
    }

    inputCardNumber.edt.setOnEditorActionListener((v, actionId, event) -> {
      if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId
          == EditorInfo.IME_ACTION_NEXT)) {
        hideKeyboard();
        validateCard();
        onClickMonth();
      }
      return false;
    });

    inputCardNumber.setItemClickListener(item -> {
      if (item != null) {
        if (item.equalsIgnoreCase("empty")) {
          imgCardBrand.setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  private void renderCreditCard(CreditCardResponse creditCard) {
    if (creditCard.getLast4() != null && !isEmpty(creditCard.getLast4())) {
      inputCardNumber.setText("XXXX XXXX XXXX " + creditCard.getLast4());
      inputCardNumber.setEnabled(false);
    }
    if (creditCard.getBrand() != null && !TextUtils.isEmpty(creditCard.getBrand())) {
      checkCreditCardType(creditCard.getBrand());
    } else {
      imgCardBrand.setVisibility(View.INVISIBLE);
    }
    if (creditCard.getExpMonth() != 0) {
      inputExpMonth.setText(String.valueOf(creditCard.getExpMonth()));
    }
    if (creditCard.getExpYear() != 0) {
      inputExpYear.setText(String.valueOf(creditCard.getExpYear()));
    }

    if (creditCard.getCartDefault() != null) {
      if (creditCard.getCartDefault()) {
        imgCheck.setBackgroundResource(R.drawable.ic_checked);
        imgCheck.setEnabled(false);
        txtSetPrimaryCredit.setEnabled(false);

        // 20200720 - Wiki Toan Tran - Disable condition delete card
        //imgDelete.setVisibility(View.INVISIBLE);
        layoutDisableChecked.setVisibility(View.VISIBLE);
      } else {
        layoutDisableChecked.setVisibility(View.GONE);
      }
    }
  }

  @OnClick(R.id.img_delete)
  void onClickDelete() {
    if (creditCard != null) {
      actionListener.onDeleteClicked(creditCard);
    }
  }

  @OnClick({R.id.img_checkbox, R.id.txt_set_primary_credit})
  void onClickChecked() {
    if (!isChecked) {
      isChecked = true;
      imgCheck.setBackgroundResource(R.drawable.ic_checked);
    } else {
      isChecked = false;
      imgCheck.setBackgroundResource(R.drawable.ic_circle);
    }
  }

  @OnClick(R.id.tv_save)
  void onSave() {
    if (actionListener != null) {
      if (validateCardData()) {
        actionListener.onSaveClicked(buildCreditCard());
      }
    }
  }

  @OnClick(R.id.img_close)
  void onClickedClose() {
    dismiss();
  }

  @OnClick(R.id.v_exp_month)
  void onClickMonth() {
    final String[] months =
        {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    new AlertDialog.Builder(getContext()).setTitle("Choose Month")
        .setItems(months, (dialog, item) -> {
          inputExpMonth.setText(months[item]);
        })
        .show();
  }

  @OnClick(R.id.v_exp_year)
  void onClickYear() {
    final String[] years = getYears();
    new AlertDialog.Builder(getContext()).setTitle("Choose Year")
        .setItems(years, (dialog, item) -> {
          inputExpYear.setText(years[item]);
        })
        .show();
  }

  private String[] getYears() {
    String[] years = new String[10];
    final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    for (int i = 0; i < years.length; i++) {
      years[i] = "" + (currentYear + i);
    }
    return years;
  }

  private boolean validateCardData() {
    boolean isValidate = true;


    if (isEmpty(inputOwnerName.getText())) {
      inputOwnerName.showError("Full Name");
      isValidate = false;
    } else {
      if (!inputOwnerName.getText().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_full_name));
        isValidate = false;
      }
    }
    if (isEmpty(inputPhoneNumber.getText())) {
      inputPhoneNumber.showError("mobile number");
      isValidate = false;
    }
    if (isEmpty(inputAddress.getText())) {
      inputAddress.showError("street address");
      isValidate = false;
    }
    if (isEmpty(inputPostCode.getText())) {
      inputPostCode.showError("zip / postal code");
      isValidate = false;
    }
    if (isEmpty(inputCity.getText())) {
      inputCity.showError("city");
      isValidate = false;
    }

    if (isEmpty(inputCountry.getText())) {
      inputCountry.showError("country");
      isValidate = false;
    }


    if (isEmpty(inputCardNumber.getText())) {
      inputCardNumber.showError(getString(R.string.error_card_number));
      isValidate = false;
    }
    if (isEmpty(inputExpMonth.getText())) {

      inputExpMonth.showError(getString(R.string.error_expired_month));
      isValidate = false;
    }
    if (isEmpty(inputExpYear.getText())) {
      inputExpYear.showError(getString(R.string.error_expired_year));
      isValidate = false;
    }
    if (isEmpty(inputCVC.getText())) {
      inputCVC.showError(getString(R.string.error_expired_cvc));
      isValidate = false;
    } else {
      /*<<START>> 20200401 - WIKI Toan Tran - check validate for CVV/CVC*/
      Card card = new Card(inputCardNumber.getText(), 0, 0, "");
      if (!isEmpty(inputCardNumber.getText())) {
        if (card.getType().equalsIgnoreCase(Constants.AMERICAN_EXPRESS_BRAND)) {
          // just AMERICAN EXPRESS
          if (inputCVC.getText().length() != 4) {
            createErrorDialog(getString(R.string.error_invalid));
            inputCVC.showError(getString(R.string.error_expired_cvc));
            isValidate = false;
          }
        } else if (inputCVC.getText().length() != 3) {
          createErrorDialog(getString(R.string.error_invalid));
          inputCVC.showError(getString(R.string.error_expired_cvc));
          isValidate = false;
        }
      }
      /*<<END>> 20200401 - WIKI Toan Tran - check validate for CVV/CVC*/
    }
    return isValidate;
  }

  private void createErrorDialog(String message) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
        .show();
  }

  private void validateCard() {
    Card card = new Card(inputCardNumber.getText(), 0, 0, "");
    checkCreditCardType(card.getType());
  }

  private CreditCardResponse buildCreditCard() {
    if (creditCard == null) {
      creditCard = new CreditCardResponse();
    }
    creditCard.setCardNumber(inputCardNumber.getText());
    creditCard.setExpMonth(Integer.parseInt(inputExpMonth.getText()));
    creditCard.setExpYear(Integer.parseInt(inputExpYear.getText()));
    creditCard.setCvcCheck(inputCVC.getText());
    creditCard.setSetAsDefault(isChecked);

    creditCard.setName(inputOwnerName.getText());
    creditCard.setPhoneNumber(inputPhoneNumber.getText());
    creditCard.setAddressLine1(inputAddress.getText());
    creditCard.setAddressZip(inputPostCode.getText());
    creditCard.setAddressCity(inputCity.getText());
    creditCard.setAddressState(inputState.getText());
    creditCard.setCountry(selectedCountryValue);
    return creditCard;
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public interface ActionListener {
    void onSaveClicked(CreditCardResponse creditCard);

    void onDeleteClicked(CreditCardResponse creditCard);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }


  @OnClick(R.id.vCountry)
  void onClickCountry() {

    String[] countries = new String[countryCodes.size()];
    for (int i = 0; i < countryCodes.size(); i++) {
      countries[i] = StringUtil.toTitleCase(countryCodes.get(i).getLabel().split(" "));
    }

    final String[] countryArr = Arrays.copyOfRange(countries,1,countries.length);
    finalCountryCodes = countryCodes.subList(1,countryCodes.size());


    new AlertDialog.Builder(getContext()).setTitle("Choose country")
        .setSingleChoiceItems(countryArr, countryItem, (dialog, item) -> {
          inputCountry.setText(countryArr[item]);
          selectedCountryValue = finalCountryCodes.get(item).getValue();
          countryItem = item;
          CommonUtils.hideKeyboard(getActivity());
          dialog.dismiss();
        })
        .setPositiveButton(getString(R.string.btn_label_cancel), (dialog, item) -> {
          CommonUtils.hideKeyboard(getActivity());
        })
        .show();
  }


  private void checkCreditCardType(String type) {
    if (!TextUtils.isEmpty(type)) {
      if (type.equalsIgnoreCase(Constants.VISA_BRAND)) {
        imgCardBrand.setImageResource(R.drawable.ic_visa_blue_background);
        imgCardBrand.setVisibility(View.VISIBLE);
      } else if (type.equalsIgnoreCase(Constants.MASTER_BRAND)) {
        imgCardBrand.setImageResource(R.drawable.ic_master_card_with_text);
        imgCardBrand.setVisibility(View.VISIBLE);
      } else if (type.equalsIgnoreCase(Constants.AMERICAN_EXPRESS_BRAND)) {
        imgCardBrand.setImageResource(R.drawable.ic_american_express_blue_background);
        imgCardBrand.setVisibility(View.VISIBLE);

      } else if (type.equalsIgnoreCase(Constants.DISCOVER_BRAND)) {
        imgCardBrand.setVisibility(View.INVISIBLE);
      } else if (type.equalsIgnoreCase(Constants.DINERS_CLUB_BRAND)) {
        imgCardBrand.setVisibility(View.INVISIBLE);
      } else if (type.equalsIgnoreCase(Constants.JCB_BRAND)) {
        imgCardBrand.setVisibility(View.INVISIBLE);
      } else {
        inputCardNumber.showError(getString(R.string.invalid_card));
        imgCardBrand.setVisibility(View.INVISIBLE);
      }
    }
  }

  public void hideKeyboard() {
    if (contentView != null) {
      KBUtil.hideKeyboard(getActivity(), contentView);
    }
  }
}
