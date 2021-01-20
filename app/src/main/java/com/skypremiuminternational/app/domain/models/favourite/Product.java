package com.skypremiuminternational.app.domain.models.favourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.ExtensionAttibutes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 1/24/18.
 */

public class Product implements Serializable {
  @SerializedName("row_id")
  @Expose
  private String rowId;
  @SerializedName("entity_id")
  @Expose
  private String entityId;
  @SerializedName("created_in")
  @Expose
  private String createdIn;
  @SerializedName("updated_in")
  @Expose
  private String updatedIn;
  @SerializedName("attribute_set_id")
  @Expose
  private String attributeSetId;
  @SerializedName("type_id")
  @Expose
  private String typeId;
  @SerializedName("sku")
  @Expose
  private String sku;
  @SerializedName("has_options")
  @Expose
  private String hasOptions;
  @SerializedName("required_options")
  @Expose
  private String requiredOptions;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("price")
  @Expose
  private String price;
  @SerializedName("special_price")
  @Expose
  private String specialPrice;
  @SerializedName("special_from_date")
  @Expose
  private String specialFromDate;
  @SerializedName("special_to_date")
  @Expose
  private String specialToDate;
  @SerializedName("tax_class_id")
  @Expose
  private String taxClassId;
  @SerializedName("final_price")
  @Expose
  private Object finalPrice;
  @SerializedName("minimal_price")
  @Expose
  private String minimalPrice;
  @SerializedName("min_price")
  @Expose
  private String minPrice;
  @SerializedName("max_price")
  @Expose
  private String maxPrice;
  @SerializedName("tier_price")
  @Expose
  private Object tierPrice;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("small_image")
  @Expose
  private String smallImage;
  @SerializedName("thumbnail")
  @Expose
  private String thumbnail;
  @SerializedName("popularity_order")
  @Expose
  private String popularityOrder;
  @SerializedName("visibility")
  @Expose
  private String visibility;
  @SerializedName("partners_benefits")
  @Expose
  private String partnersBenefits;
  @SerializedName("partners_terms_conditions")
  @Expose
  private String partnersTermsConditions;
  @SerializedName("current_server_time")
  @Expose
  private String currentServerTime;
  @SerializedName("deal_discount_type")
  @Expose
  private String dealDiscountType;
  @SerializedName("deal_discount_percentage")
  @Expose
  private String dealDiscountPercentage;
  @SerializedName("deal_value")
  @Expose
  private String dealValue;
  @SerializedName("deal_to_date")
  @Expose
  private String dealToDate;
  @SerializedName("deal_from_date")
  @Expose
  private String dealFromDate;
  @SerializedName("deal_status")
  @Expose
  private String dealStatus;

  @SerializedName("news_from_date")
  @Expose
  private String newsFromDate;
  @SerializedName("news_to_date")
  @Expose
  private String newsToDate;
  @SerializedName("flash_sales")
  @Expose
  private boolean flashSales;
  @SerializedName("request_path")
  @Expose
  private String requestPath;
  @SerializedName("loyalty_value_to_earn")
  @Expose
  private String loyaltyValueToEarn;
  @SerializedName("category_ids")
  @Expose
  private List<String> categoryIds = null;
  @SerializedName("is_in_stock")
  @Expose
  private boolean isInStock;


  public boolean isInStock() {
    return isInStock;
  }

  public void setInStock(boolean inStock) {
    isInStock = inStock;
  }

  public String getLoyaltyValueToEarn() {
    return loyaltyValueToEarn;
  }

  public void setLoyaltyValueToEarn(String loyaltyValueToEarn) {
    this.loyaltyValueToEarn = loyaltyValueToEarn;
  }

  public String getCurrentServerTime() {
    return currentServerTime;
  }

  public void setCurrentServerTime(String currentServerTime) {
    this.currentServerTime = currentServerTime;
  }

  public String getDealDiscountType() {
    return dealDiscountType;
  }

  public void setDealDiscountType(String dealDiscountType) {
    this.dealDiscountType = dealDiscountType;
  }

  public String getDealDiscountPercentage() {
    return dealDiscountPercentage;
  }

  public void setDealDiscountPercentage(String dealDiscountPercentage) {
    this.dealDiscountPercentage = dealDiscountPercentage;
  }

  public String getDealValue() {
    return dealValue;
  }

  public void setDealValue(String dealValue) {
    this.dealValue = dealValue;
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

  public String getNewsFromDate() {
    return newsFromDate;
  }

  public void setNewsFromDate(String newsFromDate) {
    this.newsFromDate = newsFromDate;
  }

  public String getNewsToDate() {
    return newsToDate;
  }

  public void setNewsToDate(String newsToDate) {
    this.newsToDate = newsToDate;
  }

  public String getDealStatus() {
    return dealStatus;
  }

  public void setDealStatus(String dealStatus) {
    this.dealStatus = dealStatus;
  }

  public boolean isFlashSales() {
    return flashSales;
  }

  public void setFlashSales(boolean flashSales) {
    this.flashSales = flashSales;
  }

  public String getSpecialPrice() {
    return specialPrice;
  }

  public void setSpecialPrice(String specialPrice) {
    this.specialPrice = specialPrice;
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

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getCreatedIn() {
    return createdIn;
  }

  public void setCreatedIn(String createdIn) {
    this.createdIn = createdIn;
  }

  public String getUpdatedIn() {
    return updatedIn;
  }

  public void setUpdatedIn(String updatedIn) {
    this.updatedIn = updatedIn;
  }

  public String getAttributeSetId() {
    return attributeSetId;
  }

  public void setAttributeSetId(String attributeSetId) {
    this.attributeSetId = attributeSetId;
  }

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getHasOptions() {
    return hasOptions;
  }

  public void setHasOptions(String hasOptions) {
    this.hasOptions = hasOptions;
  }

  public String getRequiredOptions() {
    return requiredOptions;
  }

  public void setRequiredOptions(String requiredOptions) {
    this.requiredOptions = requiredOptions;
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

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getTaxClassId() {
    return taxClassId;
  }

  public void setTaxClassId(String taxClassId) {
    this.taxClassId = taxClassId;
  }

  public Object getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(Object finalPrice) {
    this.finalPrice = finalPrice;
  }

  public String getMinimalPrice() {
    return minimalPrice;
  }

  public void setMinimalPrice(String minimalPrice) {
    this.minimalPrice = minimalPrice;
  }

  public String getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(String minPrice) {
    this.minPrice = minPrice;
  }

  public String getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(String maxPrice) {
    this.maxPrice = maxPrice;
  }

  public Object getTierPrice() {
    return tierPrice;
  }

  public void setTierPrice(Object tierPrice) {
    this.tierPrice = tierPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSmallImage() {
    return smallImage;
  }

  public void setSmallImage(String smallImage) {
    this.smallImage = smallImage;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getPopularityOrder() {
    return popularityOrder;
  }

  public void setPopularityOrder(String popularityOrder) {
    this.popularityOrder = popularityOrder;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getPartnersBenefits() {
    return partnersBenefits;
  }

  public void setPartnersBenefits(String partnersBenefits) {
    this.partnersBenefits = partnersBenefits;
  }

  public String getPartnersTermsConditions() {
    return partnersTermsConditions;
  }

  public void setPartnersTermsConditions(String partnersTermsConditions) {
    this.partnersTermsConditions = partnersTermsConditions;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public void setRequestPath(String requestPath) {
    this.requestPath = requestPath;
  }

  public List<String> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<String> categoryIds) {
    this.categoryIds = categoryIds;
  }
}
