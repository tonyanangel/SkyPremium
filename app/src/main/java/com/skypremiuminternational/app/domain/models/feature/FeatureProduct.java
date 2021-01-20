package com.skypremiuminternational.app.domain.models.feature;

import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 11/17/17.
 */

public class FeatureProduct implements Serializable {

  @SerializedName("items")
  @Expose
  private List<ItemsItem> items = null;
  @SerializedName("search_criteria")
  @Expose
  private Object searchCriteria;
  @SerializedName("total_count")
  @Expose
  private Integer totalCount;

  public List<ItemsItem> getFeatureItems() {
    return items;
  }

  public void setFeatureItems(List<ItemsItem> items) {
    this.items = items;
  }

  public Object getSearchCriteria() {
    return searchCriteria;
  }

  public void setSearchCriteria(Object searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }
}
