package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomsItem {

  @SerializedName("arrival")
  private String arrival;

  @SerializedName("rate")
  private Rate rate;

  @SerializedName("bed_group_id")
  private String bedGroupId;

  @SerializedName("number_of_adults")
  private int numberOfAdults;

  @SerializedName("smoking")
  private boolean smoking;

  @SerializedName("child_ages")
  private List<Integer> childAges;

  @SerializedName("links")
  private Links links;

  @SerializedName("id")
  private String id;

  @SerializedName("departure")
  private String departure;

  @SerializedName("given_name")
  private String givenName;

  @SerializedName("family_name")
  private String familyName;

  @SerializedName("status")
  private String status;

  public void setArrival(String arrival) {
    this.arrival = arrival;
  }

  public String getArrival() {
    return arrival;
  }

  public void setRate(Rate rate) {
    this.rate = rate;
  }

  public Rate getRate() {
    return rate;
  }

  public void setBedGroupId(String bedGroupId) {
    this.bedGroupId = bedGroupId;
  }

  public String getBedGroupId() {
    return bedGroupId;
  }

  public void setNumberOfAdults(int numberOfAdults) {
    this.numberOfAdults = numberOfAdults;
  }

  public int getNumberOfAdults() {
    return numberOfAdults;
  }

  public void setSmoking(boolean smoking) {
    this.smoking = smoking;
  }

  public boolean isSmoking() {
    return smoking;
  }

  public void setChildAges(List<Integer> childAges) {
    this.childAges = childAges;
  }

  public List<Integer> getChildAges() {
    return childAges;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setDeparture(String departure) {
    this.departure = departure;
  }

  public String getDeparture() {
    return departure;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}