package com.skypremiuminternational.app.data.network.request;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class ProductListRequest {

  String categoryId;
  String field;
  String direction;
  String isThetime;
  private boolean saveResult;

  public ProductListRequest(String categoryId, String field, String direction,String isThetime) {
    this.categoryId = categoryId;
    this.field = field;
    this.direction = direction;
    this.isThetime = isThetime;
  }

  public String getIsThetime() {
    return isThetime;
  }

  public void setIsThetime(String isThetime) {
    this.isThetime = isThetime;
  }
  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public void setSaveResult(boolean saveResult) {
    this.saveResult = saveResult;
  }

  public boolean isSaveResult() {
    return saveResult;
  }
}
