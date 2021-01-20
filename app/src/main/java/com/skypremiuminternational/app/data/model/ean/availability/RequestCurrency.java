package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class RequestCurrency {

  @SerializedName("currency")
  private String currency;

  @SerializedName("value")
  private String value;

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}