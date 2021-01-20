package com.skypremiuminternational.app.data.model.ean.booking.history;

import com.google.gson.annotations.SerializedName;

public class Links {

  @SerializedName("retrieve")
  private Retrieve retrieve;

  public void setRetrieve(Retrieve retrieve) {
    this.retrieve = retrieve;
  }

  public Retrieve getRetrieve() {
    return retrieve;
  }
}