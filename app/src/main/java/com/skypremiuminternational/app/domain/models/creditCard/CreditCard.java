package com.skypremiuminternational.app.domain.models.creditCard;

/**
 * Created by sandi on 10/7/17.
 */

public class CreditCard {

  String card;
  String expiryDate;

  public CreditCard(String card, String expiryDate) {
    this.card = card;
    this.expiryDate = expiryDate;
  }

  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
}
