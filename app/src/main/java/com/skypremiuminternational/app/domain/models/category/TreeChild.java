package com.skypremiuminternational.app.domain.models.category;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class TreeChild implements Serializable {
  
  String filterLabel="";
  String filterType="";
  String tree="";
  String attributeId="";
  String minimumPrice="";
  String maximumPrice="";
  String name="";
  String options="";
  String label="";
  String productCount="";
  String categoryId="";
  String parentId="";
  String attributeCode="";
  String isActive="";
  String position="";
  String level="";
  String childrenData="";
  
  public List<TreeChild_> getChild() {
    return child;
  }
  
  public void setChild(List<TreeChild_> child) {
    this.child = child;
  }
  
  List<TreeChild_> child;
  
  public String getFilterLabel() {
    return filterLabel;
  }
  
  public void setFilterLabel(String filterLabel) {
    this.filterLabel = filterLabel;
  }
  
  public String getFilterType() {
    return filterType;
  }
  
  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }
  
  public String getTree() {
    return tree;
  }
  
  public void setTree(String tree) {
    this.tree = tree;
  }
  
  public String getAttributeId() {
    return attributeId;
  }
  
  public void setAttributeId(String attributeId) {
    this.attributeId = attributeId;
  }
  
  public String getMinimumPrice() {
    return minimumPrice;
  }
  
  public void setMinimumPrice(String minimumPrice) {
    this.minimumPrice = minimumPrice;
  }
  
  public String getMaximumPrice() {
    return maximumPrice;
  }
  
  public void setMaximumPrice(String maximumPrice) {
    this.maximumPrice = maximumPrice;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getOptions() {
    return options;
  }
  
  public void setOptions(String options) {
    this.options = options;
  }
  
  public String getLabel() {
    return label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public String getProductCount() {
    return productCount;
  }
  
  public void setProductCount(String productCount) {
    this.productCount = productCount;
  }
  
  public String getCategoryId() {
    return categoryId;
  }
  
  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }
  
  public String getParentId() {
    return parentId;
  }
  
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  
  public String getAttributeCode() {
    return attributeCode;
  }
  
  public void setAttributeCode(String attributeCode) {
    this.attributeCode = attributeCode;
  }
  
  public String getIsActive() {
    return isActive;
  }
  
  public void setIsActive(String isActive) {
    this.isActive = isActive;
  }
  
  public String getPosition() {
    return position;
  }
  
  public void setPosition(String position) {
    this.position = position;
  }
  
  public String getLevel() {
    return level;
  }
  
  public void setLevel(String level) {
    this.level = level;
  }
  
  public String getChildrenData() {
    return childrenData;
  }
  
  public void setChildrenData(String childrenData) {
    this.childrenData = childrenData;
  }
  
  @NonNull
  @Override
  public String toString() {
    return filterLabel+"\n"+
      filterType+"\n"+
      tree+"\n"+
      attributeId+"\n"+
      minimumPrice+"\n"+
      maximumPrice+"\n"+
      name+"\n"+
      options+"\n"+
      label+"\n"+
      productCount+"\n"+
      categoryId+"\n"+
      parentId+"\n"+
      attributeCode+"\n"+
      isActive+"\n"+
      position+"\n"+
      level+"\n"+
      childrenData+"\n";
  }
  
  public boolean isEmptylAll(){
    if(filterLabel==""&&
      filterType==""&&
      tree==""&&
      attributeId==""&&
      minimumPrice==""&&
      maximumPrice==""&&
      name==""&&
      options==""&&
      label==""&&
      productCount==""&&
      categoryId==""&&
      parentId==""&&
      attributeCode==""&&
      isActive==""&&
      position==""&&
      level==""&&
      childrenData==""){
      return true;
    }else {
      return false;
    }
  }
}
