package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

  @SerializedName("name")
  String name;
  @SerializedName("address")
  String address;
  @SerializedName("localAddress")
  String localAddress;
  @SerializedName("city")
  String city;
  @SerializedName("localCity")
  String localCity;
  @SerializedName("country")
  String country;
  @SerializedName("hoursOfOperation")
  String hoursOfOperation;
  @SerializedName("website")
  String website;
  @SerializedName("postalCode")
  String postalCode;
  @SerializedName("sky_dollar_earn")
  String skyDollarEarn;
  @SerializedName("loyalty_expired")
  String loyaltyExpired;


  public String getSkyDollarEarn() {
    return skyDollarEarn;
  }

  public void setSkyDollarEarn(String skyDollarEarn) {
    this.skyDollarEarn = skyDollarEarn;
  }

  public String getLoyaltyExpired() {
    return loyaltyExpired;
  }

  public void setLoyaltyExpired(String loyaltyExpired) {
    this.loyaltyExpired = loyaltyExpired;
  }

  public String getPostalCode() {
    return postalCode == null ? "" : postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getAddress() {
    return address == null ? "" : address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLocalAddress() {
    return localAddress == null ? "" : localAddress;
  }

  public void setLocalAddress(String localAddress) {
    this.localAddress = localAddress;
  }

  public String getCity() {
    return city == null ? ""  : city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getLocalCity() {
    return localCity == null ? "" : localCity;
  }

  public void setLocalCity(String localCity) {
    this.localCity = localCity;
  }

  public String getCountry() {
    return country == null ? "" : country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getHoursOfOperation() {
    return hoursOfOperation == null ? "" : hoursOfOperation;
  }

  public void setHoursOfOperation(String hoursOfOperation) {
    this.hoursOfOperation = hoursOfOperation;
  }

  public String getWebsite() {
    return website == null ? "" : website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }
}
