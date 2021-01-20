package com.skypremiuminternational.app.domain.models.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 10/24/17.
 */

public class CustomAttribute implements Serializable {

  @SerializedName("attribute_code")
  @Expose
  public String attributeCode;
  @SerializedName("value")
  @Expose
  public Object value;

  public String getAttributeCode() {
    return attributeCode;
  }

  public void setAttributeCode(String attributeCode) {
    this.attributeCode = attributeCode;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
