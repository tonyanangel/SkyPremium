package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailShipping implements Serializable {
  @SerializedName("billing_address")
  @Expose
  public CartDetailAddress address;
  @SerializedName("method")
  @Expose
  public Object method;

  public CartDetailAddress getAddress() {
    return address;
  }

  public void setAddress(CartDetailAddress address) {
    this.address = address;
  }

  public Object getMethod() {
    return method;
  }

  public void setMethod(Object method) {
    this.method = method;
  }
}
