package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

public class ReserveResultDataItem {

  @SerializedName("id")
  String bookingNumber;
  @SerializedName("diner")
  Diner diner;
  @SerializedName("restaurant")
  Restaurant restaurant;
  @SerializedName("sky_dollar_earn")
  String skyDollarEarn;
  @SerializedName("loyalty_expired")
  String loyaltyExpired;

  public String getSkyDollarEarn() {
    return skyDollarEarn;
  }

  public void setSkyDollarEarn(String skyDollarEarn) {
    this.skyDollarEarn = skyDollarEarn;
  }

  public String getLoyaltyExpired() {
    return loyaltyExpired;
  }

  public void setLoyaltyExpired(String loyaltyExpired) {
    this.loyaltyExpired = loyaltyExpired;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public String getBookingNumber() {
    return bookingNumber;
  }

  public void setBookingNumber(String bookingNumber) {
    this.bookingNumber = bookingNumber;
  }

  public Diner getDiner() {
    return diner;
  }

  public void setDiner(Diner diner) {
    this.diner = diner;
  }
}
