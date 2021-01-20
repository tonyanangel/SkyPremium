package com.skypremiuminternational.app.data.network.request;

public class SearchProductRequest {

  String keyword, field, direction, categoryId;

  public SearchProductRequest(String keyword, String field, String direction, String categoryId) {
    this.keyword = keyword;
    this.field = field;
    this.direction = direction;
    this.categoryId = categoryId;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }
}