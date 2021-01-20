package com.skypremiuminternational.app.data.network.request;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class DetailsRequest {

  String sku;
  String category_id;

  public DetailsRequest(String sku,String category_id) {
    this.sku = sku;
    this.category_id = category_id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }
  public String getCategory_id() {
    return category_id;
  }

  public void setCategory_id(String category_id) {
    this.category_id = category_id;
  }
}
