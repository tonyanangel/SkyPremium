package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReserveHistoryItem implements Serializable {


  @SerializedName("h_gw_id")
  String hgwId;
  @SerializedName("reservation_id")
  String reservationId;
  @SerializedName("restaurant_name")
  String restaurantName;
  @SerializedName("reservation_on_date")
  String reservationOnDate;
  @SerializedName("reservation_date")
  String reservationDate;
  @SerializedName("membership_id")
  String membershipId;
  @SerializedName("adult_guests")
  String adultGuests;
  @SerializedName("child_guests")
  String childGuests;
  @SerializedName("reservation_status")
  String reservationStatus;
  @SerializedName("reservation_sky_dollar_earn")
  String reservationSkyDollarEarn;
  @SerializedName("reservation_data")
  String reservationData;
  @SerializedName("product_id")
  String productId;
  @SerializedName("loyal_expiried")
  String loyalExpiried;
  @SerializedName("extension_data")
  List<ExtensionData> extensionData;

  public String getLoyalExpiried() {
    return loyalExpiried;
  }

  public void setLoyalExpiried(String loyalExpiried) {
    this.loyalExpiried = loyalExpiried;
  }

  public String getReservationSkyDollarEarn() {
    return reservationSkyDollarEarn;
  }

  public void setReservationSkyDollarEarn(String reservationSkyDollarEarn) {
    this.reservationSkyDollarEarn = reservationSkyDollarEarn;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getHgwId() {
    return hgwId;
  }

  public void setHgwId(String hgwId) {
    this.hgwId = hgwId;
  }

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public String getReservationOnDate() {
    return reservationOnDate;
  }

  public void setReservationOnDate(String reservationOnDate) {
    this.reservationOnDate = reservationOnDate;
  }

  public String getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(String reservationDate) {
    this.reservationDate = reservationDate;
  }

  public String getMembershipId() {
    return membershipId;
  }

  public void setMembershipId(String membershipId) {
    this.membershipId = membershipId;
  }

  public String getAdultGuests() {
    return adultGuests;
  }

  public void setAdultGuests(String adultGuests) {
    this.adultGuests = adultGuests;
  }

  public String getChildGuests() {
    return childGuests;
  }

  public void setChildGuests(String childGuests) {
    this.childGuests = childGuests;
  }

  public String getReservationStatus() {
    return reservationStatus;
  }

  public void setReservationStatus(String reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  public String getReservationData() {
    return reservationData;
  }

  public void setReservationData(String reservationData) {
    this.reservationData = reservationData;
  }

  public List<ExtensionData> getExtensionData() {
    return extensionData;
  }

  public void setExtensionData(List<ExtensionData> extensionData) {
    this.extensionData = extensionData;
  }
}
