package com.skypremiuminternational.app.data.model.billingaddress;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillingAddressesResponse {

  @SerializedName("total_count")
  private int totalCount;

  @SerializedName("items")
  private List<BillingAddress> billingAddresses;

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setBillingAddresses(List<BillingAddress> billingAddresses) {
    this.billingAddresses = billingAddresses;
  }

  public List<BillingAddress> getBillingAddresses() {
    return billingAddresses;
  }
}