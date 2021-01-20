package com.skypremiuminternational.app.domain.models.feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sandi on 11/17/17.
 */

public class FeatureCustomAttribute implements Serializable {

  @SerializedName("attribute_code")
  @Expose
  private String attributeCode;
  @SerializedName("value")
  @Expose
  private String value;

  public String getAttributeCode() {
    return attributeCode;
  }

  public void setAttributeCode(String attributeCode) {
    this.attributeCode = attributeCode;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
