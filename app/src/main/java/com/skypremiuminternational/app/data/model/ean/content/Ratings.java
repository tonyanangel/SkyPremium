package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Ratings {

  @SerializedName("tripadvisor")
  private Tripadvisor tripadvisor;

  @SerializedName("property")
  private Property property;

  public void setTripadvisor(Tripadvisor tripadvisor) {
    this.tripadvisor = tripadvisor;
  }

  public Tripadvisor getTripadvisor() {
    return tripadvisor;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public Property getProperty() {
    return property;
  }
}