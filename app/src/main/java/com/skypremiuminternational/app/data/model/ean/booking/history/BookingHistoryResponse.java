package com.skypremiuminternational.app.data.model.ean.booking.history;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingHistoryResponse {

  @SerializedName("items")
  private List<Booking> items;

  public void setItems(List<Booking> items) {
    this.items = items;
  }

  public List<Booking> getItems() {
    return items;
  }
}