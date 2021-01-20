package com.skypremiuminternational.app.data.model.billingaddress;

import com.google.gson.annotations.SerializedName;

public class RegionItem {

  @SerializedName("region_id")
  private String regionId;

  @SerializedName("region")
  private String region;

  @SerializedName("region_code")
  private String regionCode;

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getRegion() {
    return region;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getRegionCode() {
    return regionCode;
  }
}