package com.skypremiuminternational.app.domain.models.myOrder.detail;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes {

    @SerializedName("shipping_assignments")
    private List<ShippingAssignmentsItem> shippingAssignments;
    @SerializedName("thumbnail_image_url")
    private String thumbnailImageUrl;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("total_sky_dollar_earn")
    private double totalSkyCollarEarn;


  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public void setShippingAssignments(List<ShippingAssignmentsItem> shippingAssignments) {
    this.shippingAssignments = shippingAssignments;
  }

  public List<ShippingAssignmentsItem> getShippingAssignments() {
    return shippingAssignments;
  }

  public double getTotalSkyCollarEarn() {
    return totalSkyCollarEarn;
  }

  public void setTotalSkyCollarEarn(double totalSkyCollarEarn) {
    this.totalSkyCollarEarn = totalSkyCollarEarn;
  }

  public String getThumbnailImageUrl() {
    return thumbnailImageUrl;
  }

  public void setThumbnailImageUrl(String thumbnailImageUrl) {
    this.thumbnailImageUrl = thumbnailImageUrl;
  }

  @Override
  public String toString() {
    return
        "ExtensionAttributes{" + "shipping_assignments = '" + shippingAssignments + '\'' + "}";
  }
}