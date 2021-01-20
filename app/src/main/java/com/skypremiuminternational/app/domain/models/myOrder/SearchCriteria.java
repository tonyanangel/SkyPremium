package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class SearchCriteria implements Serializable {
  @SerializedName("filter_groups")
  @Expose
  private List<FilterGroup> filterGroups = null;

  public List<FilterGroup> getFilterGroups() {
    return filterGroups;
  }

  public void setFilterGroups(List<FilterGroup> filterGroups) {
    this.filterGroups = filterGroups;
  }
}
