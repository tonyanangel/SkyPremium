package com.skypremiuminternational.app.domain.models.wellness;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.ExtensionAttibutes;
import com.skypremiuminternational.app.domain.models.details.ExtensionAttributes;

import java.io.Serializable;
import java.util.List;

public class ItemsItem implements Serializable {

  @SerializedName("visibility")
  private int visibility;
  @SerializedName("type_id")
  private String typeId;
  @SerializedName("created_at")
  private String createdAt;
  @SerializedName("extension_attributes")
  private ExtensionAttributes extensionAttributes;
  @SerializedName("tier_prices")
  private List<Object> tierPrices;
  @SerializedName("custom_attributes")
  private List<CustomAttributesItem> customAttributes;
  @SerializedName("attribute_set_id")
  private int attributeSetId;
  @SerializedName("updated_at")
  private String updatedAt;
  @SerializedName("price")
  private String price;
  @SerializedName("name")
  private String name;
  @SerializedName("id")
  private int id;
  @SerializedName("sku")
  private String sku;
  @SerializedName("product_links")
  private List<Object> productLinks;
  @SerializedName("flash_sale")
  private boolean isFlashSales;
  @SerializedName("status")
  private int status;
  private boolean dealStatus;
  private String discountType;
  private String discountPercentage;
  private String specialPrice;
  private String specialFromDate;
  private String specialToDate;
  private String minimalprice;
  private String dealFromDate;
  private String dealToDate;
  private boolean isFavourite;
  private String categoryName;
  private String pillarId;
  private String currentServerTime;
  private boolean searchHomeProduct;
  private String pillarName;
  private boolean isNew;
  private boolean isWholeSale;
  private String reserveButtonTitle;
  private String reserveButtonLink;

  public String getReserveButtonTitle() {
    return reserveButtonTitle;
  }

  public void setReserveButtonTitle(String reserveButtonTitle) {
    this.reserveButtonTitle = reserveButtonTitle;
  }

  public String getReserveButtonLink() {
    return reserveButtonLink;
  }

  public void setReserveButtonLink(String reserveButtonLink) {
    this.reserveButtonLink = reserveButtonLink;
  }

  public String getSpecialFromDate() {
    return specialFromDate;
  }

  public void setSpecialFromDate(String specialFromDate) {
    this.specialFromDate = specialFromDate;
  }

  public String getSpecialToDate() {
    return specialToDate;
  }

  public void setSpecialToDate(String specialToDate) {
    this.specialToDate = specialToDate;
  }

  public String getDealValue() {
    return dealValue;
  }

  public void setDealValue(String dealValue) {
    this.dealValue = dealValue;
  }

  private String dealValue;

  public String getCurrentServerTime() {
    return currentServerTime;
  }

  public void setCurrentServerTime(String currentServerTime) {
    this.currentServerTime = currentServerTime;
  }

  public int getVisibility() {
    return visibility;
  }

  public void setVisibility(int visibility) {
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

  public ExtensionAttributes getExtensionAttributes() {

      return extensionAttributes;

  }

  public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public List<Object> getTierPrices() {
    return tierPrices;
  }

  public void setTierPrices(List<Object> tierPrices) {
    this.tierPrices = tierPrices;
  }

  public List<CustomAttributesItem> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(List<CustomAttributesItem> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public int getAttributeSetId() {
    return attributeSetId;
  }

  public void setAttributeSetId(int attributeSetId) {
    this.attributeSetId = attributeSetId;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public List<Object> getProductLinks() {
    return productLinks;
  }

  public void setProductLinks(List<Object> productLinks) {
    this.productLinks = productLinks;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "ItemsItem{"
        + "visibility = '"
        + visibility
        + '\''
        + ",type_id = '"
        + typeId
        + '\''
        + ",created_at = '"
        + createdAt
        + '\''
        + ",extension_attributes = '"
        + extensionAttributes
        + '\''
        + ",tier_prices = '"
        + tierPrices
        + '\''
        + ",custom_attributes = '"
        + customAttributes
        + '\''
        + ",attribute_set_id = '"
        + attributeSetId
        + '\''
        + ",updated_at = '"
        + updatedAt
        + '\''
        + ",price = '"
        + price
        + '\''
        + ",name = '"
        + name
        + '\''
        + ",id = '"
        + id
        + '\''
        + ",sku = '"
        + sku
        + '\''
        + ",product_links = '"
        + productLinks
        + '\''
        + ",status = '"
        + status
        + '\''
        + "}";
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getPillarId() {
    return pillarId;
  }

  public void setPillarId(String pillarId) {
    this.pillarId = pillarId;
  }

  public boolean isFavourite() {
    return isFavourite;
  }

  public void setFavourite(boolean favourite) {
    isFavourite = favourite;
  }

  public boolean isFlashSales() {
    return isFlashSales;
  }

  public void setFlashSales(boolean isFlashSales) {
    this.isFlashSales = isFlashSales;
  }

  public boolean getDealStatus() {
    return dealStatus;
  }

  public void setDealStatus(boolean dealStatus) {
    this.dealStatus = dealStatus;
  }

  public String getDiscountType() {
    return discountType;
  }

  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  public String getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(String discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public String getMinimalprice() {
    return specialPrice;
  }

  public void setMinimalprice(String minimalprice) {
    this.minimalprice = minimalprice;
  }

  public String getSpecialPrice() {
    return specialPrice;
  }

  public void setSpecialPrice(String dealValue) {
    this.specialPrice = dealValue;
  }

  public String getDealToDate() {
    return dealToDate;
  }

  public void setDealToDate(String dealToDate) {
    this.dealToDate = dealToDate;
  }

  public String getDealFromDate() {
    return dealFromDate;
  }

  public void setDealFromDate(String dealFromDate) {
    this.dealFromDate = dealFromDate;
  }

  public boolean isSearchHomeProduct() {
    return searchHomeProduct;
  }

  public void setSearchHomeProduct(boolean searchHomeProduct) {
    this.searchHomeProduct = searchHomeProduct;
  }

  public String getPillarName() {
    return pillarName;
  }

  public void setPillarName(String pillarName) {
    this.pillarName = pillarName;
  }

  public boolean isNew() {
    return isNew;
  }

  public void setNew(boolean isNew) {
    this.isNew = isNew;
  }

  public void setWholeSale(boolean wholeSale) {
    this.isWholeSale = wholeSale;
  }

  public boolean isWholeSale() {
    return isWholeSale;
  }
}