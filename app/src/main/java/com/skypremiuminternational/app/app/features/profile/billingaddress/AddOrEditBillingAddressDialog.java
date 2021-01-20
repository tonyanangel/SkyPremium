package com.skypremiuminternational.app.app.features.profile.billingaddress;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.utils.listener.OnTextChangedListener;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.app.view.SkyTextInputWithoutSignLayout;
import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.data.model.billingaddress.RegionItem;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class AddOrEditBillingAddressDialog extends BottomSheetDialogFragment {

  public static final int ADD_ADDRESS = 0;
  public static final int EDIT_ADDRESS = 1;

  @BindView(R.id.input_salutation)
  SkyTextInputSignLayout inputSalutation;
  @BindView(R.id.input_company)
  SkyTextInputWithoutSignLayout inputCompany;
  @BindView(R.id.img_checkbox)
  ImageView imgCheck;
  @BindView(R.id.img_close)
  ImageView imgClose;
  @BindView(R.id.tv_save)
  TextView txtSave;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.sign_of_mandatory)
  TextView signOfMandatory;
  @BindView(R.id.input_first_name)
  SkyTextInputSignLayout inputFirstName;
  @BindView(R.id.input_last_name)
  SkyTextInputSignLayout inputLastName;
  @BindView(R.id.input_phone_number)
  SkyTextInputSignLayout inputPhoneNumber;
  @BindView(R.id.input_post_code)
  SkyTextInputSignLayout inputPostCode;
  @BindView(R.id.input_address)
  SkyTextInputSignLayout inputAddress;
  @BindView(R.id.input_unit_number)
  SkyTextInputSignLayout inputUnit;
  @BindView(R.id.input_city)
  SkyTextInputSignLayout inputCity;
  @BindView(R.id.input_state)
  SkyTextInputLayout inputState;
  @BindView(R.id.input_country)
  SkyTextInputSignLayout inputCountry;
  @BindView(R.id.img_garbage)
  ImageView imgGarbage;
  @BindView(R.id.layout_checkbox)
  ConstraintLayout layoutCheckbox;
  @BindView(R.id.txt_set_primary_address)
  TextView txtSetPrimaryAddres;
  @BindView(R.id.layout_disable_checked)
  FrameLayout layoutDisableChecked;

  private Unbinder unbinder;
  private boolean isChecked;
  private ActionListener actionListener;
  private BillingAddress address;
  private List<CountryCode> countryCodes;
  private int selectedCountryPosition = 0;
  private String selectedCountryId;
  private int dialogType;
  private int salutationPosition = -1;
  private String[] countries;
  private String blockCharacterSet = "<>'\\\"*";

  private InputFilter filter = new InputFilter() {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

      if (source != null && blockCharacterSet.contains(("" + source))) {
        return "";
      }
      return null;
    }
  };

  public AddOrEditBillingAddressDialog() {

  }

  public static AddOrEditBillingAddressDialog newInstance(BillingAddress address,
                                                          List<CountryCode> countryCodes,
                                                          int dialogType) {
    AddOrEditBillingAddressDialog fragment = new AddOrEditBillingAddressDialog();
    fragment.address = address;
    fragment.dialogType = dialogType;
    fragment.countryCodes = countryCodes;
    return fragment;
  }

  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    View contentView =
        View.inflate(getContext(), R.layout.dialog_add_or_edit_billing_address, null);
    unbinder = ButterKnife.bind(this, contentView);

    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
    int height = displayMetrics.heightPixels;
    int maxHeight = (int) (height * 0.88);
    dialog.setContentView(contentView);

    BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) contentView.getParent());
    mBehavior.setPeekHeight(maxHeight);

    ((View) contentView.getParent()).setBackgroundColor(
        getResources().getColor(R.color.transparent));

    if (dialogType == ADD_ADDRESS) {
      title.setText(getString(R.string.txt_add_billing_addresss));
      imgGarbage.setVisibility(View.GONE);
    } else {
      title.setText(getString(R.string.txt_edit_billing_addresss));
      imgGarbage.setVisibility(View.VISIBLE);
    }

    countries = new String[countryCodes.size()];
    for (int i = 0; i < countryCodes.size(); i++) {
      countries[i] = StringUtil.toTitleCase(countryCodes.get(i).getName().split(" "));
    }
    if (address != null) {
      renderAddress(address);
    }

    inputUnit.edt.setFilters(new InputFilter[]{filter});
    inputUnit.edt.setLongClickable(false);

/*    inputState.setOnEditorActionListener((tv, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        onClickCountry();
      }
      return true;
    });*/
  }

  private void renderAddress(BillingAddress address) {
    inputFirstName.setText(address.getFirstname());
    inputLastName.setText(address.getLastname());
    inputPhoneNumber.setText(address.getTelephone());
    inputPostCode.setText(address.getPostcode());
    inputCompany.setText(address.getCompany());
    if (Validator.isTextValid(address.getSalutation())) {
      salutationPosition = findSalutationPosition(address.getSalutation());
      String salutation = address.getSalutation();
      if (!salutation.endsWith(".")) {
        salutation += ".";
      }
      inputSalutation.setText(salutation);
    }
    if (Validator.isTextValid(address.getCountryId())) {
      String country = findCountry(address.getCountryId());
      inputCountry.setText(country);
    }

    StringBuilder streets = new StringBuilder();
    if (address.getStreet() != null) {
      for (String street : address.getStreet()) {
        streets.append(street);
        streets.append(",");
      }
    }
    if (streets.length() > 0) {
      String str = streets.substring(0, streets.lastIndexOf(","));
      inputAddress.setText(str);
    }

    if (Validator.isTextValid(address.getUnitNumber())) {
      inputUnit.setText(address.getUnitNumber());
    }
    if (address.getCity() != null) {
      if (!isEmpty(address.getCity())) {
        inputCity.setText(address.getCity());
      }
    }
    if (address.getRegion() != null && address.getRegion().size() > 0) {
      if (Validator.isTextValid(address.getRegion().get(0).getRegion())) {
        inputState.setText(address.getRegion().get(0).getRegion());
      }
    }
    if (address.getCountryId() != null) {
      if (!isEmpty(address.getCountryId())) {
        for (int i = 0; i < countryCodes.size(); i++) {
          CountryCode countryCode = countryCodes.get(i);
          if (address.getCountryId().equalsIgnoreCase(String.valueOf(countryCode.getId()))) {
            selectedCountryPosition = i;
            inputCountry.setText(countryCode.getName());
            selectedCountryId = String.valueOf(countryCode.getId());
          }
        }
      }
    }

    if (address.isDefaultBilling()) {
      imgCheck.setBackgroundResource(R.drawable.ic_checked);
      imgCheck.setEnabled(false);
      txtSetPrimaryAddres.setEnabled(false);

      // 20200720 - Wiki Toan Tran - Disable condition primary address
      //imgGarbage.setVisibility(View.INVISIBLE);
      layoutDisableChecked.setVisibility(View.VISIBLE);
    } else {
      layoutDisableChecked.setVisibility(View.GONE);
    }
  }

  private String findCountry(String countryId) {
    for (int i = 0; i < countryCodes.size(); i++) {
      CountryCode countryCode = countryCodes.get(i);
      if (countryId.equalsIgnoreCase(countryCode.getId())) {
        selectedCountryPosition = i;
        return countryCode.getName();
      }
    }
    return "";
  }

  private int findSalutationPosition(String salutation) {
    for (int i = 0; i < Constants.SALUTATIONS.length; i++) {
      String sal = Constants.SALUTATIONS[i];
      if (salutation.trim().equalsIgnoreCase(sal.substring(0, sal.length() - 1))) {
        return i;
      }
    }
    return 0;
  }

  @OnClick(R.id.img_garbage)
  void onClickedDelete() {
    if (address != null) {
      actionListener.onDeleteClicked(address);
    }
  }

  @OnClick({R.id.img_checkbox, R.id.txt_set_primary_address})
  void onClickedCheck() {
    if (!isChecked) {
      isChecked = true;
      imgCheck.setBackgroundResource(R.drawable.ic_checked);
    } else {
      isChecked = false;
      imgCheck.setBackgroundResource(R.drawable.ic_circle);
    }
  }

  @OnClick(R.id.tv_save)
  void onClickedSave() {
    if (actionListener != null) {
      if (validateAddress()) {
        actionListener.onSaveClicked(buildAddress());
      }
    }
  }

  @OnClick(R.id.img_close)
  void onClickedClose() {
    dismiss();
  }

  @OnClick(R.id.vCountry)
  void onClickCountry() {
    new AlertDialog.Builder(getContext()).setTitle("Choose country")
        .setSingleChoiceItems(countries, selectedCountryPosition, (dialog, item) -> {
          inputCountry.setText(countries[item]);
          selectedCountryId = String.valueOf(countryCodes.get(item).getId());
          selectedCountryPosition = item;
          CommonUtils.hideKeyboard(getActivity());
          dialog.dismiss();
        })
        .setPositiveButton(getString(R.string.btn_label_cancel), (dialog, item) -> {
          CommonUtils.hideKeyboard(getActivity());
        })
        .show();
  }

  private boolean validateAddress() {
    boolean isValid = true;
    if (isEmpty(inputSalutation.getText())) {
      inputSalutation.showError(getString(R.string.guest_detail_salutation_label));
      isValid = false;
    }
    if (isEmpty(inputFirstName.getText())) {
      inputFirstName.showError("First Name");
      isValid = false;
    } else {
      if (!inputFirstName.getText().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_first_name));
        isValid = false;
      }
    }
    if (isEmpty(inputLastName.getText())) {
      inputLastName.showError("Last Name");
      isValid = false;
    } else {
      if (!inputLastName.getText().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_last_name));
        isValid = false;
      }
    }
    if (isEmpty(inputPhoneNumber.getText())) {
      inputPhoneNumber.showError("mobile number");
      isValid = false;
    }
    if (isEmpty(inputAddress.getText())) {
      inputAddress.showError("street address");
      isValid = false;
    }


    //20200805 - WIKI - Toan Tran disable condition for special symbols
    /*if (isEmpty(inputUnit.getText()) || !CommonUtils.isUnitNumber(inputUnit.getText())) {
      inputUnit.showError("unit number");
      isValid = false;
    }*/
    if (isEmpty(inputUnit.getText())) {
      inputUnit.showError("unit number");
      isValid = false;
    }
    if (isEmpty(inputPostCode.getText())) {
      inputPostCode.showError("zip / postal code");
      isValid = false;
    }
    if (isEmpty(inputCity.getText())) {
      inputCity.showError("city");
      isValid = false;
    } else {
      if (!inputCity.getText().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_city));
        isValid = false;
      }
    }
    if (!isEmpty(inputState.getText()) && !inputState.getText().matches("[a-zA-Z ]+")) {
      createErrorDialog(getString(R.string.error_state));
      isValid = false;
    }
    if (isEmpty(inputCountry.getText())) {
      inputCountry.showError("country");
      isValid = false;
    }
    return isValid;
  }

  private void createErrorDialog(String message) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
        .show();
  }

  private BillingAddress buildAddress() {
    if (address == null) {
      address = new BillingAddress();
    }
    String salutation = inputSalutation.getText();
    if (Validator.isTextValid(salutation)) {
      salutation = salutation.substring(0, salutation.length() - 1);
    }

    if (!address.isDefaultBilling()) {
      address.setDefaultBilling(isChecked);
    }

    address.setCompany(inputCompany.getText());
    address.setSalutation(salutation);
    address.setFirstname(inputFirstName.getText());
    address.setLastname(inputLastName.getText());
    address.setTelephone(inputPhoneNumber.getText());
    address.setPostcode(inputPostCode.getText());
    address.setStreet(Arrays.asList(inputAddress.getText().split(",")));
    List<RegionItem> regions = address.getRegion();
    if (regions == null) {
      regions = new ArrayList<>();
      RegionItem regionItem = new RegionItem();
      regionItem.setRegion(inputState.getText());
      regionItem.setRegionCode("");
      regionItem.setRegionId("");
      regions.add(regionItem);
    } else {
      for (RegionItem regionItem : regions) {
        regionItem.setRegion(inputState.getText());
      }
    }
    address.setUnitNumber(inputUnit.getText());
    address.setRegion(regions);
    address.setCity(inputCity.getText());
    address.setCountryId(String.valueOf(selectedCountryId));
    return address;
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  public interface ActionListener {
    void onSaveClicked(BillingAddress address);

    void onDeleteClicked(BillingAddress address);
  }

  public boolean isDefault() {
    return isChecked;
  }

  @OnClick(R.id.v_salutation)
  void onSalutationClicked() {
    new AlertDialog.Builder(getContext()).setTitle("Choose salutation")
        .setSingleChoiceItems(Constants.SALUTATIONS, salutationPosition,
            (dialog, item) -> {
              salutationPosition = item;
              inputSalutation.setText(Constants.SALUTATIONS[item]);
              dialog.dismiss();
            })
        .show();
  }

  private void hideKeyboard() {
    View view = getActivity().getCurrentFocus();
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
}

