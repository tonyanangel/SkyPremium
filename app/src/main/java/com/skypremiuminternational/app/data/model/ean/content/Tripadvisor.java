package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Tripadvisor {

  @SerializedName("rating")
  private String rating;

  @SerializedName("count")
  private int count;

  @SerializedName("links")
  private Links links;

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getRating() {
    return rating;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }
}