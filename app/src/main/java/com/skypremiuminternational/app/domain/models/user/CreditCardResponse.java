package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/26/18.
 */

public class CreditCardResponse implements Serializable {
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("object")
  @Expose
  private String object;
  @SerializedName("address_city")
  @Expose
  private String addressCity;
  @SerializedName("address_country")
  @Expose
  private String addressCountry;
  @SerializedName("address_line1")
  @Expose
  private String addressLine1;
  @SerializedName("address_line1_check")
  @Expose
  private String addressLine1Check;
  @SerializedName("address_line2")
  @Expose
  private String addressLine2;
  @SerializedName("address_state")
  @Expose
  private String addressState;
  @SerializedName("address_zip")
  @Expose
  private String addressZip;
  @SerializedName("address_zip_check")
  @Expose
  private String addressZipCheck;
  @SerializedName("brand")
  @Expose
  private String brand;
  @SerializedName("country")
  @Expose
  private String country;
  @SerializedName("customer")
  @Expose
  private String customer;
  @SerializedName("cvc_check")
  @Expose
  private Object cvcCheck;
  @SerializedName("dynamic_last4")
  @Expose
  private Object dynamicLast4;
  @SerializedName("exp_month")
  @Expose
  private int expMonth;
  @SerializedName("exp_year")
  @Expose
  private int expYear;
  @SerializedName("fingerprint")
  @Expose
  private String fingerprint;
  @SerializedName("funding")
  @Expose
  private String funding;
  @SerializedName("last4")
  @Expose
  private String last4;
  @SerializedName("metadata")
  @Expose
  private List<Object> metadata = null;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("tokenization_method")
  @Expose
  private Object tokenizationMethod;
  @SerializedName("cart_default")
  @Expose
  private Boolean cartDefault;
  @SerializedName("phone_number")
  @Expose
  private String phoneNumber;

  private boolean setAsDefault;

  private String cardNumber;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getObject() {
    return object;
  }

  public void setObject(String object) {
    this.object = object;
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

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine1Check() {
    return addressLine1Check;
  }

  public void setAddressLine1Check(String addressLine1Check) {
    this.addressLine1Check = addressLine1Check;
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

  public String getAddressZipCheck() {
    return addressZipCheck;
  }

  public void setAddressZipCheck(String addressZipCheck) {
    this.addressZipCheck = addressZipCheck;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public Object getCvcCheck() {
    return cvcCheck;
  }

  public void setCvcCheck(Object cvcCheck) {
    this.cvcCheck = cvcCheck;
  }

  public Object getDynamicLast4() {
    return dynamicLast4;
  }

  public void setDynamicLast4(Object dynamicLast4) {
    this.dynamicLast4 = dynamicLast4;
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

  public String getFingerprint() {
    return fingerprint;
  }

  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  public String getFunding() {
    return funding;
  }

  public void setFunding(String funding) {
    this.funding = funding;
  }

  public String getLast4() {
    return last4;
  }

  public void setLast4(String last4) {
    this.last4 = last4;
  }

  public List<Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<Object> metadata) {
    this.metadata = metadata;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getTokenizationMethod() {
    return tokenizationMethod;
  }

  public void setTokenizationMethod(Object tokenizationMethod) {
    this.tokenizationMethod = tokenizationMethod;
  }

  public Boolean getCartDefault() {
    return cartDefault;
  }

  public void setCartDefault(Boolean cartDefault) {
    this.cartDefault = cartDefault;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public static CreditCardResponse skeletonCreditCard(String city, String name, String street, String postCode, String countryCode) {
    CreditCardResponse creditCardResponse = new CreditCardResponse();
    creditCardResponse.setAddressCity(city);
    creditCardResponse.setName(name);
    creditCardResponse.setAddressLine1(street);
    creditCardResponse.setAddressZip(postCode);
    creditCardResponse.setAddressCountry(countryCode);
    return creditCardResponse;
  }

  public boolean isSetAsDefault() {
    return setAsDefault;
  }

  public void setSetAsDefault(boolean setAsDefault) {
    this.setAsDefault = setAsDefault;
  }

  /*20201707 - WIKI Viet Nguyen - fix bug set default visa*/
  private Boolean isVisaSelected = false;

  public Boolean getVisaSelected() {
    return isVisaSelected;
  }

  public void setVisaSelected(Boolean visaSelected) {
    isVisaSelected = visaSelected;
  }
}
