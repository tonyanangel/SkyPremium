package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.myOrder.ShippingAssignment;

import java.io.Serializable;
import java.util.List;

public class ExtensionAttributes implements Serializable {

  @SerializedName("image_url")
  @Expose
  private String imageUrl;

  @SerializedName("category_name")
  @Expose
  private String  categoryName;

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String image_url) {
    this.imageUrl = image_url;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String category_name) {
    this.categoryName = category_name;
  }
}