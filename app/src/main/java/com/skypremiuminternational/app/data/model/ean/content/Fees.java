package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Fees {

  @SerializedName("optional")
  private String optional;

  @SerializedName("mandatory")
  private String mandatory;

  public void setOptional(String optional) {
    this.optional = optional;
  }

  public String getOptional() {
    return optional;
  }

  public void setMandatory(String mandatory) {
    this.mandatory = mandatory;
  }

  public String getMandatory() {
    return mandatory;
  }
}