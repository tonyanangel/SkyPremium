package com.skypremiuminternational.app.domain.models.wellness;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductListResponse implements Serializable {

  @SerializedName("total_count")
  private int totalCount;

  @SerializedName("items")
  private List<ItemsItem> items;

  @SerializedName("search_criteria")
  private SearchCriteria searchCriteria;

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setItems(List<ItemsItem> items) {
    this.items = items;
  }

  public List<ItemsItem> getItems() {
    return items;
  }

  public SearchCriteria getSearchCriteria() {
    return searchCriteria;
  }
  public void setSearchCriteria(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  @Override
  public String toString() {
    return "ProductListResponse{"
        + "total_count = '"
        + totalCount
        + '\''
        + ",items = '"
        + items
        + '\''
        + ",search_criteria = '"
        + searchCriteria
        + '\''
        + "}";
  }
}