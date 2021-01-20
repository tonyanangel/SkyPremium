package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableProperty {

  @SerializedName("rooms")
  private List<RoomsItem> rooms;

  @SerializedName("property_id")
  private String propertyId;

  public void setRooms(List<RoomsItem> rooms) {
    this.rooms = rooms;
  }

  public List<RoomsItem> getRooms() {
    return rooms;
  }

  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getPropertyId() {
    return propertyId;
  }
}