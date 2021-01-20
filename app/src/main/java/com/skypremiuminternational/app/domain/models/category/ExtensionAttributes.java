package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.StockItem;

public class ExtensionAttributes {
  
  @SerializedName("stock_item")
  @Expose
  private StockItem stockItem;
  
  
  public StockItem getStockItem() {
    return stockItem;
  }
  
  public void setStockItem(StockItem stockItem) {
    this.stockItem = stockItem;
  }
}
