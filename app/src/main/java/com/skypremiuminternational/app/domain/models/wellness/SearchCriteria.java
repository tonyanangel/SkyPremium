package com.skypremiuminternational.app.domain.models.wellness;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchCriteria implements Serializable {

  @SerializedName("filter_groups")
  private List<FilterGroupsItem> filterGroups;

  @SerializedName("current_page")
  private int currentPage;

  @SerializedName("page_size")
  private int pageSize;

  public void setFilterGroups(List<FilterGroupsItem> filterGroups) {
    this.filterGroups = filterGroups;
  }

  public List<FilterGroupsItem> getFilterGroups() {
    return filterGroups;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageSize() {
    return pageSize;
  }

  @Override
  public String toString() {
    return "SearchCriteria{"
        + "filter_groups = '"
        + filterGroups
        + '\''
        + ",current_page = '"
        + currentPage
        + '\''
        + ",page_size = '"
        + pageSize
        + '\''
        + "}";
  }
}