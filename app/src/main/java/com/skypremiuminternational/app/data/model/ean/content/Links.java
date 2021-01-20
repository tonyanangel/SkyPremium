package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Links {

  @SerializedName("350px")
  private Dimension350px dimension350Px;

  @SerializedName("70px")
  private Dimension70px dimension70Px;

  @SerializedName("200px")
  private Dimension200px dimension200Px;

  @SerializedName("1000px")
  private Dimension1000px dimension1000Px;

  public void setDimension350Px(Dimension350px dimension350Px) {
    this.dimension350Px = dimension350Px;
  }

  public Dimension350px getDimension350Px() {
    return dimension350Px;
  }

  public void setDimension70Px(Dimension70px dimension70Px) {
    this.dimension70Px = dimension70Px;
  }

  public Dimension70px getDimension70Px() {
    return dimension70Px;
  }

  public void setDimension200Px(Dimension200px dimension200Px) {
    this.dimension200Px = dimension200Px;
  }

  public Dimension200px getDimension200Px() {
    return dimension200Px;
  }

  public void setDimension1000Px(Dimension1000px dimension1000Px) {
    this.dimension1000Px = dimension1000Px;
  }

  public Dimension1000px getDimension1000Px() {
    return dimension1000Px;
  }
}