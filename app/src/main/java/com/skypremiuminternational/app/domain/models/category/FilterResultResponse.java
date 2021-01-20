package com.skypremiuminternational.app.domain.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.StockItem;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;

import java.util.List;

public class FilterResultResponse {
  
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("sku")
  @Expose
  private Integer sku;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("attribute_set_id")
  @Expose
  private Integer attribute_set_id;
  @SerializedName("price")
  @Expose
  private Integer price;
  @SerializedName("status")
  @Expose
  private Integer status;
  @SerializedName("type_id")
  @Expose
  private String type_id;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("extension_attributes")
  @Expose
  private ExtensionAttributes extensionAttributes;
  @SerializedName("product_links")
  @Expose
  private List<ProductLink> productLinks;
  @SerializedName("tier_prices")
  @Expose
  private List<TierPrice> tierPrices;
  @SerializedName("custom_attributes")
  @Expose
  private List<CustomAttribute> customAttributes;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getSku() {
    return sku;
  }
  
  public void setSku(Integer sku) {
    this.sku = sku;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Integer getAttribute_set_id() {
    return attribute_set_id;
  }
  
  public void setAttribute_set_id(Integer attribute_set_id) {
    this.attribute_set_id = attribute_set_id;
  }
  
  public Integer getPrice() {
    return price;
  }
  
  public void setPrice(Integer price) {
    this.price = price;
  }
  
  public Integer getStatus() {
    return status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getType_id() {
    return type_id;
  }
  
  public void setType_id(String type_id) {
    this.type_id = type_id;
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
  
  public List<ProductLink> getProductLinks() {
    return productLinks;
  }
  
  public void setProductLinks(List<ProductLink> productLinks) {
    this.productLinks = productLinks;
  }
  
  public List<TierPrice> getTierPrices() {
    return tierPrices;
  }
  
  public void setTierPrices(List<TierPrice> tierPrices) {
    this.tierPrices = tierPrices;
  }
  
  public List<CustomAttribute> getCustomAttributes() {
    return customAttributes;
  }
  
  public void setCustomAttributes(List<CustomAttribute> customAttributes) {
    this.customAttributes = customAttributes;
  }
}
