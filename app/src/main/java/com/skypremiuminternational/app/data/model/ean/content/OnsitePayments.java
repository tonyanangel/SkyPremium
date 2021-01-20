package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class OnsitePayments {

  @SerializedName("types")
  private Types types;

  @SerializedName("currency")
  private String currency;

  public void setTypes(Types types) {
    this.types = types;
  }

  public Types getTypes() {
    return types;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }
}