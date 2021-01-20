package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RatesItem {

  @SerializedName("fenced_deal")
  private boolean fencedDeal;

  @SerializedName("bed_groups")
  private Map<String,BedGroupsItem> bedGroups;

  private Map<String,AmenityItem> amenities;

  @SerializedName("merchant_of_record")
  private String merchantOfRecord;

  @SerializedName("occupancy_pricing")
  private Map<String, Occupancy> occupancies;

  @SerializedName("refundable")
  private boolean refundable;

  @SerializedName("links")
  private Links links;

  @SerializedName("id")
  private String id;

  @SerializedName("deposit_required")
  private boolean depositRequired;

  @SerializedName("cancel_penalties")
  private List<CancelPenaltiesItem> cancelPenalties;

  @SerializedName("promo_id")
  private String promoId;

  @SerializedName("available_rooms")
  private int availableRooms;

  public void setFencedDeal(boolean fencedDeal) {
    this.fencedDeal = fencedDeal;
  }

  public boolean isFencedDeal() {
    return fencedDeal;
  }

  public void setBedGroups(Map<String,BedGroupsItem> bedGroups) {
    this.bedGroups = bedGroups;
  }

  public Map<String,BedGroupsItem> getBedGroups() {
    return bedGroups;
  }

  public void setMerchantOfRecord(String merchantOfRecord) {
    this.merchantOfRecord = merchantOfRecord;
  }

  public String getMerchantOfRecord() {
    return merchantOfRecord;
  }

  public void setOccupancies(Map<String, Occupancy> occupancies) {
    this.occupancies = occupancies;
  }

  public Map<String, Occupancy> getOccupancies() {
    return occupancies;
  }

  public void setRefundable(boolean refundable) {
    this.refundable = refundable;
  }

  public boolean isRefundable() {
    return refundable;
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

  public void setDepositRequired(boolean depositRequired) {
    this.depositRequired = depositRequired;
  }

  public boolean isDepositRequired() {
    return depositRequired;
  }

  public void setCancelPenalties(List<CancelPenaltiesItem> cancelPenalties) {
    this.cancelPenalties = cancelPenalties;
  }

  public List<CancelPenaltiesItem> getCancelPenalties() {
    return cancelPenalties;
  }

  public void setPromoId(String promoId) {
    this.promoId = promoId;
  }

  public String getPromoId() {
    return promoId;
  }

  public void setAvailableRooms(int availableRooms) {
    this.availableRooms = availableRooms;
  }

  public int getAvailableRooms() {
    return availableRooms;
  }

  public Map<String,AmenityItem> getAmenities() {
    return amenities;
  }

  public void setAmenities(
          Map<String,AmenityItem> amenities) {
    this.amenities = amenities;
  }
}