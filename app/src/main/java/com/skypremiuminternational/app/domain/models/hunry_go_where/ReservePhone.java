package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

public class ReservePhone {


  @SerializedName("label")
  String label;
  @SerializedName("value")
  String value;
  @SerializedName("countryCode")
  String countryCode;
  @SerializedName("isPrimary")
  boolean isPrimary;


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setPrimary(boolean primary) {
    isPrimary = primary;
  }
}
