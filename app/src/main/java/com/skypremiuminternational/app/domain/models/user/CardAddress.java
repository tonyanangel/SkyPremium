package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardAddress {

  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("country")
  @Expose
  private String country;
  @SerializedName("line1")
  @Expose
  private String line1;
  @SerializedName("line2")
  @Expose
  private String line2;
  @SerializedName("postal_code")
  @Expose
  private String postalCode;
  @SerializedName("state")
  @Expose
  private String state;


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getLine1() {
    return line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public String getLine2() {
    return line2;
  }

  public void setLine2(String line2) {
    this.line2 = line2;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
