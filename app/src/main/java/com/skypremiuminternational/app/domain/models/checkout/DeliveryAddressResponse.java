package com.skypremiuminternational.app.domain.models.checkout;

/**
 * Created by aeindraaung on 1/29/18.
 */

public class DeliveryAddressResponse {
  private int id;
  private String name;
  private String address;
  private boolean isChecked;

  public DeliveryAddressResponse(int id, String name, String address, boolean isChecked) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.isChecked = isChecked;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean checked) {
    isChecked = checked;
  }
}
