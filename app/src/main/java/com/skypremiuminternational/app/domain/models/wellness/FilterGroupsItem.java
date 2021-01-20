package com.skypremiuminternational.app.domain.models.wellness;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FilterGroupsItem implements Serializable {

  @SerializedName("filters")
  private List<FiltersItem> filters;

  public void setFilters(List<FiltersItem> filters) {
    this.filters = filters;
  }

  public List<FiltersItem> getFilters() {
    return filters;
  }


}