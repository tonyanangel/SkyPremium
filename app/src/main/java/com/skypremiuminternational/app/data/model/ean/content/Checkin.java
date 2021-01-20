package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Checkin {

  @SerializedName("instructions")
  private String instructions;

  @SerializedName("special_instructions")
  private String specialInstructions;

  @SerializedName("end_time")
  private String endTime;

  @SerializedName("begin_time")
  private String beginTime;

  @SerializedName("min_age")
  private String minAge;

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setSpecialInstructions(String specialInstructions) {
    this.specialInstructions = specialInstructions;
  }

  public String getSpecialInstructions() {
    return specialInstructions;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setMinAge(String minAge) {
    this.minAge = minAge;
  }

  public String getMinAge() {
    return minAge;
  }
}