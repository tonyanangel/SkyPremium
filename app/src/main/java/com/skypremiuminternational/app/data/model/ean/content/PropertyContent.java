package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class PropertyContent {

  @SerializedName("fees")
  private Fees fees;

  @SerializedName("images")
  private List<ImagesItem> images;

  @SerializedName("rooms")
  private Map<String, RoomContent> rooms;

  @SerializedName("address")
  private Address address;

  @SerializedName("rates")
  private Map<String, Rates> rates;

  @SerializedName("policies")
  private Policies policies;

  @SerializedName("descriptions")
  private Descriptions descriptions;

  @SerializedName("property_id")
  private String propertyId;

  @SerializedName("checkin")
  private Checkin checkin;

  @SerializedName("onsite_payments")
  private OnsitePayments onsitePayments;

  @SerializedName("phone")
  private String phone;

  @SerializedName("ratings")
  private Ratings ratings;

  @SerializedName("name")
  private String name;

  @SerializedName("rank")
  private int rank;

  @SerializedName("date_entered")
  private String dateEntered;

  @SerializedName("location")
  private Location location;

  @SerializedName("fax")
  private String fax;

  @SerializedName("category")
  private Category category;

  @SerializedName("checkout")
  private Checkout checkout;

  public void setFees(Fees fees) {
    this.fees = fees;
  }

  public Fees getFees() {
    return fees;
  }

  public void setImages(List<ImagesItem> images) {
    this.images = images;
  }

  public List<ImagesItem> getImages() {
    return images;
  }

  public Map<String, RoomContent> getRooms() {
    return rooms;
  }

  public void setRooms(Map<String, RoomContent> rooms) {
    this.rooms = rooms;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }

  public void setRates(Map<String, Rates> rates) {
    this.rates = rates;
  }

  public Map<String, Rates> getRates() {
    return rates;
  }

  public void setPolicies(Policies policies) {
    this.policies = policies;
  }

  public Policies getPolicies() {
    return policies;
  }

  public void setDescriptions(Descriptions descriptions) {
    this.descriptions = descriptions;
  }

  public Descriptions getDescriptions() {
    return descriptions;
  }

  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getPropertyId() {
    return propertyId;
  }

  public void setCheckin(Checkin checkin) {
    this.checkin = checkin;
  }

  public Checkin getCheckin() {
    return checkin;
  }

  public void setOnsitePayments(OnsitePayments onsitePayments) {
    this.onsitePayments = onsitePayments;
  }

  public OnsitePayments getOnsitePayments() {
    return onsitePayments;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhone() {
    return phone;
  }

  public void setRatings(Ratings ratings) {
    this.ratings = ratings;
  }

  public Ratings getRatings() {
    return ratings;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getRank() {
    return rank;
  }

  public void setDateEntered(String dateEntered) {
    this.dateEntered = dateEntered;
  }

  public String getDateEntered() {
    return dateEntered;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getFax() {
    return fax;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Category getCategory() {
    return category;
  }

  public void setCheckout(Checkout checkout) {
    this.checkout = checkout;
  }

  public Checkout getCheckout() {
    return checkout;
  }
}