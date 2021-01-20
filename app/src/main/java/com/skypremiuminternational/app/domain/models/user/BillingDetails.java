package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingDetails {


  @SerializedName("address")
  @Expose
  CardAddress address;
  @SerializedName("email")
  @Expose
  String email;
  @SerializedName("name")
  @Expose
  String name;
  @SerializedName("phone")
  @Expose
  String phone;

  public CardAddress getAddress() {
    return address;
  }

  public void setAddress(CardAddress address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
