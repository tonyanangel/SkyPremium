package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

public class ConfigurationItem {

  @SerializedName("size")
  private String size;

  @SerializedName("count")
  private int count;

  @SerializedName("type")
  private String type;

  public void setSize(String size) {
    this.size = size;
  }

  public String getSize() {
    return size;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}