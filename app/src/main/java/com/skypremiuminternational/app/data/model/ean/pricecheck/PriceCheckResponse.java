package com.skypremiuminternational.app.data.model.ean.pricecheck;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.availability.Links;
import com.skypremiuminternational.app.data.model.ean.availability.Occupancies;
import com.skypremiuminternational.app.data.model.ean.availability.Occupancy;

import java.util.Map;

public class PriceCheckResponse {

  @SerializedName("occupancy_pricing")
  private Map<String, Occupancy> occupancies;

  @SerializedName("links")
  private Links links;

  @SerializedName("status")
  private String status;

  public void setOccupancies(Map<String, Occupancy> occupancies) {
    this.occupancies = occupancies;
  }

  public Map<String, Occupancy> getOccupancies() {
    return occupancies;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}