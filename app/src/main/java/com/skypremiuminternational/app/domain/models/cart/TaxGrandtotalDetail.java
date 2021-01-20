package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class TaxGrandtotalDetail {
  @SerializedName("amount")
  @Expose
  private String amount;
  @SerializedName("rates")
  @Expose
  private List<Rate> rates = null;
  @SerializedName("group_id")
  @Expose
  private int groupId;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public List<Rate> getRates() {
    return rates;
  }

  public void setRates(List<Rate> rates) {
    this.rates = rates;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }
}
