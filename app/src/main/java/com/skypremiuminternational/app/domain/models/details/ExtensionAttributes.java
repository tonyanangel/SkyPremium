package com.skypremiuminternational.app.domain.models.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sandi on 10/24/17.
 */

public class ExtensionAttributes implements Serializable {

  @SerializedName("stock_item")
  @Expose
  public StockItem stockItem;

  @SerializedName("summary_rating")
  @Expose
  public float summaryRating;
  @SerializedName("display_reservations")
  @Expose
  public boolean displayReservations;

  public StockItem getStockItem() {
    return stockItem;
  }

  public void setStockItem(StockItem stockItem) {
    this.stockItem = stockItem;
  }

  public float getSummaryRating() {
    return summaryRating;
  }

  public void setSummaryRating(float summaryRating) {
    this.summaryRating = summaryRating;
  }

  public boolean isDisplayReservations() {
    return displayReservations;
  }

  public void setDisplayReservations(boolean displayReservations) {
    this.displayReservations = displayReservations;
  }
}
