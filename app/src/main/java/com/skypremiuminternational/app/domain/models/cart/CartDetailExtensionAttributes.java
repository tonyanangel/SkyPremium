package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailExtensionAttributes {
  @SerializedName("shipping_assignments")
  @Expose
  private List<CartDetailShippingAssignments> shippingAssignments = null;

  public List<CartDetailShippingAssignments> getShippingAssignments() {
    return shippingAssignments;
  }

  public void setShippingAssignments(List<CartDetailShippingAssignments> shippingAssignments) {
    this.shippingAssignments = shippingAssignments;
  }
}
