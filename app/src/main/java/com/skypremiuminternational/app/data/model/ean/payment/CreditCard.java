package com.skypremiuminternational.app.data.model.ean.payment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditCard {

  @SerializedName("name")
  private String name;

  @SerializedName("card_options")
  private List<CardOption> cardOptions;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setCardOptions(List<CardOption> cardOptions) {
    this.cardOptions = cardOptions;
  }

  public List<CardOption> getCardOptions() {
    return cardOptions;
  }
}