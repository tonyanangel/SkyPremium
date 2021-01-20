package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 11/12/17.
 */

public class CategoryDetailsResponse implements Serializable {

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
  @SerializedName("children")
  @Expose
  private String children;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("path")
  @Expose
  private String path;
  @SerializedName("available_sort_by")
  @Expose
  private List<Object> availableSortBy = null;
  @SerializedName("include_in_menu")
  @Expose
  private Boolean includeInMenu;
  @SerializedName("custom_attributes")
  @Expose
  private List<CustomAttribute> customAttributes = null;

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

  public String getChildren() {
    return children;
  }

  public void setChildren(String children) {
    this.children = children;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List<Object> getAvailableSortBy() {
    return availableSortBy;
  }

  public void setAvailableSortBy(List<Object> availableSortBy) {
    this.availableSortBy = availableSortBy;
  }

  public Boolean getIncludeInMenu() {
    return includeInMenu;
  }

  public void setIncludeInMenu(Boolean includeInMenu) {
    this.includeInMenu = includeInMenu;
  }

  public List<CustomAttribute> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(List<CustomAttribute> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public static class CustomAttribute implements Serializable {

    @SerializedName("attribute_code")
    @Expose
    private String attributeCode;
    @SerializedName("value")
    @Expose
    private String value;

    public String getAttributeCode() {
      return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
      this.attributeCode = attributeCode;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}
