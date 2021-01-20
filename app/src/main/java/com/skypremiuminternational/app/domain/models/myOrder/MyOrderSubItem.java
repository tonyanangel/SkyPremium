package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class MyOrderSubItem implements Serializable {
  @SerializedName("amount_refunded")
  @Expose
  private String amountRefunded;
  @SerializedName("base_amount_refunded")
  @Expose
  private String baseAmountRefunded;
  @SerializedName("base_discount_amount")
  @Expose
  private String baseDiscountAmount;
  @SerializedName("base_discount_invoiced")
  @Expose
  private String baseDiscountInvoiced;
  @SerializedName("base_discount_tax_compensation_amount")
  @Expose
  private String baseDiscountTaxCompensationAmount;
  @SerializedName("base_original_price")
  @Expose
  private String baseOriginalPrice;
  @SerializedName("base_price")
  @Expose
  private String basePrice;
  @SerializedName("base_price_incl_tax")
  @Expose
  private String basePriceInclTax;
  @SerializedName("base_row_invoiced")
  @Expose
  private String baseRowInvoiced;
  @SerializedName("base_row_total")
  @Expose
  private String baseRowTotal;
  @SerializedName("base_row_total_incl_tax")
  @Expose
  private String baseRowTotalInclTax;
  @SerializedName("base_tax_amount")
  @Expose
  private String baseTaxAmount;
  @SerializedName("base_tax_invoiced")
  @Expose
  private String baseTaxInvoiced;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("discount_amount")
  @Expose
  private String discountAmount;
  @SerializedName("discount_invoiced")
  @Expose
  private String discountInvoiced;
  @SerializedName("discount_percent")
  @Expose
  private String discountPercent;
  @SerializedName("free_shipping")
  @Expose
  private String freeShipping;
  @SerializedName("discount_tax_compensation_amount")
  @Expose
  private String discountTaxCompensationAmount;
  @SerializedName("is_qty_decimal")
  @Expose
  private String isQtyDecimal;
  @SerializedName("is_virtual")
  @Expose
  private String isVirtual;
  @SerializedName("item_id")
  @Expose
  private String itemId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("loyalty_value_to_earn")
  @Expose
  private String loyaltyValue;
  @SerializedName("no_discount")
  @Expose
  private String noDiscount;
  @SerializedName("order_id")
  @Expose
  private String orderId;
  @SerializedName("original_price")
  @Expose
  private String originalPrice;
  @SerializedName("price")
  @Expose
  private String price;
  @SerializedName("price_incl_tax")
  @Expose
  private String priceInclTax;
  @SerializedName("product_id")
  @Expose
  private String productId;
  @SerializedName("product_type")
  @Expose
  private String productType;
  @SerializedName("qty_canceled")
  @Expose
  private String qtyCanceled;
  @SerializedName("qty_invoiced")
  @Expose
  private String qtyInvoiced;
  @SerializedName("qty_ordered")
  @Expose
  private String qtyOrdered;
  @SerializedName("qty_refunded")
  @Expose
  private String qtyRefunded;
  @SerializedName("qty_returned")
  @Expose
  private String qtyReturned;
  @SerializedName("qty_shipped")
  @Expose
  private String qtyShipped;
  @SerializedName("quote_item_id")
  @Expose
  private String quoteItemId;
  @SerializedName("row_invoiced")
  @Expose
  private String rowInvoiced;
  @SerializedName("row_total")
  @Expose
  private String rowTotal;
  @SerializedName("row_total_incl_tax")
  @Expose
  private String rowTotalInclTax;
  @SerializedName("row_weight")
  @Expose
  private String rowWeight;
  @SerializedName("sku")
  @Expose
  private String sku;
  @SerializedName("store_id")
  @Expose
  private String storeId;
  @SerializedName("tax_amount")
  @Expose
  private String taxAmount;
  @SerializedName("tax_invoiced")
  @Expose
  private String taxInvoiced;
  @SerializedName("tax_percent")
  @Expose
  private String taxPercent;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("applied_rule_ids")
  @Expose
  private String appliedRuleIds;
  @SerializedName("base_discount_tax_compensation_invoiced")
  @Expose
  private String baseDiscountTaxCompensationInvoiced;
  @SerializedName("discount_tax_compensation_invoiced")
  @Expose
  private String discountTaxCompensationInvoiced;
  @SerializedName("weee_tax_applied")
  @Expose
  private String weeeTaxApplied;

  public String getAmountRefunded() {
    return amountRefunded;
  }

  public void setAmountRefunded(String amountRefunded) {
    this.amountRefunded = amountRefunded;
  }

  public String getBaseAmountRefunded() {
    return baseAmountRefunded;
  }

  public void setBaseAmountRefunded(String baseAmountRefunded) {
    this.baseAmountRefunded = baseAmountRefunded;
  }

  public String getBaseDiscountAmount() {
    return baseDiscountAmount;
  }

  public void setBaseDiscountAmount(String baseDiscountAmount) {
    this.baseDiscountAmount = baseDiscountAmount;
  }

  public String getBaseDiscountInvoiced() {
    return baseDiscountInvoiced;
  }

  public void setBaseDiscountInvoiced(String baseDiscountInvoiced) {
    this.baseDiscountInvoiced = baseDiscountInvoiced;
  }

  public String getBaseDiscountTaxCompensationAmount() {
    return baseDiscountTaxCompensationAmount;
  }

  public void setBaseDiscountTaxCompensationAmount(String baseDiscountTaxCompensationAmount) {
    this.baseDiscountTaxCompensationAmount = baseDiscountTaxCompensationAmount;
  }

  public String getBaseOriginalPrice() {
    return baseOriginalPrice;
  }

  public void setBaseOriginalPrice(String baseOriginalPrice) {
    this.baseOriginalPrice = baseOriginalPrice;
  }

  public String getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(String basePrice) {
    this.basePrice = basePrice;
  }

  public String getBasePriceInclTax() {
    return basePriceInclTax;
  }

  public void setBasePriceInclTax(String basePriceInclTax) {
    this.basePriceInclTax = basePriceInclTax;
  }

  public String getBaseRowInvoiced() {
    return baseRowInvoiced;
  }

  public void setBaseRowInvoiced(String baseRowInvoiced) {
    this.baseRowInvoiced = baseRowInvoiced;
  }

  public String getBaseRowTotal() {
    return baseRowTotal;
  }

  public void setBaseRowTotal(String baseRowTotal) {
    this.baseRowTotal = baseRowTotal;
  }

  public String getBaseRowTotalInclTax() {
    return baseRowTotalInclTax;
  }

  public void setBaseRowTotalInclTax(String baseRowTotalInclTax) {
    this.baseRowTotalInclTax = baseRowTotalInclTax;
  }

  public String getBaseTaxAmount() {
    return baseTaxAmount;
  }

  public void setBaseTaxAmount(String baseTaxAmount) {
    this.baseTaxAmount = baseTaxAmount;
  }

  public String getBaseTaxInvoiced() {
    return baseTaxInvoiced;
  }

  public void setBaseTaxInvoiced(String baseTaxInvoiced) {
    this.baseTaxInvoiced = baseTaxInvoiced;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(String discountAmount) {
    this.discountAmount = discountAmount;
  }

  public String getDiscountInvoiced() {
    return discountInvoiced;
  }

  public void setDiscountInvoiced(String discountInvoiced) {
    this.discountInvoiced = discountInvoiced;
  }

  public String getDiscountPercent() {
    return discountPercent;
  }

  public void setDiscountPercent(String discountPercent) {
    this.discountPercent = discountPercent;
  }

  public String getFreeShipping() {
    return freeShipping;
  }

  public void setFreeShipping(String freeShipping) {
    this.freeShipping = freeShipping;
  }

  public String getDiscountTaxCompensationAmount() {
    return discountTaxCompensationAmount;
  }

  public void setDiscountTaxCompensationAmount(String discountTaxCompensationAmount) {
    this.discountTaxCompensationAmount = discountTaxCompensationAmount;
  }

  public String getIsQtyDecimal() {
    return isQtyDecimal;
  }

  public void setIsQtyDecimal(String isQtyDecimal) {
    this.isQtyDecimal = isQtyDecimal;
  }

  public String getIsVirtual() {
    return isVirtual;
  }

  public void setIsVirtual(String isVirtual) {
    this.isVirtual = isVirtual;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNoDiscount() {
    return noDiscount;
  }

  public void setNoDiscount(String noDiscount) {
    this.noDiscount = noDiscount;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(String originalPrice) {
    this.originalPrice = originalPrice;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getPriceInclTax() {
    return priceInclTax;
  }

  public void setPriceInclTax(String priceInclTax) {
    this.priceInclTax = priceInclTax;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getQtyCanceled() {
    return qtyCanceled;
  }

  public void setQtyCanceled(String qtyCanceled) {
    this.qtyCanceled = qtyCanceled;
  }

  public String getQtyInvoiced() {
    return qtyInvoiced;
  }

  public void setQtyInvoiced(String qtyInvoiced) {
    this.qtyInvoiced = qtyInvoiced;
  }

  public String getQtyOrdered() {
    return qtyOrdered;
  }

  public void setQtyOrdered(String qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public String getQtyRefunded() {
    return qtyRefunded;
  }

  public void setQtyRefunded(String qtyRefunded) {
    this.qtyRefunded = qtyRefunded;
  }

  public String getQtyReturned() {
    return qtyReturned;
  }

  public void setQtyReturned(String qtyReturned) {
    this.qtyReturned = qtyReturned;
  }

  public String getQtyShipped() {
    return qtyShipped;
  }

  public void setQtyShipped(String qtyShipped) {
    this.qtyShipped = qtyShipped;
  }

  public String getQuoteItemId() {
    return quoteItemId;
  }

  public void setQuoteItemId(String quoteItemId) {
    this.quoteItemId = quoteItemId;
  }

  public String getRowInvoiced() {
    return rowInvoiced;
  }

  public void setRowInvoiced(String rowInvoiced) {
    this.rowInvoiced = rowInvoiced;
  }

  public String getRowTotal() {
    return rowTotal;
  }

  public void setRowTotal(String rowTotal) {
    this.rowTotal = rowTotal;
  }

  public String getRowTotalInclTax() {
    return rowTotalInclTax;
  }

  public void setRowTotalInclTax(String rowTotalInclTax) {
    this.rowTotalInclTax = rowTotalInclTax;
  }

  public String getRowWeight() {
    return rowWeight;
  }

  public void setRowWeight(String rowWeight) {
    this.rowWeight = rowWeight;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getTaxInvoiced() {
    return taxInvoiced;
  }

  public void setTaxInvoiced(String taxInvoiced) {
    this.taxInvoiced = taxInvoiced;
  }

  public String getTaxPercent() {
    return taxPercent;
  }

  public void setTaxPercent(String taxPercent) {
    this.taxPercent = taxPercent;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getAppliedRuleIds() {
    return appliedRuleIds;
  }

  public void setAppliedRuleIds(String appliedRuleIds) {
    this.appliedRuleIds = appliedRuleIds;
  }

  public String getBaseDiscountTaxCompensationInvoiced() {
    return baseDiscountTaxCompensationInvoiced;
  }

  public void setBaseDiscountTaxCompensationInvoiced(String baseDiscountTaxCompensationInvoiced) {
    this.baseDiscountTaxCompensationInvoiced = baseDiscountTaxCompensationInvoiced;
  }

  public String getDiscountTaxCompensationInvoiced() {
    return discountTaxCompensationInvoiced;
  }

  public void setDiscountTaxCompensationInvoiced(String discountTaxCompensationInvoiced) {
    this.discountTaxCompensationInvoiced = discountTaxCompensationInvoiced;
  }

  public String getWeeeTaxApplied() {
    return weeeTaxApplied;
  }

  public void setWeeeTaxApplied(String weeeTaxApplied) {
    this.weeeTaxApplied = weeeTaxApplied;
  }

  public String getLoyaltyValue() {
    return loyaltyValue;
  }

  public void setLoyaltyValue(String loyaltyValue) {
    this.loyaltyValue = loyaltyValue;
  }
}
