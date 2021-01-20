package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class Totals {

  @SerializedName("inclusive")
  private Inclusive inclusive;

  @SerializedName("exclusive")
  private Exclusive exclusive;

  @SerializedName("marketing_fee")
  private MarketingFee marketingFee;

  public void setInclusive(Inclusive inclusive) {
    this.inclusive = inclusive;
  }

  public Inclusive getInclusive() {
    return inclusive;
  }

  public void setExclusive(Exclusive exclusive) {
    this.exclusive = exclusive;
  }

  public Exclusive getExclusive() {
    return exclusive;
  }

  public MarketingFee getMarketingFee() {
    return marketingFee;
  }

  public void setMarketingFee(MarketingFee marketingFee) {
    this.marketingFee = marketingFee;
  }
}