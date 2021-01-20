package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class ShippingAssignment implements Serializable {
  @SerializedName("shipping")
  @Expose
  private Shipping shipping;
  @SerializedName("items")
  @Expose
  private List<ShippingItem> items = null;

  public Shipping getShipping() {
    return shipping;
  }

  public void setShipping(Shipping shipping) {
    this.shipping = shipping;
  }

  public List<ShippingItem> getItems() {
    return items;
  }

  public void setItems(List<ShippingItem> items) {
    this.items = items;
  }
}
