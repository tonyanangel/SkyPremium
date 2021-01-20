package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class Filter implements Serializable {
  @SerializedName("field")
  @Expose
  private String field;
  @SerializedName("value")
  @Expose
  public String value;
  @SerializedName("condition_type")
  @Expose
  public String conditionType;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getConditionType() {
    return conditionType;
  }

  public void setConditionType(String conditionType) {
    this.conditionType = conditionType;
  }
}
