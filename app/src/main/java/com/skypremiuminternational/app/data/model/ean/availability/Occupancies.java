package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class Occupancies {

  @SerializedName("2-9,4")
  private Occupancy occupancy;

  public void setOccupancy(Occupancy occupancy) {
    this.occupancy = occupancy;
  }

  public Occupancy getOccupancy() {
    return occupancy;
  }
}