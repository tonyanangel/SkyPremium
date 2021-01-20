package com.skypremiuminternational.app.data.network.request;

import java.util.HashMap;
import java.util.Map;

public class ReservationRequest {

  String outletId;
  String date;
  Integer time;
  Integer adult;
  Integer child;
  String note;
  Integer shiftId;
  Integer productId;

  //for edit
  String reservationId;
  String verificationKey;
  //for cancellation
  String cancelPolicy;
  //for display
  String restaurantName;

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public String getCancelPolicy() {
    return cancelPolicy;
  }

  public void setCancelPolicy(String cancelPolicy) {
    this.cancelPolicy = cancelPolicy;
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

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public String getOutletId() {
    return outletId;
  }

  public void setOutletId(String outletId) {
    this.outletId = outletId;
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

  public Integer getAdult() {
    return adult;
  }

  public void setAdult(Integer adult) {
    this.adult = adult;
  }

  public Integer getChild() {
    return child;
  }

  public void setChild(Integer child) {
    this.child = child;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getShiftId() {
    return shiftId;
  }

  public void setShiftId(Integer shiftId) {
    this.shiftId = shiftId;
  }

  public Map<String,String> toMap (){
    Map<String,String> map = new HashMap<>();

    map.put("outlet_id",getOutletId());
    map.put("date",getDate());
    map.put("time",getTime().toString());
    map.put("adult",getAdult().toString());
    map.put("child",getChild().toString());
    map.put("note",getNote());
    map.put("shift_id",getShiftId().toString());
    map.put("product_id",getProductId().toString());

    return map;
  }
}
