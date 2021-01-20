package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Property {

  @SerializedName("rating")
  private String rating;

  @SerializedName("type")
  private String type;

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getRating() {
    return rating;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}