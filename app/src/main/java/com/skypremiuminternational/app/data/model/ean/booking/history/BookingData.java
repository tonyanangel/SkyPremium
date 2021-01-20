package com.skypremiuminternational.app.data.model.ean.booking.history;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingData {

  @SerializedName("rooms")
  private List<RoomsItem> rooms;

  @SerializedName("itinerary_id")
  private String itineraryId;

  @SerializedName("label_status")
  private String label_status;

  @SerializedName("isDisplayCancelsButton")
  private boolean isDisplayCancelsButton;

  @SerializedName("links")
  private Links links;

  @SerializedName("cancels_penalties")
  private List<CancelsPenalties> cancelsPenaltiesList ;

  public void setCancelsPenaltiesList(List<CancelsPenalties> cancelsPenaltiesList) {
    this.cancelsPenaltiesList = cancelsPenaltiesList;
  }

  public List<CancelsPenalties> getCancelsPenaltiesList () {
    return cancelsPenaltiesList;
  }

  private AdditionalData additionalData;

  public void setDisplayCancelsButton(boolean isDisplayCancelsButton) {
    this.isDisplayCancelsButton = isDisplayCancelsButton;
  }

  public boolean getDisplayCancelsButton() {
    return isDisplayCancelsButton;
  }

  public void setRooms(List<RoomsItem> rooms) {
    this.rooms = rooms;
  }

  public List<RoomsItem> getRooms() {
    return rooms;
  }

  public void setItineraryId(String itineraryId) {
    this.itineraryId = itineraryId;
  }

  public String getItineraryId() {
    return itineraryId;
  }

  public void setLabel_status(String label_status) {
    this.label_status = label_status;
  }

  public String getLabel_status() {
    return label_status;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }

  public AdditionalData getAdditionalData() {
    return additionalData;
  }

  public void setAdditionalData(
      AdditionalData additionalData) {
    this.additionalData = additionalData;
  }
}