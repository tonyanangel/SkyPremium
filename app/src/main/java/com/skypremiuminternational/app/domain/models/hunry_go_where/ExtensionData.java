package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExtensionData implements Serializable {


  @SerializedName("reservation_id")
  String reservationId;
  @SerializedName("verificationKey")
  String verificationKey;
  @SerializedName("date")
  String date;
  @SerializedName("time")
  Integer time;
  @SerializedName("adult")
  String adult;
  @SerializedName("child")
  String child;
  @SerializedName("note")
  String note;
  @SerializedName("shift_id")
  String shiftId;
  @SerializedName("outlet_id")
  String outletId;
  @SerializedName("image_url")
  String image_url;
  @SerializedName("cancelled_policy")
  String cancelledPolicy;
  @SerializedName("loyal_expiried")
  String loyalExpiried;


  public String getCancelledPolicy() {
    return cancelledPolicy;
  }

  public void setCancelledPolicy(String cancelledPolicy) {
    this.cancelledPolicy = cancelledPolicy;
  }

  public String getLoyalExpiried() {
    return loyalExpiried;
  }

  public void setLoyalExpiried(String loyalExpiried) {
    this.loyalExpiried = loyalExpiried;
  }

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }

  public String getOutletId() {
    return outletId;
  }

  public void setOutletId(String outletId) {
    this.outletId = outletId;
  }

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public String getVerificationKey() {
    return verificationKey;
  }

  public void setVerificationKey(String verificationKey) {
    this.verificationKey = verificationKey;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public String getAdult() {
    return adult;
  }

  public void setAdult(String adult) {
    this.adult = adult;
  }

  public String getChild() {
    return child;
  }

  public void setChild(String child) {
    this.child = child;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getShiftId() {
    return shiftId;
  }

  public void setShiftId(String shiftId) {
    this.shiftId = shiftId;
  }
}
