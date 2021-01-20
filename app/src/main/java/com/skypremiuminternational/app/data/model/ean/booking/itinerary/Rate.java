package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.availability.CancelPenaltiesItem;
import com.skypremiuminternational.app.data.model.ean.availability.NightlyItem;

import java.util.List;

public class Rate {

  private List<Fee> fees;

  @SerializedName("amenities")
  private List<Integer> amenities;

  @SerializedName("merchant_of_record")
  private String merchantOfRecord;

  @SerializedName("nightly")
  private List<List<NightlyItem>> nightly;

  @SerializedName("refundable")
  private boolean refundable;

  @SerializedName("id")
  private String id;

  @SerializedName("cancel_penalties")
  private List<CancelPenaltiesItem> cancelPenalties;

  @SerializedName("pricing")
  private Pricing pricing;

  public void setPricing(Pricing pricing) {
    this.pricing = pricing;
  }

  public Pricing getPricing() {
    return pricing;
  }

  public void setAmenities(List<Integer> amenities) {
    this.amenities = amenities;
  }

  public List<Integer> getAmenities() {
    return amenities;
  }

  public void setMerchantOfRecord(String merchantOfRecord) {
    this.merchantOfRecord = merchantOfRecord;
  }

  public String getMerchantOfRecord() {
    return merchantOfRecord;
  }

  public void setNightly(List<List<NightlyItem>> nightly) {
    this.nightly = nightly;
  }

  public List<List<NightlyItem>> getNightly() {
    return nightly;
  }

  public void setRefundable(boolean refundable) {
    this.refundable = refundable;
  }

  public boolean isRefundable() {
    return refundable;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setCancelPenalties(List<CancelPenaltiesItem> cancelPenalties) {
    this.cancelPenalties = cancelPenalties;
  }

  public List<CancelPenaltiesItem> getCancelPenalties() {
    return cancelPenalties;
  }

  public List<Fee> getFees() {
    return fees;
  }

  public void setFees(
      List<Fee> fees) {
    this.fees = fees;
  }
}