package com.skypremiuminternational.app.domain.models.details;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmw on 2/6/2018.
 */

public class RecommendItems implements Serializable {
  @SerializedName("items")
  private List<ItemsItem> items = new ArrayList<>();
  @SerializedName("search_criteria")
  private String searchCriteria;
  @SerializedName("total_count")
  private int totalCount;

  public RecommendItems(List<ItemsItem> items, String searchCriteria, int totalCount) {
    this.items = items;
    this.searchCriteria = searchCriteria;
    this.totalCount = totalCount;
  }

  public List<ItemsItem> getItems() {
    return items;
  }

  public void setItems(List<ItemsItem> items) {
    this.items = items;
  }

  public String getSearchCriteria() {
    return searchCriteria;
  }

  public void setSearchCriteria(String searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
