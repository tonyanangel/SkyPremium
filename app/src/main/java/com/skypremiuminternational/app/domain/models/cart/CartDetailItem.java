package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.Nullable;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailItem {

  private String imageUrl;
  private String category;
  @SerializedName("item_id")
  @Expose
  private int itemId;
  @SerializedName("sku")
  @Expose
  private String sku;
  @SerializedName("qty")
  @Expose
  private int qty;

  @SerializedName("qty_in_stock")
  @Expose
  private int qty_stock;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("price")
  @Expose
  private String price;
  @SerializedName("product_type")
  @Expose
  private String productType;
  @SerializedName("quote_id")
  @Expose
  private String quoteId;
  @SerializedName("loyalty_value_to_earn")
  @Expose
  private String loyaltyValue;

  public ExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  @SerializedName("extension_attributes")
  @Expose
  private ExtensionAttributes extensionAttributes;

  private boolean isQtyEditable = true;

  private String errorMessage = null;

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public int getQty_stock() {
    return qty_stock;
  }

  public void setQty_stock(int qty_stock) {
    this.qty_stock = qty_stock;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getQuoteId() {
    return quoteId;
  }

  public void setQuoteId(String quoteId) {
    this.quoteId = quoteId;
  }

  public boolean isQtyEditable() {
    return isQtyEditable;
  }

  public void setQtyEditable(boolean qtyEditable) {
    isQtyEditable = qtyEditable;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLoyaltyValue() {
    return loyaltyValue;
  }

  public void setLoyaltyValue(String loyaltyValue) {
    this.loyaltyValue = loyaltyValue;
  }
}
