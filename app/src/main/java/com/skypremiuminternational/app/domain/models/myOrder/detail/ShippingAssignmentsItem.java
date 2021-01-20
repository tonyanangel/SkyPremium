package com.skypremiuminternational.app.domain.models.myOrder.detail;

import com.skypremiuminternational.app.domain.models.myOrder.Shipping;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShippingAssignmentsItem {

  @SerializedName("shipping")
  private Shipping shipping;

  @SerializedName("items")
  private List<ItemsItem> items;

  public void setShipping(Shipping shipping) {
    this.shipping = shipping;
  }

  public Shipping getShipping() {
    return shipping;
  }

  public void setItems(List<ItemsItem> items) {
    this.items = items;
  }

  public List<ItemsItem> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return
        "ShippingAssignmentsItem{" +
            "shipping = '" + shipping + '\'' +
            ",items = '" + items + '\'' +
            "}";
  }
}