package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Descriptions {

  @SerializedName("amenities")
  private String amenities;

  @SerializedName("national_ratings")
  private String nationalRatings;

  @SerializedName("rooms")
  private String rooms;

  @SerializedName("dining")
  private String dining;

  @SerializedName("business_amenities")
  private String businessAmenities;

  @SerializedName("location")
  private String location;

  @SerializedName("attractions")
  private String attractions;

  @SerializedName("renovations")
  private String renovations;

  public void setAmenities(String amenities) {
    this.amenities = amenities;
  }

  public String getAmenities() {
    return amenities;
  }

  public void setNationalRatings(String nationalRatings) {
    this.nationalRatings = nationalRatings;
  }

  public String getNationalRatings() {
    return nationalRatings;
  }

  public void setRooms(String rooms) {
    this.rooms = rooms;
  }

  public String getRooms() {
    return rooms;
  }

  public void setDining(String dining) {
    this.dining = dining;
  }

  public String getDining() {
    return dining;
  }

  public void setBusinessAmenities(String businessAmenities) {
    this.businessAmenities = businessAmenities;
  }

  public String getBusinessAmenities() {
    return businessAmenities;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }

  public void setAttractions(String attractions) {
    this.attractions = attractions;
  }

  public String getAttractions() {
    return attractions;
  }

  public String getRenovations() {
    return renovations;
  }

  public void setRenovations(String renovations) {
    this.renovations = renovations;
  }
}