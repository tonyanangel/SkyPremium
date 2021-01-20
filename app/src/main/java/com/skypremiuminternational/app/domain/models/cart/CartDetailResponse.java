package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailResponse implements Serializable {
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("is_active")
  @Expose
  private Boolean isActive;
  @SerializedName("is_virtual")
  @Expose
  private Boolean isVirtual;
  @SerializedName("items")
  @Expose
  private List<CartDetailItem> items = null;
  @SerializedName("items_count")
  @Expose
  private int itemsCount;
  @SerializedName("items_qty")
  @Expose
  private int itemsQty;
  @SerializedName("customer")
  @Expose
  private CartDetailCustomer customer;
  @SerializedName("billing_address")
  @Expose
  private CartDetailBillingAddress billingAddress;
  @SerializedName("orig_order_id")
  @Expose
  private int origOrderId;
  @SerializedName("currency")
  @Expose
  private CartDetailCurrency currency;
  @SerializedName("customer_is_guest")
  @Expose
  private Boolean customerIsGuest;
  @SerializedName("customer_note_notify")
  @Expose
  private Boolean customerNoteNotify;
  @SerializedName("customer_tax_class_id")
  @Expose
  private int customerTaxClassId;
  @SerializedName("store_id")
  @Expose
  private int storeId;
  @SerializedName("extension_attributes")
  @Expose
  private CartDetailExtensionAttributes
      extensionAttributes;
  private boolean containVirtualProduct;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Boolean getVirtual() {
    return isVirtual;
  }

  public void setVirtual(Boolean virtual) {
    isVirtual = virtual;
  }

  public List<CartDetailItem> getItems() {
    return items;
  }

  public void setItems(List<CartDetailItem> items) {
    this.items = items;
  }

  public int getItemsCount() {
    return itemsCount;
  }

  public void setItemsCount(int itemsCount) {
    this.itemsCount = itemsCount;
  }

  public int getItemsQty() {
    return itemsQty;
  }

  public void setItemsQty(int itemsQty) {
    this.itemsQty = itemsQty;
  }

  public CartDetailCustomer getCustomer() {
    return customer;
  }

  public void setCustomer(CartDetailCustomer customer) {
    this.customer = customer;
  }

  public CartDetailBillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(CartDetailBillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public int getOrigOrderId() {
    return origOrderId;
  }

  public void setOrigOrderId(int origOrderId) {
    this.origOrderId = origOrderId;
  }

  public CartDetailCurrency getCurrency() {
    return currency;
  }

  public void setCurrency(CartDetailCurrency currency) {
    this.currency = currency;
  }

  public Boolean getCustomerIsGuest() {
    return customerIsGuest;
  }

  public void setCustomerIsGuest(Boolean customerIsGuest) {
    this.customerIsGuest = customerIsGuest;
  }

  public Boolean getCustomerNoteNotify() {
    return customerNoteNotify;
  }

  public void setCustomerNoteNotify(Boolean customerNoteNotify) {
    this.customerNoteNotify = customerNoteNotify;
  }

  public int getCustomerTaxClassId() {
    return customerTaxClassId;
  }

  public void setCustomerTaxClassId(int customerTaxClassId) {
    this.customerTaxClassId = customerTaxClassId;
  }

  public int getStoreId() {
    return storeId;
  }

  public void setStoreId(int storeId) {
    this.storeId = storeId;
  }

  public CartDetailExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(CartDetailExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public void setContainVirtualProduct(boolean containVirtualProduct) {
    this.containVirtualProduct = containVirtualProduct;
  }

  public boolean isContainVirtualProduct() {
    return containVirtualProduct;
  }
}
