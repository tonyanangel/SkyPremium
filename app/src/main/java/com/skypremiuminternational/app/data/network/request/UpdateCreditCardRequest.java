package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/28/18.
 */

public class UpdateCreditCardRequest implements Serializable {
  @SerializedName("creditCardItem")
  @Expose
  private CreditCardItem
      creditCardItem;

  public UpdateCreditCardRequest(CreditCardItem creditCardItem) {
    this.creditCardItem = creditCardItem;
  }

  public CreditCardItem getCreditCardItemToken() {
    return creditCardItem;
  }

  public void setCreditCardItemToken(
      CreditCardItem creditCardItemToken) {
    this.creditCardItem = creditCardItemToken;
  }

  public static class CreditCardItem implements Serializable {
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("exp_month")
    @Expose
    private int expMonth;
    @SerializedName("exp_year")
    @Expose
    private int expYear;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;
    @SerializedName("address_state")
    @Expose
    private String addressState;
    @SerializedName("address_zip")
    @Expose
    private String addressZip;
    @SerializedName("address_city")
    @Expose
    private String addressCity;
    @SerializedName("address_country")
    @Expose
    private String addressCountry;
    @SerializedName("buildId")
    @Expose
    private String buildId;
    @SerializedName("chanel")
    @Expose
    private String chanel;

    public CreditCardItem(String cardId, int expMonth, int expYear, String name,
                          String addressLine1, String addressLine2, String addressState,
                          String addressZip, String addressCity, String addressCountry,String buildId, String chanel) {
      this.cardId = cardId;
      this.expMonth = expMonth;
      this.expYear = expYear;
      this.name = name;
      this.addressLine1 = addressLine1;
      this.addressLine2 = addressLine2;
      this.addressState = addressState;
      this.addressZip = addressZip;
      this.addressCity = addressCity;
      this.addressCountry = addressCountry;
      this.buildId = buildId;
      this.chanel = chanel;
    }

    public String getCardId() {
      return cardId;
    }

    public void setCardId(String cardId) {
      this.cardId = cardId;
    }

    public int getExpMonth() {
      return expMonth;
    }

    public void setExpMonth(int expMonth) {
      this.expMonth = expMonth;
    }

    public int getExpYear() {
      return expYear;
    }

    public void setExpYear(int expYear) {
      this.expYear = expYear;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAddressLine1() {
      return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
      return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
    }

    public String getAddressState() {
      return addressState;
    }

    public void setAddressState(String addressState) {
      this.addressState = addressState;
    }

    public String getAddressZip() {
      return addressZip;
    }

    public void setAddressZip(String addressZip) {
      this.addressZip = addressZip;
    }

    public String getAddressCity() {
      return addressCity;
    }

    public void setAddressCity(String addressCity) {
      this.addressCity = addressCity;
    }

    public String getAddressCountry() {
      return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
      this.addressCountry = addressCountry;
    }
    public String getBuildId() {
      return buildId;
    }

    public void setBuildId(String buildId) {
      this.buildId = buildId;
    }

    public String getChanel() {
      return chanel;
    }

    public void setChanel(String chanel) {
      this.chanel = chanel;
    }
  }
}
