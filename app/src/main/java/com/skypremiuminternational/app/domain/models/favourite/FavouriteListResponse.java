package com.skypremiuminternational.app.domain.models.favourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.ExtensionAttibutes;
import com.skypremiuminternational.app.domain.models.favourite.ExtensionAttributes;

import java.io.Serializable;

/**
 * Created by aeindraaung on 1/24/18.
 */

public class FavouriteListResponse implements Serializable {
  @SerializedName("wishlist_item_id")
  @Expose
  private String wishlistItemId;
  @SerializedName("wishlist_id")
  @Expose
  private String wishlistId;
  @SerializedName("product_id")
  @Expose
  private String productId;
  @SerializedName("store_id")
  @Expose
  private String storeId;
  @SerializedName("added_at")
  @Expose
  private String addedAt;
  @SerializedName("description")
  @Expose
  private Object description;
  @SerializedName("qty")
  @Expose
  private Integer qty;
  @SerializedName("product")
  @Expose
  private Product product;
  @SerializedName("extension_attributes")
  @Expose
  private ExtensionAttributes extensionAttibutes;
  private String pillarId;
  private String categoryName;
  private String pillarName;



  public ExtensionAttributes getExtensionAttibutes() {
    return extensionAttibutes;
  }

  public void setExtensionAttibutes(ExtensionAttributes extensionAttibutes) {
    this.extensionAttibutes = extensionAttibutes;
  }


  public String getWishlistItemId() {
    return wishlistItemId;
  }

  public void setWishlistItemId(String wishlistItemId) {
    this.wishlistItemId = wishlistItemId;
  }

  public String getWishlistId() {
    return wishlistId;
  }

  public void setWishlistId(String wishlistId) {
    this.wishlistId = wishlistId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getAddedAt() {
    return addedAt;
  }

  public void setAddedAt(String addedAt) {
    this.addedAt = addedAt;
  }

  public Object getDescription() {
    return description;
  }

  public void setDescription(Object description) {
    this.description = description;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getPillarId() {
    return pillarId;
  }

  public void setPillarId(String pillarId) {
    this.pillarId = pillarId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getPillarName() {
    return pillarName;
  }

  public void setPillarName(String pillarName) {
    this.pillarName = pillarName;
  }
}
