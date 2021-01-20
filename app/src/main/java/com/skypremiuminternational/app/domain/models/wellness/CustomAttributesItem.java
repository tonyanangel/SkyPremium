package com.skypremiuminternational.app.domain.models.wellness;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomAttributesItem implements Serializable {

  @SerializedName("value")
  private Object value;

  @SerializedName("attribute_code")
  private String attributeCode;

  public void setValue(Object value) {
    this.value = value;
  }

  public Object getValue() {
    return value;
  }

  public void setAttributeCode(String attributeCode) {
    this.attributeCode = attributeCode;
  }

  public String getAttributeCode() {
    return attributeCode;
  }

  @Override
  public String toString() {
    return "CustomAttributesItem{"
        + "value = '"
        + value
        + '\''
        + ",attribute_code = '"
        + attributeCode
        + '\''
        + "}";
  }
}