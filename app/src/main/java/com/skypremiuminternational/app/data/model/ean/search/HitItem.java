package com.skypremiuminternational.app.data.model.ean.search;

import com.google.gson.annotations.SerializedName;

public class HitItem {

  @SerializedName("id")
  private String id;

  @SerializedName("fields")
  private Fields fields;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setFields(Fields fields) {
    this.fields = fields;
  }

  public Fields getFields() {
    return fields;
  }
}