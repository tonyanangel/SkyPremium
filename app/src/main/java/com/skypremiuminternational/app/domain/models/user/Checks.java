package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checks {


  @SerializedName("address_line1_check")
  @Expose
  private String addressLine1Check;

  @SerializedName("address_postal_code_check")
  @Expose
  private String addressPostalCodeCheck;

  @SerializedName("cvc_check")
  @Expose
  private String cvcCheck;


  public String getAddressLine1Check() {
    return addressLine1Check;
  }

  public void setAddressLine1Check(String addressLine1Check) {
    this.addressLine1Check = addressLine1Check;
  }

  public String getAddressPostalCodeCheck() {
    return addressPostalCodeCheck;
  }

  public void setAddressPostalCodeCheck(String addressPostalCodeCheck) {
    this.addressPostalCodeCheck = addressPostalCodeCheck;
  }

  public String getCvcCheck() {
    return cvcCheck;
  }

  public void setCvcCheck(String cvcCheck) {
    this.cvcCheck = cvcCheck;
  }
}
