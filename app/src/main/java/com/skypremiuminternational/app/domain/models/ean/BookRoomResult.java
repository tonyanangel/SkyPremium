package com.skypremiuminternational.app.domain.models.ean;

public class BookRoomResult {
  public final String itineraryId;
  public final String cancelItineraryLink;
  public final String retrieveItineraryLink;

  public BookRoomResult(String itineraryId, String cancelItineraryLink, String retrieveItineraryLink) {
    this.itineraryId = itineraryId;
    this.cancelItineraryLink = cancelItineraryLink;
    this.retrieveItineraryLink = retrieveItineraryLink;
  }
}
