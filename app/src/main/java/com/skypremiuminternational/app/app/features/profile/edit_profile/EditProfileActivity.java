package com.skypremiuminternational.app.app.features.profile.edit_profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.cart.ShoppingCartActivity;
import com.skypremiuminternational.app.app.features.forgot_password.update.UpdatePasswordActivity;
import com.skypremiuminternational.app.app.features.membership_renewal.MembershipRenewalActivity;
import com.skypremiuminternational.app.app.features.skytextinputlayout.SkyTextInputLayout;
import com.skypremiuminternational.app.app.internal.mvp.BaseActivity;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ErrorMessageFactory;
import com.skypremiuminternational.app.app.utils.PasswordTransformationMethod;
import com.skypremiuminternational.app.app.utils.StringUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.utils.listener.MemberShipRenewalActionsListener;
import com.skypremiuminternational.app.data.network.URL;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.ean.ISOCountry;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.UserUtil;

import dagger.android.AndroidInjection;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class EditProfileActivity extends BaseActivity<EditProfilePresenter>
    implements EditProfileView<EditProfilePresenter> {

  @BindView(R.id.tvTitle_toolbar)
  TextView tvTitle_toolbar;
  @BindView(R.id.llMemberShip)
  LinearLayout llMemberShip;
  @BindView(R.id.tvExpire)
  TextView tvExpire;
  @BindView(R.id.tvExpireDate)
  TextView tvExpireDate;
  @BindView(R.id.tvMsg)
  TextView tvMsg;
  @BindView(R.id.tvStatus)
  TextView tvStatus;
  @BindView(R.id.stilEmail)
  SkyTextInputLayout stilEmail;
  @BindView(R.id.stilPassword)
  SkyTextInputLayout stilPassword;
  @BindView(R.id.stilDob)
  SkyTextInputLayout stilDob;
  @BindView(R.id.stilSalutation)
  SkyTextInputLayout stilSalutation;
  @BindView(R.id.stilFirstName)
  SkyTextInputLayout stilFirstName;
  @BindView(R.id.stilLastName)
  SkyTextInputLayout stilLastName;
  @BindView(R.id.stilMobileNumber)
  SkyTextInputLayout stilMobileNumber;
  @BindView(R.id.ivFemale)
  ImageView ivFemale;
  @BindView(R.id.ivMale)
  ImageView ivMale;
  @BindView(R.id.tvMale)
  TextView tvFemale;
  @BindView(R.id.tvFemale)
  TextView tvMale;
  @BindView(R.id.stilNationality)
  SkyTextInputLayout stilNationality;
  @BindView(R.id.stilCountry)
  SkyTextInputLayout stilCountry;
  @BindView(R.id.llPhoneCode)
  LinearLayout llPhoneCode;
  @BindView(R.id.tvPhoneCode)
  TextView tvPhoneCode;
  @BindView(R.id.tv_renew_membership)
  TextView txtRenewMembership;

  @Inject
  ErrorMessageFactory errorMessageFactory;
  private List<CountryCode> countryCodes = new ArrayList<>();
  private List<Nationality> nationalities = new ArrayList<>();
  private List<ISOCountry> isoCountries = new ArrayList<>();
  private PhoneCode phoneCodes;

  private CountryCode selectedCountryCode;
  private Nationality selectedNationality;
  private UserDetailResponse userDetailResponse;
  private int nationalityItem = 0, countryItem = 0, salutationItem = 0, phoneCodeItem = 0;
  private ProgressDialog progressDialog;
  private Double loyaltyPoint;

  static boolean success = false;

  /**
   * Allows to start this activity
   */
  public static void startMe(Context context) {
    Intent intent = new Intent(context, EditProfileActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    success = false;
    context.startActivity(intent);
  }

  public static void startMe(Context context,boolean paymentSuccess) {
    Intent intent = new Intent(context, EditProfileActivity.class);
    success = paymentSuccess;
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
    ButterKnife.bind(this);

  }

  @Override
  public void onStart() {
    super.onStart();

    stilPassword.setTransformationMethod(new PasswordTransformationMethod());

    tvTitle_toolbar.setText(getResources().getString(R.string.edit_profile));
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setMessage("Loading ...");

    presenter.getCountryCodes();
    stilDob.setOnTouchListener((v, event) -> {
      if (event.getAction() == MotionEvent.ACTION_UP) {
        showBirthdayDialog();
      }
      return false;
    });
  }

  @Override
  public void onPause() {
    super.onPause();
    if (progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Inject
  @Override
  public void injectPresenter(EditProfilePresenter presenter) {
    super.injectPresenter(presenter);
  }

  @Override
  public void render(EditProfileViewState viewState) {
    if (viewState.isLoading()) {
      progressDialog.show();
    } else {

      if (viewState.isSuccess()) {
        if (viewState.message().equals("1") && viewState.userDetail() != null) {
          userDetailResponse = viewState.userDetail();
          assert userDetailResponse != null;
          loyaltyPoint = userDetailResponse.getLoyaltyPoints();
          int groupId = userDetailResponse.getGroupId();
          // TODO: 30/3/18 3 months stuff
          if (groupId == Constants.GROUP_ID_EXPIRED
              || groupId == Constants.GROUP_ID_AWAITING_RENEWAL
              || (userDetailResponse.getDaysLeft() != UserUtil.DEFAULT_DAYS_LEFT
              && userDetailResponse.getDaysLeft() <= 90)) {
            txtRenewMembership.setVisibility(View.VISIBLE);
          } else {
            txtRenewMembership.setVisibility(View.GONE);
          }
          countryCodes = viewState.countryCodes();
          phoneCodes = viewState.phoneCodes();
          nationalities = viewState.nationalities();
          setUserDetails(userDetailResponse);
        } else if (viewState.message().equals("2")) {
          userDetailResponse = viewState.userDetail();
          setUserDetails(userDetailResponse);
          Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
          finish();
        } else if (viewState.message().equals("3")) {
          showCheckoutNotice(viewState.renewalPrice());
        }
      } else {
        Toast.makeText(this, errorMessageFactory.getErrorMessage(viewState.error()),
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void showCheckoutNotice(Double renewalPrice) {

    
    String urlcode = BuildConfig.BASE_URL + URL.CONFIGCOUPON;
    String urlsky = BuildConfig.BASE_URL + URL.CONFIGSKY;
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    StringRequest postRequest = new StringRequest(Request.Method.GET, urlcode, new Response.Listener<String>() {
      @Override
      public void onResponse(String a) {



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.GET, urlsky, new Response.Listener<String>() {
          @Override
          public void onResponse(String s) {
            MembershipRenewalDialogFragment fragment;
            if(s.contains("\"false\"") && a.contains("\"false\"")){
              presenter.addRenewalToCart(false);
            }else if(s.contains("\"false\"")){
              presenter.addRenewalToCart(false);
            }else{
              if (null != loyaltyPoint && loyaltyPoint >= renewalPrice) {
                fragment = MembershipRenewalDialogFragment.newInstance(
                        MembershipRenewalDialogFragment.DIALOG_TYPE_WITH_LOYALTY_POINTS, renewalPrice,
                        loyaltyPoint);
                fragment.setActionsListener(new MemberShipRenewalActionsListener() {
                  @Override
                  public void onClickedRenewLater(boolean showPromptAgain) {
                    // do nothing
                  }

                  @Override
                  public void onClickedRenewMemberShip(boolean showPromptAgain) {
                    //do nothing
                  }

                  @Override
                  public void onClickedRenew(boolean usePoints) {
                    presenter.addRenewalToCart(usePoints);
                  }
                });


                fragment.show(getSupportFragmentManager(), "membership_renewal");
              } else {
                presenter.addRenewalToCart(false);
              }


            }
          }


        },
                new Response.ErrorListener() {

                  @Override
                  public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Failed to load Redeem Sky Dollar config.",Toast.LENGTH_SHORT).show();
                  }
                }){

          //This is for Headers
          @Override
          public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json");
            params.put("Authorization", Constants.CLIENT_TOKEN);
            return params;
          }

        };


        requestQueue.add(postRequest);

      }
    },
            new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Failed to load Redeem Coupon code config.",Toast.LENGTH_SHORT).show();
              }
            }){

      //This is for Headers
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Authorization", Constants.CLIENT_TOKEN);
        return params;
      }

    };


    requestQueue.add(postRequest);



  }




  @Override
  public void goToShoppingCart() {
    progressDialog.dismiss();
    ShoppingCartActivity.start(this);
  }

 /* @Override
  public void render(String country,String id) {
    progressDialog.dismiss();
    this.setCountry(country,id);
  }
*/
  @OnClick(R.id.ivNavigation_toolbar)
  void onClickMenu() {
    finish();
  }

  @OnClick(R.id.tvChangePassword)
  void onClickChangePassword() {
    UpdatePasswordActivity.startMe(this);
  }

  @OnClick(R.id.vSalutation)
  void onClickSalutation() {
    showSalutationDialog();
  }

  @OnClick(R.id.vNationality)
  void onClickNationality() {
    showNationalitiesDialog();
  }

  @OnClick(R.id.vCountry)
  void onClickCountry() {
    showCountryCodesDialog();
  }

  @OnClick(R.id.llPhoneCode)
  void onClickPhoneCodes() {
    showPhoneCodesDialog();
  }

  @OnClick(R.id.tv_save)
  void onClickSumit() {
    if (!(isValid())) {
      createUpdateUserObject();
    }
  }

  @OnClick(R.id.tv_renew_membership)
  public void onClickRenewMembership() {

    //presenter.getRenewalProduct();
    MembershipRenewalActivity.startMe(this);

  }

  private void createUpdateUserObject() {
    CustomAttribute customAttribute_Nationality =
        new CustomAttribute("nationality",
            String.valueOf(selectedNationality.getId()));
    CustomAttribute customAttribute_CountryCode =
        new CustomAttribute("contact_country_code",
            tvPhoneCode.getText().toString().substring(1));
    CustomAttribute customAttribute_AddCountry =
        new CustomAttribute("address_country",
            String.valueOf(selectedCountryCode.getId()));
    CustomAttribute customAttribute_Salutation =
        new CustomAttribute("salutation",
            stilSalutation.getText().substring(0, stilSalutation.getText().length() - 1));
    CustomAttribute customAttribute_ContactNumber =
        new CustomAttribute("contact_number", stilMobileNumber.getText().trim());

    List<CustomAttribute> customAttributeList = new ArrayList<>();
    customAttributeList.add(customAttribute_Nationality);
    customAttributeList.add(customAttribute_CountryCode);
    customAttributeList.add(customAttribute_AddCountry);
    //customAttributeList.add(customAttribute_Language);
    customAttributeList.add(customAttribute_Salutation);
    //customAttributeList.add(customAttribute_Currency);
    customAttributeList.add(customAttribute_ContactNumber);

    String member_number = "";

    for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equals("language")) {
        customAttributeList.add(
            new CustomAttribute("language", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("currency")) {
        customAttributeList.add(
            new CustomAttribute("currency", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("member_number")) {
        member_number = customAttribute.getValue();
        customAttributeList.add(
            new CustomAttribute("member_number", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contract_status")) {
        customAttributeList.add(
            new CustomAttribute("contract_status", customAttribute.getValue()));
      }
    }
    UpdateUserRequest.Customer customer = new UpdateUserRequest.Customer(userDetailResponse.getId(),
        userDetailResponse.getWebsiteId(), stilFirstName.getText(), stilLastName.getText(),
        userDetailResponse.getEmail(), customAttributeList);
    presenter.updateUserDetail(new UpdateUserRequest(customer, member_number));
  }

  private void showSalutationDialog() {

    new AlertDialog.Builder(this).setTitle("Choose salutation")
        .setSingleChoiceItems(Constants.SALUTATIONS, salutationItem, (dialog, item) -> {
          stilSalutation.setText(Constants.SALUTATIONS[item]);
          salutationItem = item;
          dialog.dismiss();
        })
        .show();
  }

  private void showCountryCodesDialog() {

    String[] countries = new String[countryCodes.size()];
    for (int i = 0; i < countryCodes.size(); i++) {
      countries[i] = StringUtil.toTitleCase(countryCodes.get(i).getName().split(" "));
    }

    final String[] countryArr = countries;
    new AlertDialog.Builder(this).setTitle("Choose country")
        .setSingleChoiceItems(countryArr, countryItem, (dialog, item) -> {
          stilCountry.setText(countryArr[item]);
          selectedCountryCode =
              new CountryCode(countryCodes.get(item).getId(), countryCodes.get(item).getName());
          Timber.e(String.valueOf(selectedCountryCode));
          countryItem = item;
          dialog.dismiss();
        })
        .setPositiveButton("Cancel", null)
        .show();
  }

  private void showPhoneCodesDialog() {
    List<PhoneCode.PhoneCode_> phoneCode_s = new ArrayList<>();

    for (int j = 0; j < phoneCodes.getPhoneCodes().size(); j++) {
      phoneCode_s.add(phoneCodes.getPhoneCodes().get(j));
    }

    final String[] phones = new String[phoneCode_s.size()];
    String[] codes = new String[phoneCode_s.size()];

    for (int i = 0; i < phoneCode_s.size(); i++) {
      phones[i] = phoneCode_s.get(i).getCountryName();
      codes[i] = phoneCode_s.get(i).getDiallingCode();
    }

    final String[] phoneArr = phones;
    final String[] codeArr = codes;
    //.setTitle("Choose Country Codes")
    new AlertDialog.Builder(this).setTitle("Choose dial code")
        .setSingleChoiceItems(phoneArr, phoneCodeItem, (dialog, item) -> {
          tvPhoneCode.setText("+" + codeArr[item]);
          phoneCodeItem = item;
          dialog.dismiss();
        })
        .setPositiveButton("Cancel", null)
        .show();
  }

  private void showNationalitiesDialog() {
    String[] nationality = new String[nationalities.size()];

    for (int i = 0; i < nationalities.size(); i++) {
      nationality[i] = StringUtil.toTitleCase(nationalities.get(i).getName().split(" "));
    }

    final String[] nationalityArr = nationality;
    new AlertDialog.Builder(this).setTitle("Choose nationality")
        .setSingleChoiceItems(nationalityArr, nationalityItem, (dialog, item) -> {
          stilNationality.setText(nationalityArr[item]);
          selectedNationality =
              new Nationality(nationalities.get(item).getId(), nationalities.get(item).getName());
          Timber.e(String.valueOf(selectedNationality));
          nationalityItem = item;
          dialog.dismiss();
        })
        .setPositiveButton("Cancel", null)
        .show();
  }

  private void setUserDetails(UserDetailResponse response) {
    this.userDetailResponse = response;
    //tilEmail.getEditText().setText(response.getEmail());
    stilEmail.setText(response.getEmail());

    stilFirstName.setText(response.getFirstname());
    stilLastName.setText(response.getLastname());
    for (CustomAttribute customAttribute : response.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equals("salutation")) {
        for (int i = 0; i < Constants.SALUTATIONS.length; i++) {
          String salutation = Constants.SALUTATIONS[i];
          String value = customAttribute.getValue();
          if (salutation.equals(value + ".")) {
            salutationItem = i;
          }
        }
        stilSalutation.setText(customAttribute.getValue() + ".");
      } else if (customAttribute.getAttributeCode().equals("contact_number")) {
        stilMobileNumber.setText(customAttribute.getValue());
      } else if (customAttribute.getAttributeCode().equals("member_type")) {
        if (customAttribute.getValue().equalsIgnoreCase("CM")) {
          llMemberShip.setVisibility(View.GONE);
        }
      } else if (customAttribute.getAttributeCode().equals("member_status")) {
        if (customAttribute.getValue().equals("AC")) {
          tvExpire.setText("Your membership is ");
          tvStatus.setText("active");
          tvMsg.setText(" and will expire on ");
        } else {
          tvExpire.setText("Your membership has ");
          tvStatus.setText("expired");
          tvMsg.setText(" on ");
        }
      } else if (customAttribute.getAttributeCode().equals("member_expiry_date")) {
        if (customAttribute.getValue() != null) {
          try {
            java.util.Date convertedDate =
                Constants.DATE_YEAR_FORMAT.parse(customAttribute.getValue());
            tvExpireDate.setText(String.format(Constants.DATE_FORMAT.format(convertedDate)));
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
      }
    }
    if (response.getGender() != null) {
      if (response.getGender() == 1) {
        ivMale.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_accent));
        ivFemale.setImageDrawable(
            getResources().getDrawable(R.drawable.ic_radio_button_unchecked_accent));
      } else {
        ivMale.setImageDrawable(
            getResources().getDrawable(R.drawable.ic_radio_button_unchecked_accent));
        ivFemale.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_accent));
      }
    }
    if (response.getDob() != null) {
      //stilDob.setText(Constants.DATE_FORMAT.format(Date.valueOf(response.getDob())));
      //stilDob.setText(response.getDob());
      try {
        java.util.Date convertedDate = Constants.DATE_FORMAT_DOB.parse(response.getDob());
        stilDob.setText(String.format(Constants.DATE_FORMAT.format(convertedDate)));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    setUserCountry();
    setUserNationality();
    setUserPhoneCodes();
  }

  void showBirthdayDialog() {
    Calendar c = Calendar.getInstance();
    Calendar cMin = Calendar.getInstance();
    Calendar cMax = Calendar.getInstance();
    cMax.add(Calendar.YEAR, Constants.MIN_AGE);

    if (!TextUtils.isEmpty(stilDob.getText().toString())) {
      try {
        c.setTime(Constants.DATE_FORMAT.parse(stilDob.getText().toString()));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } else {
      c = cMax;
    }
    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
        (view, year, monthOfYear, dayOfMonth) -> stilDob.setText(Constants.DATE_FORMAT.format(
            new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime())), c.get(Calendar.YEAR),
        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    datePickerDialog.getDatePicker().setMaxDate(cMax.getTime().getTime());
    datePickerDialog.show();
  }

  private void clickMale() {
    ivMale.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_accent));
    ivFemale.setImageDrawable(
        getResources().getDrawable(R.drawable.ic_radio_button_unchecked_accent));
  }

  private void clickFemale() {
    ivFemale.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_accent));
    ivMale.setImageDrawable(
        getResources().getDrawable(R.drawable.ic_radio_button_unchecked_accent));
  }

  private void setCountry(String country,String id){
    stilCountry.setText(country);
    selectedCountryCode = new CountryCode(id, country);
  }

  private void setUserCountry() {

    String countryCodeId = null;
    if (userDetailResponse.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equals("address_country")) {
          countryCodeId = customAttribute.getValue();
          break;
        }
      }
    }
    //presenter.GetisoCountry(countryCodeId);
    for (int i = 0; i < countryCodes.size(); i++) {
      CountryCode countryCode = countryCodes.get(i);
      if (String.valueOf(countryCode.getId()).equals(countryCodeId)) {
        countryItem = i;
        stilCountry.setText(StringUtil.toTitleCase(countryCode.getName().split(" ")));
        Timber.e(countryCode.getId() + countryCode.getName());
        selectedCountryCode = new CountryCode(countryCode.getId(), countryCode.getName());
      }
    }
    progressDialog.hide();
  }

  private void setUserNationality() {
    String nationalityId = null;
    if (userDetailResponse.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equals("nationality")) {
          nationalityId = customAttribute.getValue();
          break;
        }
      }
    }
    for (int i = 0; i < nationalities.size(); i++) {
      Nationality nationality = nationalities.get(i);
      if (String.valueOf(nationality.getId()).equals(nationalityId)) {
        nationalityItem = i;
        stilNationality.setText(StringUtil.toTitleCase(nationality.getName().split(" ")));
        selectedNationality = new Nationality(nationality.getId(), nationality.getName());
      }
    }
  }

  private void setUserPhoneCodes() {
    String contactCountryCode = null;
    if (userDetailResponse.getCustomAttributes() != null) {
      for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equals("contact_country_code")) {
          contactCountryCode = customAttribute.getValue();
          break;
        }
      }
    }

    for (int i = 0; i < phoneCodes.getPhoneCodes().size(); i++) {
      PhoneCode.PhoneCode_ countryCode = phoneCodes.getPhoneCodes().get(i);
      if (String.valueOf(countryCode.getDiallingCode()).equals(contactCountryCode)) {
        phoneCodeItem = i;
        tvPhoneCode.setText("+" + countryCode.getDiallingCode());
      }
    }
  }

  private boolean isValid() {
    boolean IS_NOT_VALID = false;
    if (!Validator.isUsernameValid(stilFirstName.getText())) {
      stilFirstName.showError();
      IS_NOT_VALID = true;
    } else {
      stilFirstName.hideError();
    }
    if (!Validator.isUsernameValid(stilLastName.getText())) {
      stilLastName.showError();
      IS_NOT_VALID = true;
    } else {
      stilLastName.hideError();
    }
    if (!Validator.isPhoneValid(stilMobileNumber.getText())) {
      stilMobileNumber.showError();
      IS_NOT_VALID = true;
    } else {
      stilMobileNumber.hideError();
    }
    if (selectedCountryCode == null || selectedNationality == null) {
      Toast.makeText(this, "Choose Nationality or Country Code", Toast.LENGTH_SHORT).show();
      IS_NOT_VALID = true;
    }
    return IS_NOT_VALID;
  }
}
