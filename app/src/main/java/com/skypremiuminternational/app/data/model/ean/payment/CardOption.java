package com.skypremiuminternational.app.data.model.ean.payment;

import com.google.gson.annotations.SerializedName;

public class CardOption {

  @SerializedName("name")
  private String name;

  @SerializedName("card_type")
  private String cardType;

  @SerializedName("processing_country")
  private String processingCountry;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCardType() {
    return cardType;
  }

  public String getProcessingCountry() {
    return processingCountry;
  }

  public void setProcessingCountry(String processingCountry) {
    this.processingCountry = processingCountry;
  }
}