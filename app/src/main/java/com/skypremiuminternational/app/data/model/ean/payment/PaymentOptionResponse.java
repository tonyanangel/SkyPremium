package com.skypremiuminternational.app.data.model.ean.payment;

import com.google.gson.annotations.SerializedName;

public class PaymentOptionResponse {

  @SerializedName("credit_card")
  private CreditCard creditCard;

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  public CreditCard getCreditCard() {
    return creditCard;
  }
}