package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

public class ReserveTimeSlotItem {

  @SerializedName("timeSlotId")
  Integer timeSlotId;
  @SerializedName("remainingTimeSlotCapacity")
  Integer remainingTimeSlotCapacity;
  @SerializedName("remainingShiftCapacity")
  Integer remainingShiftCapacity;
  @SerializedName("maxNumberOfSeatsForSingleReservation")
  Integer maxNumberOfSeatsForSingleReservation;
  @SerializedName("minNumberOfSeatsForSingleReservation")
  Integer minNumberOfSeatsForSingleReservation;
  @SerializedName("shiftId")
  Integer shiftId;
  @SerializedName("cutOffTime")
  Integer cutOffTime;
  @SerializedName("minPartySizeBooking")
  Object minPartySizeBooking;
  @SerializedName("maxPartySizeBooking")
  Object maxPartySizeBooking;
  @SerializedName("tableTags")
  Object tableTags;
  @SerializedName("dynamicCutoffTime")
  Object dynamicCutoffTime;


  public Integer getTimeSlotId() {
    return timeSlotId;
  }

  public void setTimeSlotId(Integer timeSlotId) {
    this.timeSlotId = timeSlotId;
  }

  public Integer getRemainingTimeSlotCapacity() {
    return remainingTimeSlotCapacity;
  }

  public void setRemainingTimeSlotCapacity(Integer remainingTimeSlotCapacity) {
    this.remainingTimeSlotCapacity = remainingTimeSlotCapacity;
  }

  public Integer getRemainingShiftCapacity() {
    return remainingShiftCapacity;
  }

  public void setRemainingShiftCapacity(Integer remainingShiftCapacity) {
    this.remainingShiftCapacity = remainingShiftCapacity;
  }

  public Integer getMaxNumberOfSeatsForSingleReservation() {
    return maxNumberOfSeatsForSingleReservation;
  }

  public void setMaxNumberOfSeatsForSingleReservation(Integer maxNumberOfSeatsForSingleReservation) {
    this.maxNumberOfSeatsForSingleReservation = maxNumberOfSeatsForSingleReservation;
  }

  public Integer getMinNumberOfSeatsForSingleReservation() {
    return minNumberOfSeatsForSingleReservation;
  }

  public void setMinNumberOfSeatsForSingleReservation(Integer minNumberOfSeatsForSingleReservation) {
    this.minNumberOfSeatsForSingleReservation = minNumberOfSeatsForSingleReservation;
  }

  public Integer getShiftId() {
    return shiftId;
  }

  public void setShiftId(Integer shiftId) {
    this.shiftId = shiftId;
  }

  public Integer getCutOffTime() {
    return cutOffTime;
  }

  public void setCutOffTime(Integer cutOffTime) {
    this.cutOffTime = cutOffTime;
  }

  public Object getMinPartySizeBooking() {
    return minPartySizeBooking;
  }

  public void setMinPartySizeBooking(Object minPartySizeBooking) {
    this.minPartySizeBooking = minPartySizeBooking;
  }

  public Object getMaxPartySizeBooking() {
    return maxPartySizeBooking;
  }

  public void setMaxPartySizeBooking(Object maxPartySizeBooking) {
    this.maxPartySizeBooking = maxPartySizeBooking;
  }

  public Object getTableTags() {
    return tableTags;
  }

  public void setTableTags(Object tableTags) {
    this.tableTags = tableTags;
  }

  public Object getDynamicCutoffTime() {
    return dynamicCutoffTime;
  }

  public void setDynamicCutoffTime(Object dynamicCutoffTime) {
    this.dynamicCutoffTime = dynamicCutoffTime;
  }
}
