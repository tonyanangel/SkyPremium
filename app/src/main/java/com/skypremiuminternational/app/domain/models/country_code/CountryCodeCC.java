package com.skypremiuminternational.app.domain.models.country_code;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryCodeCC implements Serializable {


  @SerializedName("value")
  @Expose
  private String value;
  @SerializedName("label")
  @Expose
  private String label;
  @SerializedName("is_region_visible")
  @Expose
  private Boolean isRegionVisible;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Boolean getRegionVisible() {
    return isRegionVisible;
  }

  public void setRegionVisible(Boolean regionVisible) {
    isRegionVisible = regionVisible;
  }
}
