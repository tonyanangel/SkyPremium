package com.skypremiuminternational.app.data.model.ean.content;

import com.skypremiuminternational.app.data.model.ean.availability.AmenityItem;

import java.util.Map;

public class Rates {

  public final String id;
  public final Map<String, AmenityItem> amenities;

  public Rates(String id, Map<String, AmenityItem> amenities) {
    this.id = id;
    this.amenities = amenities;
  }
}