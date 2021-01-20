package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.availability.AmenityItem;

import java.util.List;
import java.util.Map;

public class RoomContent {

  @SerializedName("amenities")
  private Map<String, AmenityItem> amenities;

  @SerializedName("images")
  private List<ImagesItem> images;

  @SerializedName("bed_groups")
  private Map<String, BedGroup> bedGroups;

  @SerializedName("name")
  private String name;

  @SerializedName("id")
  private String id;

  @SerializedName("descriptions")
  private Description descriptions;

  public Map<String, AmenityItem> getAmenities() {
    return amenities;
  }

  public void setAmenities(Map<String, AmenityItem> amenities) {
    this.amenities = amenities;
  }

  public void setImages(List<ImagesItem> images) {
    this.images = images;
  }

  public List<ImagesItem> getImages() {
    return images;
  }

  public Map<String, BedGroup> getBedGroups() {
    return bedGroups;
  }

  public void setBedGroups(Map<String, BedGroup> bedGroups) {
    this.bedGroups = bedGroups;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setDescriptions(Description descriptions) {
    this.descriptions = descriptions;
  }

  public Description getDescriptions() {
    return descriptions;
  }
}