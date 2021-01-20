package com.skypremiuminternational.app.domain.models.search;

import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 11/19/17.
 */

public class SearchProductResponse implements Serializable {

  @SerializedName("items")
  @Expose
  private List<ItemsItem> items = null;
  @SerializedName("search_criteria")
  @Expose
  private SearchCriteria searchCriteria;
  @SerializedName("total_count")
  @Expose
  private Integer totalCount;

  public SearchProductResponse(List<ItemsItem> items) {
    this.items = items;
  }

  public List<ItemsItem> getItems() {
    return items;
  }

  public void setItems(List<ItemsItem> items) {
    this.items = items;
  }

  public SearchCriteria getSearchCriteria() {
    return searchCriteria;
  }

  public void setSearchCriteria(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public static class SearchCriteria implements Serializable {

    @SerializedName("filter_groups")
    @Expose
    private List<FilterGroup> filterGroups = null;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;

    public List<FilterGroup> getFilterGroups() {
      return filterGroups;
    }

    public void setFilterGroups(List<FilterGroup> filterGroups) {
      this.filterGroups = filterGroups;
    }

    public Integer getPageSize() {
      return pageSize;
    }

    public void setPageSize(Integer pageSize) {
      this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
      return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
      this.currentPage = currentPage;
    }
  }

  public static class FilterGroup implements Serializable {

    @SerializedName("filters")
    @Expose
    private List<Filter> filters = null;

    public List<Filter> getFilters() {
      return filters;
    }

    public void setFilters(List<Filter> filters) {
      this.filters = filters;
    }
  }

  public static class Filter implements Serializable {

    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("condition_type")
    @Expose
    private String conditionType;

    public String getField() {
      return field;
    }

    public void setField(String field) {
      this.field = field;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getConditionType() {
      return conditionType;
    }

    public void setConditionType(String conditionType) {
      this.conditionType = conditionType;
    }
  }
}
