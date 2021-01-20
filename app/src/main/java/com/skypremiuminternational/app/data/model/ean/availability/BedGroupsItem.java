package com.skypremiuminternational.app.data.model.ean.availability;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class  BedGroupsItem {

  @SerializedName("configuration")
  private List<ConfigurationItem> configuration;

  @SerializedName("links")
  private Links links;

  @SerializedName("id")
  private String id;

  public void setConfiguration(List<ConfigurationItem> configuration) {
    this.configuration = configuration;
  }

  public List<ConfigurationItem> getConfiguration() {
    return configuration;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}