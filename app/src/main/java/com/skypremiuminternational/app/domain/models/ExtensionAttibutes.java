package com.skypremiuminternational.app.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wmw on 2/7/2018.
 */

public class ExtensionAttibutes implements Serializable {
  @SerializedName("stock_item")
  @Expose
  public StockItem stockItem;

  public ExtensionAttibutes(StockItem stockItem) {
    this.stockItem = stockItem;
  }

  public StockItem getStockItem() {
    return stockItem;
  }

  public void setStockItem(StockItem stockItem) {
    this.stockItem = stockItem;
  }
}
