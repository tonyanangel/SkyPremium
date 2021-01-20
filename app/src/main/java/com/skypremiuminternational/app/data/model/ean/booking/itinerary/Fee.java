package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

public class Fee {
  public final String type;
  public final String value;
  public final String scope;
  public final String frequency;
  public final String currency;

  public Fee(String type, String value, String scope, String frequency, String currency) {
    this.type = type;
    this.value = value;
    this.scope = scope;
    this.frequency = frequency;
    this.currency = currency;
  }
}
