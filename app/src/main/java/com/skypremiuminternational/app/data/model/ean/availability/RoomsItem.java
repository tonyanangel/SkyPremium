package com.skypremiuminternational.app.data.model.ean.availability;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomsItem {

  @SerializedName("room_name")
  @Nullable
  private String roomName;

  @SerializedName("rates")
  private List<RatesItem> rates;

  @SerializedName("id")
  private String id;

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRates(List<RatesItem> rates) {
    this.rates = rates;
  }

  public List<RatesItem> getRates() {
    return rates;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}