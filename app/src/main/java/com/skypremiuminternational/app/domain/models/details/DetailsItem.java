package com.skypremiuminternational.app.domain.models.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 10/24/17.
 */

public class DetailsItem implements Serializable {

  public boolean isFavourite;
  @SerializedName("id")
  @Expose
  public Integer id;
  @SerializedName("sku")
  @Expose
  public String sku;
  @SerializedName("name")
  @Expose
  public String name;
  @SerializedName("attribute_set_id")
  @Expose
  public Integer attributeSetId;
  @SerializedName("price")
  @Expose
  public Object price;
  @SerializedName("status")
  @Expose
  public Integer status;
  @SerializedName("visibility")
  @Expose
  public Integer visibility;
  @SerializedName("type_id")
  @Expose
  public String typeId;
  @SerializedName("created_at")
  @Expose
  public String createdAt;
  @SerializedName("updated_at")
  @Expose
  public String updatedAt;
  @SerializedName("extension_attributes")
  @Expose
  public ExtensionAttributes extensionAttributes;
  @SerializedName("product_links")
  @Expose
  public List<Object> productLinks = null;
  @SerializedName("options")
  @Expose
  public List<Object> options = null;
  @SerializedName("media_gallery_entries")
  @Expose
  public List<MediaGalleryEntry>
      mediaGalleryEntries = null;
  @SerializedName("tier_prices")
  @Expose
  public List<Object> tierPrices = null;
  @SerializedName("custom_attributes")
  @Expose
  public List<CustomAttribute> customAttributes = null;
  @SerializedName("flash_sale")
  @Expose
  private boolean flashSales;
  private boolean dealStatus;
  private String discountType;
  private String discountPercentage;
  private String specialPrice;
  private String specialFromDate;
  private String specialToDate;
  private String dealFromDate;
  private String dealToDate;
  private String currentServerTime;
  private boolean isNew;
  private boolean isWholeSale;
  private String categoryName;
  private String dealValue;
  private String pillarName;
  private String pillarId;


  public String getPillarName() {
    return pillarName;
  }

  public void setPillarName(String pillarName) {
    this.pillarName = pillarName;
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

  public String getSpecialFromDate() {
    return specialFromDate;
  }

  public void setSpecialFormDate(String specialFromDate) {
    this.specialFromDate = specialFromDate;
  }

  public String getSpecialToDate() {
    return specialToDate;
  }

  public void setSpecialToDate(String specialToDate) {
    this.specialToDate = specialToDate;
  }

  public boolean isDealStatus() {
    return dealStatus;
  }

  public void setDealStatus(boolean dealStatus) {
    this.dealStatus = dealStatus;
  }

  public String getDealValue() {
    return dealValue;
  }

  public void setDealValue(String dealValue) {
    this.dealValue = dealValue;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAttributeSetId() {
    return attributeSetId;
  }

  public void setAttributeSetId(Integer attributeSetId) {
    this.attributeSetId = attributeSetId;
  }

  public String getPrice() {
    return price.toString();
  }

  public void setPrice(String price) {
    this.price = (Object) price;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getVisibility() {
    return visibility;
  }

  public void setVisibility(Integer visibility) {
    this.visibility = visibility;
  }

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public ExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public List<Object> getProductLinks() {
    return productLinks;
  }

  public void setProductLinks(List<Object> productLinks) {
    this.productLinks = productLinks;
  }

  public List<Object> getOptions() {
    return options;
  }

  public void setOptions(List<Object> options) {
    this.options = options;
  }

  public List<MediaGalleryEntry> getMediaGalleryEntries() {
    return mediaGalleryEntries;
  }

  public void setMediaGalleryEntries(List<MediaGalleryEntry> mediaGalleryEntries) {
    this.mediaGalleryEntries = mediaGalleryEntries;
  }

  public List<Object> getTierPrices() {
    return tierPrices;
  }

  public void setTierPrices(List<Object> tierPrices) {
    this.tierPrices = tierPrices;
  }

  public List<CustomAttribute> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(List<CustomAttribute> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public void setFlashSales(boolean flashSales) {
    this.flashSales = flashSales;
  }

  public boolean isFlashSales() {
    return flashSales;
  }

  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  public String getDiscountType() {
    return discountType;
  }

  public void setDiscountPercentage(String discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public String getDiscountPercentage() {
    return discountPercentage;
  }

  public void setSpecialPrice(String specialPrice) {
    this.specialPrice = specialPrice;
  }

  public String getSpecialPrice() {
    return specialPrice;
  }

  public void setDealFromDate(String dealFromDate) {
    this.dealFromDate = dealFromDate;
  }

  public String getDealFromDate() {
    return dealFromDate;
  }

  public void setDealToDate(String dealToDate) {
    this.dealToDate = dealToDate;
  }

  public void setCurrentServerTime(String currentServerTime) {
    this.currentServerTime = currentServerTime;
  }

  public boolean isNew() {
    return isNew;
  }

  public void setWholeSale(boolean wholeSale) {
    this.isWholeSale = wholeSale;
  }

  public boolean isWholeSale() {
    return isWholeSale;
  }

  public void setNew(boolean isNew) {
    this.isNew = isNew;
  }
}
