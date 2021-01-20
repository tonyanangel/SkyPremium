package com.skypremiuminternational.app.data.network.request;

import java.util.HashMap;
import java.util.Map;

public class ProductReviewRequest {

  String productId;
  String currentCustomer;
  String currentPage;
  String pageSize;
  String sortDate;

  public String getSortDate() {
    return sortDate;
  }

  public void setSortDate(String sortDate) {
    this.sortDate = sortDate;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getCurrentCustomer() {
    return currentCustomer;
  }

  public void setCurrentCustomer(String currentCustomer) {
    this.currentCustomer = currentCustomer;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(String currentPage) {
    this.currentPage = currentPage;
  }

  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  public Map<String, String> toMap() {
    Map<String,String> map = new HashMap<>();

    map.put("product_id",getProductId());
    map.put("current_customer",getCurrentCustomer());
    map.put("current_page",getCurrentPage());
    map.put("page_size",getPageSize());
    map.put("sortDate",getSortDate());

    return map;
  }
}
