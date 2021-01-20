package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItineraryResponse {

  @SerializedName("rooms")
  private List<RoomsItem> rooms;

  @SerializedName("billing_contact")
  private BillingContact billingContact;

  @SerializedName("itinerary_id")
  private String itineraryId;

  @SerializedName("creation_date_time")
  private String creationDateTime;

  @SerializedName("property_id")
  private String propertyId;

  @SerializedName("affiliate_reference_id")
  private String affiliateReferenceId;

  public void setRooms(List<RoomsItem> rooms) {
    this.rooms = rooms;
  }

  public List<RoomsItem> getRooms() {
    return rooms;
  }

  public void setBillingContact(BillingContact billingContact) {
    this.billingContact = billingContact;
  }

  public BillingContact getBillingContact() {
    return billingContact;
  }

  public void setItineraryId(String itineraryId) {
    this.itineraryId = itineraryId;
  }

  public String getItineraryId() {
    return itineraryId;
  }

  public void setCreationDateTime(String creationDateTime) {
    this.creationDateTime = creationDateTime;
  }

  public String getCreationDateTime() {
    return creationDateTime;
  }

  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getPropertyId() {
    return propertyId;
  }

  public void setAffiliateReferenceId(String affiliateReferenceId) {
    this.affiliateReferenceId = affiliateReferenceId;
  }

  public String getAffiliateReferenceId() {
    return affiliateReferenceId;
  }
}