package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class UpdateDelItem {
  @SerializedName("id")
  String  id;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
