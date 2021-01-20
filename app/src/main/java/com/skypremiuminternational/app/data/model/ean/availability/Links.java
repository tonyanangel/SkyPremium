package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class Links {

  @SerializedName("payment_options")
  private Endpoint payment;

  @SerializedName("price_check")
  private Endpoint priceCheck;

  private Endpoint book;

  public void setPayment(Endpoint payment) {
    this.payment = payment;
  }

  public Endpoint getPayment() {
    return payment;
  }

  public Endpoint getPriceCheck() {
    return priceCheck;
  }

  public void setPriceCheck(
      Endpoint priceCheck) {
    this.priceCheck = priceCheck;
  }

  public Endpoint getBook() {
    return book;
  }

  public void setBook(Endpoint book) {
    this.book = book;
  }
}