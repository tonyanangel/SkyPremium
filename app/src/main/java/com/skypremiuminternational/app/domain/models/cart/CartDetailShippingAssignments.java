package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailShippingAssignments implements Serializable {
  @SerializedName("shipping")
  @Expose
  public CartDetailShipping shipping;
  @SerializedName("items")
  @Expose
  public List<CartDetailItem> items = null;

  public CartDetailShipping getShipping() {
    return shipping;
  }

  public void setShipping(CartDetailShipping shipping) {
    this.shipping = shipping;
  }

  public List<CartDetailItem> getItems() {
    return items;
  }

  public void setItems(List<CartDetailItem> items) {
    this.items = items;
  }
}
