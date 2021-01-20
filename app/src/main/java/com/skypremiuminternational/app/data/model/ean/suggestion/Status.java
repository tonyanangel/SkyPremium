package com.skypremiuminternational.app.data.model.ean.suggestion;

import com.google.gson.annotations.SerializedName;

public class Status {

  @SerializedName("time-ms")
  private int timeMs;

  @SerializedName("rid")
  private String rid;

  public void setTimeMs(int timeMs) {
    this.timeMs = timeMs;
  }

  public int getTimeMs() {
    return timeMs;
  }

  public void setRid(String rid) {
    this.rid = rid;
  }

  public String getRid() {
    return rid;
  }
}