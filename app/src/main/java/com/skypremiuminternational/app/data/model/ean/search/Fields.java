package com.skypremiuminternational.app.data.model.ean.search;

import com.google.gson.annotations.SerializedName;

public class Fields {

  @SerializedName("category_name")
  private String categoryName;

  @SerializedName("city")
  private String city;

  @SerializedName("rating")
  private String rating;

  @SerializedName("property_id")
  private String propertyId;

  @SerializedName("country_code")
  private String countryCode;

  @SerializedName("category_id")
  private String categoryId;

  @SerializedName("rating_type")
  private String ratingType;

  @SerializedName("name")
  private String name;

  @SerializedName("address_line_1")
  private String addressLine1;

  @SerializedName("rank")
  private String rank;

  @SerializedName("location")
  private String location;

  @SerializedName("address_line_2")
  private String addressLine2;

  @SerializedName("postal_code")
  private String postalCode;

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getRating() {
    return rating;
  }

  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getPropertyId() {
    return propertyId;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setRatingType(String ratingType) {
    this.ratingType = ratingType;
  }

  public String getRatingType() {
    return ratingType;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public String getRank() {
    return rank;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPostalCode() {
    return postalCode;
  }
}