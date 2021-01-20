package com.skypremiuminternational.app.app.features.checkout.room.stepone;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.model.BookerInfo;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.app.view.SkyTextInputSignLayout;
import com.skypremiuminternational.app.app.view.SkyTextInputWithouLineLayout;

public class BookerInfoViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tv_phone_code_error)
  TextView tvPhoneCodeError;
  @BindView(R.id.salutation_overlay)
  View salutationOverlay;
  @BindView(R.id.tv_name)
  TextView tvName;
  @BindView(R.id.edt_salutation)
  SkyTextInputSignLayout edtSalutation;
  @BindView(R.id.edt_first_name)
  SkyTextInputSignLayout edtFirstName;
  @BindView(R.id.edt_last_name)
  SkyTextInputSignLayout edtLastName;
  @BindView(R.id.tv_phone_code)
  TextView tvPhoneCode;
  @BindView(R.id.edt_phone_number)
  SkyTextInputWithouLineLayout edtPhoneNumber;
  @BindView(R.id.cb_smoking)
  CheckBox cbSmoking;
  @BindView(R.id.edt_special_request)
  EditText edtSpecialQuest;

  public BookerInfoViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(BookerInfo bookerInfo) {
    tvName.setText(bookerInfo.roomName);
    String salutation = bookerInfo.salutation;
    if (!TextUtils.isEmpty(salutation) && !salutation.endsWith(".")) {
      salutation += ".";
    }
    edtSalutation.setText(salutation);
    edtFirstName.setText(bookerInfo.firstName);
    edtLastName.setText(bookerInfo.lastName);
    String phoneCode = "";
    if (Validator.isTextValid(bookerInfo.phoneCode)) {
      phoneCode = String.format("+%s", bookerInfo.phoneCode);
    }
    tvPhoneCode.setText(phoneCode);
    edtPhoneNumber.setText(bookerInfo.phoneNumber);
    cbSmoking.setChecked(bookerInfo.isSmoking);
    edtSpecialQuest.setText(bookerInfo.specialRequest);

    String salutationError = bookerInfo.salutationHasError ? itemView.getContext()
        .getString(R.string.error_invalid_salutation) : null;
    String firstNameError = bookerInfo.firstNameHasError ? itemView.getContext()
        .getString(R.string.error_invalid_first_name) : null;
    String lastNameError = bookerInfo.lastNameHasError ? itemView.getContext()
        .getString(R.string.error_invalid_last_name) : null;
    String phoneNumberError = bookerInfo.phoneNumberHasError ? itemView.getContext()
        .getString(R.string.error_invalid_phone_number) : null;

    if (bookerInfo.phoneCodeHasError) {
      tvPhoneCodeError.setVisibility(View.VISIBLE);
    } else {
      tvPhoneCodeError.setVisibility(View.INVISIBLE);
    }
    if (salutationError != null) {
      edtSalutation.showError(salutationError);
    }

    if (firstNameError != null) {
      edtFirstName.showError(firstNameError);
    }

    if (lastNameError != null) {
      edtLastName.showError(lastNameError);
    }
    if (phoneNumberError != null) {
      edtPhoneNumber.showError(phoneNumberError);
    }
  }
}
