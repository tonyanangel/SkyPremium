package com.skypremiuminternational.app.data.model.ean.booking.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;

import java.io.Serializable;
import java.util.List;

public class Booking {

  @SerializedName("booking_id")
  private String bookingId;

  @SerializedName("check_out")
  private String checkOut;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("booking_date")
  private String bookingDate;

  @SerializedName("check_in")
  private String checkIn;

  @SerializedName("booking_email")
  private String bookingEmail;

  @SerializedName("created_at")
  private String createdAt;

  @SerializedName("booking_status")
  private String bookingStatus;

  @SerializedName("id")
  private String id;

  @SerializedName("total_cost")
  private String totalCost;

  @SerializedName("customer_id")
  private String customerId;

  @SerializedName("hotel_name")
  private String hotelName;

  @SerializedName("booking_data")
  private Object bookingData;

  @SerializedName("ean_sky_dollar")
  private String skyDollar;


  public void setBookingId(String bookingId) {
    this.bookingId = bookingId;
  }

  public String getBookingId() {
    return bookingId;
  }

  public void setCheckOut(String checkOut) {
    this.checkOut = checkOut;
  }

  public String getCheckOut() {
    return checkOut;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setBookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
  }

  public String getBookingDate() {
    return bookingDate;
  }

  public void setCheckIn(String checkIn) {
    this.checkIn = checkIn;
  }

  public String getCheckIn() {
    return checkIn;
  }

  public void setBookingEmail(String bookingEmail) {
    this.bookingEmail = bookingEmail;
  }

  public String getBookingEmail() {
    return bookingEmail;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setBookingStatus(String bookingStatus) {
    this.bookingStatus = bookingStatus;
  }

  public String getBookingStatus() {
    return bookingStatus;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public String getHotelName() {
    return hotelName;
  }

  public Object getBookingData() {
    return bookingData;
  }

  public void setBookingData(Object bookingData) {
    this.bookingData = bookingData;
  }

  public String getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(String totalCost) {
    this.totalCost = totalCost;
  }


  public String getSkyDollar() {
    return skyDollar;
  }
}