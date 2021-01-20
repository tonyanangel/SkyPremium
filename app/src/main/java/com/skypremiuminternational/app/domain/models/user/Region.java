package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sandi on 11/12/17.
 */

public class Region implements Serializable {

  @SerializedName("region_code")
  @Expose
  private Object regionCode;
  @SerializedName("region")
  @Expose
  private Object region;
  @SerializedName("region_id")
  @Expose
  private int regionId;

  public Region() {
  }

  public Object getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(Object regionCode) {
    this.regionCode = regionCode;
  }

  public Object getRegion() {
    return region;
  }

  public void setRegion(Object region) {
    this.region = region;
  }

  public Integer getRegionId() {
    return regionId;
  }

  public void setRegionId(Integer regionId) {
    this.regionId = regionId;
  }
}
