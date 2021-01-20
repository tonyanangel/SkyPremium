package com.skypremiuminternational.app.data.model.ean.booking.book;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.booking.history.Links;
import com.skypremiuminternational.app.data.model.ean.booking.history.RoomsItem;

import java.util.List;

public class EanResult {

  @SerializedName("booking_status")
  private boolean bookingStatus;

  private List<BookResponse.Error> errors;

  @SerializedName("rooms")
  private List<RoomsItem> rooms;

  @SerializedName("itinerary_id")
  private String itineraryId;

  @SerializedName("links")
  private Links links;

  @SerializedName("type")
  private String type;

  @SerializedName("message")
  private String message;

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

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }

  public List<BookResponse.Error> getErrors() {
    return errors;
  }

  public void setErrors(
      List<BookResponse.Error> errors) {
    this.errors = errors;
  }

  public boolean getBookingStatus() {
    return bookingStatus;
  }

  public void setBookingStatus(boolean bookingStatus) {
    this.bookingStatus = bookingStatus;
  }

  public boolean isBookingStatus() {
    return bookingStatus;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}