package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/27/18.
 */

public class AddCreditCardRequest implements Serializable {
  @SerializedName("creditCardItemToken")
  @Expose
  private CreditCardItemToken creditCardItemToken;

  public AddCreditCardRequest(CreditCardItemToken creditCardItemToken) {
    this.creditCardItemToken = creditCardItemToken;
  }

  public CreditCardItemToken getCreditCardItemToken() {
    return creditCardItemToken;
  }

  public void setCreditCardItemToken(
      CreditCardItemToken creditCardItemToken) {
    this.creditCardItemToken = creditCardItemToken;
  }

  public static class CreditCardItemToken implements Serializable {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("exp_month")
    @Expose
    private int expMonth;
    @SerializedName("exp_year")
    @Expose
    private int expYear;
    @SerializedName("cvc")
    @Expose
    private String cvc;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
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
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    public CreditCardItemToken(String number, int expMonth, int expYear, String cvc, String name,
                               String addressLine1, String addressState, String addressZip, String addressCity,
                               String addressCountry,String buildId, String chanel) {
      this.number = number;
      this.expMonth = expMonth;
      this.expYear = expYear;
      this.cvc = cvc;
      this.name = name;
      this.addressLine1 = addressLine1;
      this.addressState = addressState;
      this.addressZip = addressZip;
      this.addressCity = addressCity;
      this.addressCountry = addressCountry;
      this.buildId = buildId;
      this.chanel = chanel;
    }
    public CreditCardItemToken(String number, int expMonth, int expYear, String cvc, String name,
                               String addressLine1, String addressState, String addressZip, String addressCity,
                               String addressCountry,String buildId, String chanel,String phoneNumber) {
      this.number = number;
      this.expMonth = expMonth;
      this.expYear = expYear;
      this.cvc = cvc;
      this.name = name;
      this.addressLine1 = addressLine1;
      this.addressState = addressState;
      this.addressZip = addressZip;
      this.addressCity = addressCity;
      this.addressCountry = addressCountry;
      this.buildId = buildId;
      this.chanel = chanel;
      this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
      return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    public String getNumber() {
      return number;
    }

    public void setNumber(String number) {
      this.number = number;
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

    public String getCvc() {
      return cvc;
    }

    public void setCvc(String cvc) {
      this.cvc = cvc;
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