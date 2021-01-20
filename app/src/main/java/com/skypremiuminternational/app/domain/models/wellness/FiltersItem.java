package com.skypremiuminternational.app.domain.models.wellness;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FiltersItem implements Serializable {

  @SerializedName("field")
  private String field;

  @SerializedName("condition_type")
  private String conditionType;

  @SerializedName("value")
  private String value;

  public void setField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }

  public void setConditionType(String conditionType) {
    this.conditionType = conditionType;
  }

  public String getConditionType() {
    return conditionType;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "FiltersItem{"
        + "field = '"
        + field
        + '\''
        + ",condition_type = '"
        + conditionType
        + '\''
        + ",value = '"
        + value
        + '\''
        + "}";
  }
}