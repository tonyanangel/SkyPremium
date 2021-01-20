package com.skypremiuminternational.app.data.model.ean.search;

import com.google.gson.annotations.SerializedName;

public class SearchPropertiesResponse {

  @SerializedName("hits")
  private Hits hits;

  @SerializedName("status")
  private Status status;

  public void setHits(Hits hits) {
    this.hits = hits;
  }

  public Hits getHits() {
    return hits;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Status getStatus() {
    return status;
  }
}