package com.skypremiuminternational.app.app.features.checkout.stepone;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
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
import com.skypremiuminternational.app.app.utils.CountryUtil;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.text.TextUtils.isEmpty;

/**
 * Created by aeindraaung on 2/5/18.
 */

public class AddOrEditAddressDialog extends BottomSheetDialogFragment {

  public static final String EXTRA_KEY_DIALOG_TYPE = "key";
  public static final String EXTRA_KEY_COUNTRY = "country";
  public static final int ADD_ADDRESS = 0;
  public static final int EDIT_ADDRESS = 1;

  @BindView(R.id.input_company)
  SkyTextInputLayout inputCompany;
  @BindView(R.id.input_salutation)
  SkyTextInputSignLayout inputSalutation;
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
  private Address address;
  private List<CountryCode> countryCodes;
  private int countryItem = 0;
  private int selectedCountryId;
  private String selectedCountryname;
  private int salutationPosition = -1;

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
  public AddOrEditAddressDialog() {

  }

  public static AddOrEditAddressDialog newInstance(Address address,List<CountryCode> countryCodes) {
    AddOrEditAddressDialog fragment = new AddOrEditAddressDialog();
    fragment.address = address;
    fragment.countryCodes = countryCodes;
    return fragment;
  }

  @SuppressLint("RestrictedApi")
  @Override
  public void setupDialog(Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    View contentView = View.inflate(getContext(), R.layout.dialog_add_or_edit_address, null);
    unbinder = ButterKnife.bind(this, contentView);

    Bundle bundle = getArguments();
    int dialogType = bundle.getInt(EXTRA_KEY_DIALOG_TYPE, ADD_ADDRESS);
    countryCodes = (List<CountryCode>) bundle.getSerializable(EXTRA_KEY_COUNTRY);

    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
    int height = displayMetrics.heightPixels;
    int maxHeight = (int) (height * 0.88);
    dialog.setContentView(contentView);

    BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) contentView.getParent());
    mBehavior.setPeekHeight(maxHeight);

    ((View) contentView.getParent()).setBackgroundColor(
        getResources().getColor(R.color.transparent));

    if (dialogType == ADD_ADDRESS) {
      title.setText(getString(R.string.txt_add_delivery_addresss));
      imgGarbage.setVisibility(View.GONE);
    } else {
      title.setText(getString(R.string.txt_edit_delivery_addresss));
      imgGarbage.setVisibility(View.VISIBLE);
    }
    if (address != null) {
      renderAddress(address);
    }

    inputUnit.edt.setFilters(new InputFilter[]{filter});
    inputUnit.edt.setLongClickable(false);
  }

  private void renderAddress(Address address) {
    if (!TextUtils.isEmpty(address.getCompany())) {
      inputCompany.setText(address.getCompany());
    }
    if (!TextUtils.isEmpty(address.getPrefix())) {
      salutationPosition = getSalutationPosition(address.getPrefix());
      inputSalutation.setText(address.getPrefix());
    }

    inputFirstName.setText(address.getFirstname());
    inputLastName.setText(address.getLastname());
    inputPhoneNumber.setText(address.getTelephone());
    inputPostCode.setText(address.getPostcode());
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
    if (address.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : address.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase("unit_number")) {
          inputUnit.setText("" + customAttribute.getValue());
        }
      }
    }
    if (address.getCity() != null) {
      if (!isEmpty(address.getCity())) {
        inputCity.setText(address.getCity());
      }
    }
    if (address.getRegion().getRegion() != null) {
      if (!isEmpty((CharSequence) address.getRegion().getRegion())) {
        inputState.setText((String) address.getRegion().getRegion());
      }
    }
    if (address.getCountryId() != null) {

      if (!isEmpty(address.getCountryId())) {
        if(countryCodes!=null) {
          inputCountry.setText(CountryUtil.parseCountryName(countryCodes, address.getCountryId()));
          selectedCountryId = Integer.parseInt(address.getCountryId());
        }
        /*for (int i = 0; i < countryCodes.size(); i++) {
          CountryCode countryCode = countryCodes.get(i);
          for(ISOCountry isoCountry : countryCodesiso) {
            if(isoCountry != null) {
              if (countryCode.getName().equalsIgnoreCase(isoCountry.country_name) && address.getCountryId().equalsIgnoreCase(String.valueOf(isoCountry.country_code))) {
                countryItem = i;
                inputCountry.setText(isoCountry.country_name);
                selectedCountryId = countryCode.getId();
                selectedCountryname = isoCountry.country_code;
              }
            }
          }
        }*/
      }

    }


    if (address.getDefaultShipping() != null && address.getDefaultBilling() != null) {
      if (address.getDefaultShipping()) {
        imgCheck.setBackgroundResource(R.drawable.ic_checked);
        imgCheck.setEnabled(false);
        txtSetPrimaryAddres.setEnabled(false);
        // 20200720 - Wiki Toan Tran - Disable condition primary address
        // imgGarbage.setVisibility(View.INVISIBLE);
        layoutDisableChecked.setVisibility(View.VISIBLE);
      } else {
        layoutDisableChecked.setVisibility(View.GONE);
      }
    }
  }

  private int getSalutationPosition(String prefix) {
    for (int i = 0; i < Constants.SALUTATIONS.length; i++) {
      if ((prefix + ".").equals(Constants.SALUTATIONS[i])) {
        return i;
      }
    }
    return -1;
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
    String[] countries = new String[countryCodes.size()];
    for (int i = 0; i < countryCodes.size(); i++) {
      countries[i] = StringUtil.toTitleCase(countryCodes.get(i).getName().split(" "));
    }

    final String[] countryArr = countries;
    new AlertDialog.Builder(getContext()).setTitle("Choose country")
        .setSingleChoiceItems(countryArr, countryItem, (dialog, item) -> {
          inputCountry.setText(countryArr[item]);
          selectedCountryId = Integer.parseInt(countryCodes.get(item).getId());
          countryItem = item;
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
    if (salutationPosition == -1) {
      inputSalutation.showError(getString(R.string.guest_detail_salutation_label));
      isValid = false;
    }

    if (isEmpty(inputFirstName.getText())) {
      inputFirstName.showError("First Name");
      isValid = false;
    } else {
      if (!inputFirstName.getText().toString().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_first_name));
        isValid = false;
      }
    }
    if (isEmpty(inputLastName.getText())) {
      inputLastName.showError("Last Name");
      isValid = false;
    } else {
      if (!inputLastName.getText().toString().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_last_name));
        isValid = false;
      }
    }
    if (isEmpty(inputPhoneNumber.getText())) {
      inputPhoneNumber.showError("contact number");
      isValid = false;
    }
    if (isEmpty(inputAddress.getText())) {
      inputAddress.showError("street address");
      isValid = false;
    }

    //20200805 - WIKI - Toan Tran disable condition for special symbols
    /*
    if (isEmpty(inputUnit.getText()) || !CommonUtils.isUnitNumber(inputUnit.getText())) {
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
      if (!inputCity.getText().toString().matches("[a-zA-Z ]+")) {
        createErrorDialog(getString(R.string.error_city));
        isValid = false;
      }
    }
    if (!isEmpty(inputState.getText()) && !inputState.getText().toString().matches("[a-zA-Z ]+")) {
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



  private Address buildAddress() {
    if (address != null) {
      address.setPrefix(Constants.SALUTATIONS[salutationPosition].substring(0,
          Constants.SALUTATIONS[salutationPosition].length() - 1));
      address.setCompany(inputCompany.getText());
      address.setFirstname(inputFirstName.getText());
      address.setLastname(inputLastName.getText());
      address.setTelephone(inputPhoneNumber.getText());
      address.setPostcode(inputPostCode.getText());
      address.setStreet(Arrays.asList(inputAddress.getText().split(",")));
      Region region = new Region();
      region.setRegion(inputState.getText());
      address.setRegion(region);
      address.setCustomAttributes(buildAttribute());
      address.setCity(inputCity.getText());
      address.setCountryId(String.valueOf(selectedCountryId));
    }
    return address;
  }

  private List<CustomAttribute> buildAttribute() {
    List<CustomAttribute> attributes = new ArrayList<>();
    CustomAttribute unitNumber = new CustomAttribute();
    unitNumber.setAttributeCode("unit_number");
    unitNumber.setValue(inputUnit.getText());
    attributes.add(unitNumber);

    if (address.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : address.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equalsIgnoreCase("address_order_crm")) {
          attributes.add(customAttribute);
        }
      }
    }

    return attributes;
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

  @OnClick(R.id.vSalutation)
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

  public interface ActionListener {
    void onSaveClicked(Address address);

    void onDeleteClicked(Address address);
  }

  public boolean isDefault() {
    return isChecked;
  }


}
