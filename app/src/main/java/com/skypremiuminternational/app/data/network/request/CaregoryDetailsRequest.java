package com.skypremiuminternational.app.data.network.request;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class CaregoryDetailsRequest {

  String categoryId;

  public CaregoryDetailsRequest(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }
}
