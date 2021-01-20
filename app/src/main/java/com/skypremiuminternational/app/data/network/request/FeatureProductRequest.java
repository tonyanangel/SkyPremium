package com.skypremiuminternational.app.data.network.request;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class FeatureProductRequest {

  private boolean saveResult;
  String categoryId;

  public FeatureProductRequest(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public boolean isSaveResult() {
    return saveResult;
  }

  public void setSaveResult(boolean saveResult) {
    this.saveResult = saveResult;
  }
}
