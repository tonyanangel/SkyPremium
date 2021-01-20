package com.skypremiuminternational.app.domain.models.myOrder.billingaddress;

import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes {

  @SerializedName("unit_number")
  private String unitNumber;

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  public String getUnitNumber() {
    return unitNumber;
  }
}