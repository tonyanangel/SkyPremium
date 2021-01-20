package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.util.List;

public class FilterListResponse {
  @SerializedName("items")
  @Expose
  private List<FilterResultResponse> items;
  
  
  public List<FilterResultResponse> getItems() {
    return items;
  }
  
  public void setItems(List<FilterResultResponse> items) {
    this.items = items;
  }
  
  
/*  public List<ItemsItem> getItemsOld() {
    ItemsItem itemsItem = new ItemsItem();
    
    for(FilterResultResponse item : this.items ){
      itemsItem.setAttributeSetId(item.getAttribute_set_id());
      itemsItem.setCategoryName(item.get());
      itemsItem.setCreatedAt(item.getAttribute_set_id());
      itemsItem.setCurrentServerTime(item.getAttribute_set_id());
      itemsItem.setCustomAttributes(item.getAttribute_set_id());
      itemsItem.setDealFromDate(item.getAttribute_set_id());
      itemsItem.setDealStatus(item.getAttribute_set_id());
      itemsItem.setDealToDate(item.getAttribute_set_id());
      itemsItem.setDiscountPercentage(item.getAttribute_set_id());
      itemsItem.setDiscountType(item.getAttribute_set_id());
      itemsItem.setExtensionAttributes(item.getAttribute_set_id());
      itemsItem.setFavourite(item.getAttribute_set_id());
      itemsItem.setFlashSales(item.getAttribute_set_id());
      itemsItem.setId(item.getAttribute_set_id());
      itemsItem.setName(item.getAttribute_set_id());
      itemsItem.setNew(item.getAttribute_set_id());
    }
    
    
    return itemsItem;
  }*/
  
}
