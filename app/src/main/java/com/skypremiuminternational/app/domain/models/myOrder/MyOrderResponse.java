package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 10/7/17.
 */

public class MyOrderResponse implements Serializable {
  @SerializedName("items")
  @Expose
  private List<MyOrderItem> items = null;
  @SerializedName("search_criteria")
  @Expose
  private SearchCriteria searchCriteria;
  @SerializedName("total_count")
  @Expose
  private int totalCount;
  String id;
  String date;
  String price;
  int status;

  public MyOrderResponse(String id, String date, String price, int status) {
    this.id = id;
    this.date = date;
    this.price = price;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public List<MyOrderItem> getItems() {
    return items;
  }

  public void setItems(List<MyOrderItem> items) {
    this.items = items;
  }

  public SearchCriteria getSearchCriteria() {
    return searchCriteria;
  }

  public void setSearchCriteria(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
