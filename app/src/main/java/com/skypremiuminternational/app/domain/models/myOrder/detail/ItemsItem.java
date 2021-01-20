package com.skypremiuminternational.app.domain.models.myOrder.detail;

import com.google.gson.annotations.SerializedName;

public class ItemsItem {

  @SerializedName("tax_amount")
  private String taxAmount;

  @SerializedName("tax_invoiced")
  private String taxInvoiced;

  @SerializedName("original_price")
  private String originalPrice;

  @SerializedName("free_shipping")
  private String freeShipping;

  @SerializedName("base_discount_tax_compensation_invoiced")
  private String baseDiscountTaxCompensationInvoiced;

  @SerializedName("discount_amount")
  private String discountAmount;

  @SerializedName("tax_percent")
  private String taxPercent;

  @SerializedName("price_incl_tax")
  private String priceInclTax;

  @SerializedName("price")
  private String price;

  @SerializedName("product_id")
  private String productId;

  @SerializedName("base_row_total")
  private String baseRowTotal;

  @SerializedName("sku")
  private String sku;

  @SerializedName("discount_tax_compensation_amount")
  private String discountTaxCompensationAmount;

  @SerializedName("base_original_price")
  private String baseOriginalPrice;

  @SerializedName("quote_item_id")
  private String quoteItemId;

  @SerializedName("row_weight")
  private String rowWeight;

  @SerializedName("base_amount_refunded")
  private String baseAmountRefunded;

  @SerializedName("base_price_incl_tax")
  private String basePriceInclTax;

  @SerializedName("no_discount")
  private String noDiscount;

  @SerializedName("name")
  private String name;

  @SerializedName("base_discount_invoiced")
  private String baseDiscountInvoiced;

  @SerializedName("discount_percent")
  private String discountPercent;

  @SerializedName("order_id")
  private String orderId;

  @SerializedName("is_virtual")
  private String isVirtual;

  @SerializedName("discount_tax_compensation_invoiced")
  private String discountTaxCompensationInvoiced;

  @SerializedName("created_at")
  private String createdAt;

  @SerializedName("qty_shipped")
  private String qtyShipped;

  @SerializedName("qty_ordered")
  private int qtyOrdered;

  @SerializedName("row_total")
  private String rowTotal;

  @SerializedName("qty_canceled")
  private String qtyCanceled;

  @SerializedName("amount_refunded")
  private String amountRefunded;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("weee_tax_applied")
  private String weeeTaxApplied;

  @SerializedName("base_price")
  private String basePrice;

  @SerializedName("qty_invoiced")
  private String qtyInvoiced;

  @SerializedName("row_invoiced")
  private String rowInvoiced;

  @SerializedName("row_total_incl_tax")
  private String rowTotalInclTax;

  @SerializedName("base_tax_amount")
  private String baseTaxAmount;

  @SerializedName("store_id")
  private String storeId;

  @SerializedName("qty_returned")
  private String qtyReturned;

  @SerializedName("item_id")
  private String itemId;

  @SerializedName("base_discount_amount")
  private String baseDiscountAmount;

  @SerializedName("base_row_total_incl_tax")
  private String baseRowTotalInclTax;

  @SerializedName("base_discount_tax_compensation_amount")
  private String baseDiscountTaxCompensationAmount;

  @SerializedName("product_type")
  private String productType;

  @SerializedName("qty_refunded")
  private String qtyRefunded;

  @SerializedName("base_row_invoiced")
  private String baseRowInvoiced;

  @SerializedName("is_qty_decimal")
  private String isQtyDecimal;

  @SerializedName("discount_invoiced")
  private String discountInvoiced;

  @SerializedName("loyalty_value_to_earn")
  private String loyaltyValue;

  @SerializedName("base_tax_invoiced")
  private String baseTaxInvoiced;

  private String reviewId;

  private String status;


  @SerializedName("extension_attributes")
  private ExtensionAttributes extensionAttributes;

  private String imageUrl;
  private String category;

  public ExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public void setTaxInvoiced(String taxInvoiced) {
    this.taxInvoiced = taxInvoiced;
  }

  public String getTaxInvoiced() {
    return taxInvoiced;
  }

  public void setOriginalPrice(String originalPrice) {
    this.originalPrice = originalPrice;
  }

  public String getOriginalPrice() {
    return originalPrice;
  }

  public void setFreeShipping(String freeShipping) {
    this.freeShipping = freeShipping;
  }

  public String getFreeShipping() {
    return freeShipping;
  }

  public void setBaseDiscountTaxCompensationInvoiced(String baseDiscountTaxCompensationInvoiced) {
    this.baseDiscountTaxCompensationInvoiced = baseDiscountTaxCompensationInvoiced;
  }

  public String getBaseDiscountTaxCompensationInvoiced() {
    return baseDiscountTaxCompensationInvoiced;
  }

  public void setDiscountAmount(String discountAmount) {
    this.discountAmount = discountAmount;
  }

  public String getDiscountAmount() {
    return discountAmount;
  }

  public void setTaxPercent(String taxPercent) {
    this.taxPercent = taxPercent;
  }

  public String getTaxPercent() {
    return taxPercent;
  }

  public void setPriceInclTax(String priceInclTax) {
    this.priceInclTax = priceInclTax;
  }

  public String getPriceInclTax() {
    return priceInclTax;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getPrice() {
    return price;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductId() {
    return productId;
  }

  public void setBaseRowTotal(String baseRowTotal) {
    this.baseRowTotal = baseRowTotal;
  }

  public String getBaseRowTotal() {
    return baseRowTotal;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getSku() {
    return sku;
  }

  public void setDiscountTaxCompensationAmount(String discountTaxCompensationAmount) {
    this.discountTaxCompensationAmount = discountTaxCompensationAmount;
  }

  public String getDiscountTaxCompensationAmount() {
    return discountTaxCompensationAmount;
  }

  public void setBaseOriginalPrice(String baseOriginalPrice) {
    this.baseOriginalPrice = baseOriginalPrice;
  }

  public String getBaseOriginalPrice() {
    return baseOriginalPrice;
  }

  public void setQuoteItemId(String quoteItemId) {
    this.quoteItemId = quoteItemId;
  }

  public String getQuoteItemId() {
    return quoteItemId;
  }

  public void setRowWeight(String rowWeight) {
    this.rowWeight = rowWeight;
  }

  public String getRowWeight() {
    return rowWeight;
  }

  public void setBaseAmountRefunded(String baseAmountRefunded) {
    this.baseAmountRefunded = baseAmountRefunded;
  }

  public String getBaseAmountRefunded() {
    return baseAmountRefunded;
  }

  public void setBasePriceInclTax(String basePriceInclTax) {
    this.basePriceInclTax = basePriceInclTax;
  }

  public String getBasePriceInclTax() {
    return basePriceInclTax;
  }

  public void setNoDiscount(String noDiscount) {
    this.noDiscount = noDiscount;
  }

  public String getNoDiscount() {
    return noDiscount;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setBaseDiscountInvoiced(String baseDiscountInvoiced) {
    this.baseDiscountInvoiced = baseDiscountInvoiced;
  }

  public String getBaseDiscountInvoiced() {
    return baseDiscountInvoiced;
  }

  public void setDiscountPercent(String discountPercent) {
    this.discountPercent = discountPercent;
  }

  public String getDiscountPercent() {
    return discountPercent;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setIsVirtual(String isVirtual) {
    this.isVirtual = isVirtual;
  }

  public String getIsVirtual() {
    return isVirtual;
  }

  public void setDiscountTaxCompensationInvoiced(String discountTaxCompensationInvoiced) {
    this.discountTaxCompensationInvoiced = discountTaxCompensationInvoiced;
  }

  public String getDiscountTaxCompensationInvoiced() {
    return discountTaxCompensationInvoiced;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setQtyShipped(String qtyShipped) {
    this.qtyShipped = qtyShipped;
  }

  public String getQtyShipped() {
    return qtyShipped;
  }

  public void setQtyOrdered(int qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public int getQtyOrdered() {
    return qtyOrdered;
  }

  public void setRowTotal(String rowTotal) {
    this.rowTotal = rowTotal;
  }

  public String getRowTotal() {
    return rowTotal;
  }

  public void setQtyCanceled(String qtyCanceled) {
    this.qtyCanceled = qtyCanceled;
  }

  public String getQtyCanceled() {
    return qtyCanceled;
  }

  public void setAmountRefunded(String amountRefunded) {
    this.amountRefunded = amountRefunded;
  }

  public String getAmountRefunded() {
    return amountRefunded;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setWeeeTaxApplied(String weeeTaxApplied) {
    this.weeeTaxApplied = weeeTaxApplied;
  }

  public String getWeeeTaxApplied() {
    return weeeTaxApplied;
  }

  public void setBasePrice(String basePrice) {
    this.basePrice = basePrice;
  }

  public String getBasePrice() {
    return basePrice;
  }

  public void setQtyInvoiced(String qtyInvoiced) {
    this.qtyInvoiced = qtyInvoiced;
  }

  public String getQtyInvoiced() {
    return qtyInvoiced;
  }

  public void setRowInvoiced(String rowInvoiced) {
    this.rowInvoiced = rowInvoiced;
  }

  public String getRowInvoiced() {
    return rowInvoiced;
  }

  public void setRowTotalInclTax(String rowTotalInclTax) {
    this.rowTotalInclTax = rowTotalInclTax;
  }

  public String getRowTotalInclTax() {
    return rowTotalInclTax;
  }

  public void setBaseTaxAmount(String baseTaxAmount) {
    this.baseTaxAmount = baseTaxAmount;
  }

  public String getBaseTaxAmount() {
    return baseTaxAmount;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setQtyReturned(String qtyReturned) {
    this.qtyReturned = qtyReturned;
  }

  public String getQtyReturned() {
    return qtyReturned;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setBaseDiscountAmount(String baseDiscountAmount) {
    this.baseDiscountAmount = baseDiscountAmount;
  }

  public String getBaseDiscountAmount() {
    return baseDiscountAmount;
  }

  public void setBaseRowTotalInclTax(String baseRowTotalInclTax) {
    this.baseRowTotalInclTax = baseRowTotalInclTax;
  }

  public String getBaseRowTotalInclTax() {
    return baseRowTotalInclTax;
  }

  public void setBaseDiscountTaxCompensationAmount(String baseDiscountTaxCompensationAmount) {
    this.baseDiscountTaxCompensationAmount = baseDiscountTaxCompensationAmount;
  }

  public String getBaseDiscountTaxCompensationAmount() {
    return baseDiscountTaxCompensationAmount;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProductType() {
    return productType;
  }

  public void setQtyRefunded(String qtyRefunded) {
    this.qtyRefunded = qtyRefunded;
  }

  public String getQtyRefunded() {
    return qtyRefunded;
  }

  public void setBaseRowInvoiced(String baseRowInvoiced) {
    this.baseRowInvoiced = baseRowInvoiced;
  }

  public String getBaseRowInvoiced() {
    return baseRowInvoiced;
  }

  public void setIsQtyDecimal(String isQtyDecimal) {
    this.isQtyDecimal = isQtyDecimal;
  }

  public String getIsQtyDecimal() {
    return isQtyDecimal;
  }

  public void setDiscountInvoiced(String discountInvoiced) {
    this.discountInvoiced = discountInvoiced;
  }

  public String getDiscountInvoiced() {
    return discountInvoiced;
  }

  public void setBaseTaxInvoiced(String baseTaxInvoiced) {
    this.baseTaxInvoiced = baseTaxInvoiced;
  }

  public String getBaseTaxInvoiced() {
    return baseTaxInvoiced;
  }

  @Override
  public String toString() {
    return
        "ItemsItem{" +
            "tax_amount = '" + taxAmount + '\'' +
            ",tax_invoiced = '" + taxInvoiced + '\'' +
            ",original_price = '" + originalPrice + '\'' +
            ",free_shipping = '" + freeShipping + '\'' +
            ",base_discount_tax_compensation_invoiced = '" + baseDiscountTaxCompensationInvoiced + '\'' +
            ",discount_amount = '" + discountAmount + '\'' +
            ",tax_percent = '" + taxPercent + '\'' +
            ",price_incl_tax = '" + priceInclTax + '\'' +
            ",price = '" + price + '\'' +
            ",product_id = '" + productId + '\'' +
            ",base_row_total = '" + baseRowTotal + '\'' +
            ",sku = '" + sku + '\'' +
            ",discount_tax_compensation_amount = '" + discountTaxCompensationAmount + '\'' +
            ",base_original_price = '" + baseOriginalPrice + '\'' +
            ",quote_item_id = '" + quoteItemId + '\'' +
            ",row_weight = '" + rowWeight + '\'' +
            ",base_amount_refunded = '" + baseAmountRefunded + '\'' +
            ",base_price_incl_tax = '" + basePriceInclTax + '\'' +
            ",no_discount = '" + noDiscount + '\'' +
            ",name = '" + name + '\'' +
            ",base_discount_invoiced = '" + baseDiscountInvoiced + '\'' +
            ",discount_percent = '" + discountPercent + '\'' +
            ",order_id = '" + orderId + '\'' +
            ",is_virtual = '" + isVirtual + '\'' +
            ",discount_tax_compensation_invoiced = '" + discountTaxCompensationInvoiced + '\'' +
            ",created_at = '" + createdAt + '\'' +
            ",qty_shipped = '" + qtyShipped + '\'' +
            ",qty_ordered = '" + qtyOrdered + '\'' +
            ",row_total = '" + rowTotal + '\'' +
            ",qty_canceled = '" + qtyCanceled + '\'' +
            ",amount_refunded = '" + amountRefunded + '\'' +
            ",updated_at = '" + updatedAt + '\'' +
            ",weee_tax_applied = '" + weeeTaxApplied + '\'' +
            ",base_price = '" + basePrice + '\'' +
            ",qty_invoiced = '" + qtyInvoiced + '\'' +
            ",row_invoiced = '" + rowInvoiced + '\'' +
            ",row_total_incl_tax = '" + rowTotalInclTax + '\'' +
            ",base_tax_amount = '" + baseTaxAmount + '\'' +
            ",store_id = '" + storeId + '\'' +
            ",qty_returned = '" + qtyReturned + '\'' +
            ",item_id = '" + itemId + '\'' +
            ",base_discount_amount = '" + baseDiscountAmount + '\'' +
            ",base_row_total_incl_tax = '" + baseRowTotalInclTax + '\'' +
            ",base_discount_tax_compensation_amount = '" + baseDiscountTaxCompensationAmount + '\'' +
            ",product_type = '" + productType + '\'' +
            ",qty_refunded = '" + qtyRefunded + '\'' +
            ",base_row_invoiced = '" + baseRowInvoiced + '\'' +
            ",is_qty_decimal = '" + isQtyDecimal + '\'' +
            ",discount_invoiced = '" + discountInvoiced + '\'' +
            ",base_tax_invoiced = '" + baseTaxInvoiced + '\'' +
            "}";
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
  }

  public String getLoyaltyValue() {
    return loyaltyValue;
  }

  public void setLoyaltyValue(String loyaltyValue) {
    this.loyaltyValue = loyaltyValue;
  }
}