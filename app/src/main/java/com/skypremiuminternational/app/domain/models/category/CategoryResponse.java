package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 11/12/17.
 */

public class CategoryResponse implements Serializable {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("parent_id")
  @Expose
  private Integer parentId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("is_active")
  @Expose
  private Boolean isActive;
  @SerializedName("position")
  @Expose
  private Integer position;
  @SerializedName("level")
  @Expose
  private Integer level;
  @SerializedName("product_count")
  @Expose
  private Integer productCount;
  @SerializedName("children_data")
  @Expose
  private List<ChildData> childrenData = null;
  @SerializedName("tree_catalog_filter")
  @Expose
  private List<ChildData> treeCatalogFilter = null;

  public CategoryResponse() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getProductCount() {
    return productCount;
  }

  public void setProductCount(Integer productCount) {
    this.productCount = productCount;
  }

  public List<ChildData> getChildrenData() {
    return childrenData;
  }

  public void setChildrenData(List<ChildData> childrenData) {
    this.childrenData = childrenData;
  }

  public List<ChildData> getTreeCatalogFilter() {
    return treeCatalogFilter;
  }

  public void setTreeCatalogFilter(List<ChildData> treeCatalogFilter) {
    this.treeCatalogFilter = treeCatalogFilter;
  }
}
