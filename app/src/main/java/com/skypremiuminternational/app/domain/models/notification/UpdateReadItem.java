package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class UpdateReadItem {


  @SerializedName("id")
  String  id;
  @SerializedName("read_time")
  String  readTime;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReadTime() {
    return readTime;
  }

  public void setReadTime(String readTime) {
    this.readTime = readTime;
  }
}
