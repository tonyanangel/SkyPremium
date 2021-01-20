package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class CancelPenaltiesItem {

  @SerializedName("nights")
  private String nights;

  private String percent;

  private String amount;

  @SerializedName("start")
  private String start;

  @SerializedName("end")
  private String end;

  @SerializedName("currency")
  private String currency;

  public void setNights(String nights) {
    this.nights = nights;
  }

  public String getNights() {
    return nights;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getStart() {
    return start;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getEnd() {
    return end;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }

  public String getPercent() {
    return percent;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }
}