package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 3/9/18.
 */

public class ExtraFee implements Serializable {
  @SerializedName("order_id")
  @Expose
  private Integer orderId;
  @SerializedName("shipping_fee")
  @Expose
  private String shippingFee;
  @SerializedName("delivery_fee")
  @Expose
  private String deliveryFee;
  @SerializedName("total_loyalty_value_redeemed")
  @Expose
  private String totalLoyaltyValueRedeemed;
  @SerializedName("total_loyalty_renew_product_redeemed")
  @Expose
  private String totalLoyaltyRenewProductRedeemed;

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public String getShippingFee() {
    return shippingFee;
  }

  public void setShippingFee(String shippingFee) {
    this.shippingFee = shippingFee;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public String getTotalLoyaltyValueRedeemed() {
    return totalLoyaltyValueRedeemed;
  }

  public void setTotalLoyaltyValueRedeemed(String totalLoyaltyValueRedeemed) {
    this.totalLoyaltyValueRedeemed = totalLoyaltyValueRedeemed;
  }

  public String getTotalLoyaltyRenewProductRedeemed() {
    return totalLoyaltyRenewProductRedeemed;
  }

  public void setTotalLoyaltyRenewProductRedeemed(String totalLoyaltyRenewProductRedeemed) {
    this.totalLoyaltyRenewProductRedeemed = totalLoyaltyRenewProductRedeemed;
  }
}
