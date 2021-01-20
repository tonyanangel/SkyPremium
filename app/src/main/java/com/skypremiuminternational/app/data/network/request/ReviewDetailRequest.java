package com.skypremiuminternational.app.data.network.request;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewDetailRequest {

  String customerId;
  String productId;
  String orderId;


  public ReviewDetailRequest() {
  }

  /**
   *
   * @param customerId
   * @param productId
   * @param orderId
   */
  public ReviewDetailRequest(String customerId, String productId, String orderId) {
    this.customerId = customerId;
    this.productId = productId;
    this.orderId = orderId;
  }


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Map<String,String> toMap (){
    Map<String,String> map = new HashMap<>();

    map.put("customer_id",getCustomerId());
    map.put("product_id",getProductId());
    map.put("order_id",getOrderId());

    return map;
  }
}
