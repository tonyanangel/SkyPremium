package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Category {

  @SerializedName("name")
  private String name;

  @SerializedName("id")
  private int id;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}