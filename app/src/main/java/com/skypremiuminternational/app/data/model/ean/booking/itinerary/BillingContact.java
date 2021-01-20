package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;

public class BillingContact {

  @SerializedName("address")
  private Address address;

  @SerializedName("phone")
  private String phone;

  @SerializedName("given_name")
  private String givenName;

  @SerializedName("family_name")
  private String familyName;

  @SerializedName("email")
  private String email;

  public void setAddress(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhone() {
    return phone;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}