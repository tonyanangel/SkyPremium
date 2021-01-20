package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Location {

  @SerializedName("coordinates")
  private Coordinates coordinates;

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }
}