package com.skypremiuminternational.app.data.model.ean.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hits {

  @SerializedName("hit")
  private List<HitItem> hit;

  @SerializedName("found")
  private int found;

  @SerializedName("start")
  private int start;

  public void setHit(List<HitItem> hit) {
    this.hit = hit;
  }

  public List<HitItem> getHit() {
    return hit;
  }

  public void setFound(int found) {
    this.found = found;
  }

  public int getFound() {
    return found;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getStart() {
    return start;
  }
}