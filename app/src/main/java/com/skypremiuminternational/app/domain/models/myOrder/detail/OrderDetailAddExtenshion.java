package com.skypremiuminternational.app.domain.models.myOrder.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 3/13/18.
 */

public class OrderDetailAddExtenshion implements Serializable {
  @SerializedName("unit_number")
  @Expose
  private String unitNumber;

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }
}
