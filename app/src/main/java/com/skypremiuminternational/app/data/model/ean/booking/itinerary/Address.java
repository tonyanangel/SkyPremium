package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;

public class Address {

  @SerializedName("line_1")
  private String line1;

  @SerializedName("country_code")
  private String countryCode;

  @SerializedName("city")
  private String city;

  @SerializedName("state_province_code")
  private String stateProvinceCode;

  @SerializedName("postal_code")
  private String postalCode;

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public String getLine1() {
    return line1;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  public void setStateProvinceCode(String stateProvinceCode) {
    this.stateProvinceCode = stateProvinceCode;
  }

  public String getStateProvinceCode() {
    return stateProvinceCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPostalCode() {
    return postalCode;
  }
}