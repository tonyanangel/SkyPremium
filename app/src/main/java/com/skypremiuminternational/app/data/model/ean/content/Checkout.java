package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Checkout {

  @SerializedName("time")
  private String time;

  public void setTime(String time) {
    this.time = time;
  }

  public String getTime() {
    return time;
  }
}