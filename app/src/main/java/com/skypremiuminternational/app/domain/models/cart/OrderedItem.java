package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.SerializedName;

public class OrderedItem {

  @SerializedName("tax_amount")
  private String taxAmount;

  @SerializedName("item_id")
  private int itemId;

  @SerializedName("discount_amount")
  private String discountAmount;

  @SerializedName("base_discount_amount")
  private String baseDiscountAmount;

  @SerializedName("row_total")
  private String rowTotal;

  @SerializedName("row_total_with_discount")
  private String rowTotalWithDiscount;

  @SerializedName("weee_tax_applied_amount")
  private Object weeeTaxAppliedAmount;

  @SerializedName("tax_percent")
  private String taxPercent;

  @SerializedName("base_row_total_incl_tax")
  private String baseRowTotalInclTax;

  @SerializedName("base_price_incl_tax")
  private String basePriceInclTax;

  @SerializedName("price_incl_tax")
  private String priceInclTax;

  @SerializedName("price")
  private String price;

  @SerializedName("weee_tax_applied")
  private Object weeeTaxApplied;

  @SerializedName("qty")
  private int qty;

  @SerializedName("base_price")
  private String basePrice;

  @SerializedName("options")
  private String options;

  @SerializedName("name")
  private String name;

  @SerializedName("base_row_total")
  private String baseRowTotal;

  @SerializedName("discount_percent")
  private String discountPercent;

  @SerializedName("row_total_incl_tax")
  private String rowTotalInclTax;

  @SerializedName("base_tax_amount")
  private String baseTaxAmount;

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getItemId() {
    return itemId;
  }

  public void setDiscountAmount(String discountAmount) {
    this.discountAmount = discountAmount;
  }

  public String getDiscountAmount() {
    return discountAmount;
  }

  public void setBaseDiscountAmount(String baseDiscountAmount) {
    this.baseDiscountAmount = baseDiscountAmount;
  }

  public String getBaseDiscountAmount() {
    return baseDiscountAmount;
  }

  public void setRowTotal(String rowTotal) {
    this.rowTotal = rowTotal;
  }

  public String getRowTotal() {
    return rowTotal;
  }

  public void setRowTotalWithDiscount(String rowTotalWithDiscount) {
    this.rowTotalWithDiscount = rowTotalWithDiscount;
  }

  public String getRowTotalWithDiscount() {
    return rowTotalWithDiscount;
  }

  public void setWeeeTaxAppliedAmount(Object weeeTaxAppliedAmount) {
    this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
  }

  public Object getWeeeTaxAppliedAmount() {
    return weeeTaxAppliedAmount;
  }

  public void setTaxPercent(String taxPercent) {
    this.taxPercent = taxPercent;
  }

  public String getTaxPercent() {
    return taxPercent;
  }

  public void setBaseRowTotalInclTax(String baseRowTotalInclTax) {
    this.baseRowTotalInclTax = baseRowTotalInclTax;
  }

  public String getBaseRowTotalInclTax() {
    return baseRowTotalInclTax;
  }

  public void setBasePriceInclTax(String basePriceInclTax) {
    this.basePriceInclTax = basePriceInclTax;
  }

  public String getBasePriceInclTax() {
    return basePriceInclTax;
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

  public void setWeeeTaxApplied(Object weeeTaxApplied) {
    this.weeeTaxApplied = weeeTaxApplied;
  }

  public Object getWeeeTaxApplied() {
    return weeeTaxApplied;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public int getQty() {
    return qty;
  }

  public void setBasePrice(String basePrice) {
    this.basePrice = basePrice;
  }

  public String getBasePrice() {
    return basePrice;
  }

  public void setOptions(String options) {
    this.options = options;
  }

  public String getOptions() {
    return options;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setBaseRowTotal(String baseRowTotal) {
    this.baseRowTotal = baseRowTotal;
  }

  public String getBaseRowTotal() {
    return baseRowTotal;
  }

  public void setDiscountPercent(String discountPercent) {
    this.discountPercent = discountPercent;
  }

  public String getDiscountPercent() {
    return discountPercent;
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

  @Override
  public String toString() {
    return "OrderedItem{"
        + "tax_amount = '"
        + taxAmount
        + '\''
        + ",item_id = '"
        + itemId
        + '\''
        + ",discount_amount = '"
        + discountAmount
        + '\''
        + ",base_discount_amount = '"
        + baseDiscountAmount
        + '\''
        + ",row_total = '"
        + rowTotal
        + '\''
        + ",row_total_with_discount = '"
        + rowTotalWithDiscount
        + '\''
        + ",weee_tax_applied_amount = '"
        + weeeTaxAppliedAmount
        + '\''
        + ",tax_percent = '"
        + taxPercent
        + '\''
        + ",base_row_total_incl_tax = '"
        + baseRowTotalInclTax
        + '\''
        + ",base_price_incl_tax = '"
        + basePriceInclTax
        + '\''
        + ",price_incl_tax = '"
        + priceInclTax
        + '\''
        + ",price = '"
        + price
        + '\''
        + ",weee_tax_applied = '"
        + weeeTaxApplied
        + '\''
        + ",qty = '"
        + qty
        + '\''
        + ",base_price = '"
        + basePrice
        + '\''
        + ",options = '"
        + options
        + '\''
        + ",name = '"
        + name
        + '\''
        + ",base_row_total = '"
        + baseRowTotal
        + '\''
        + ",discount_percent = '"
        + discountPercent
        + '\''
        + ",row_total_incl_tax = '"
        + rowTotalInclTax
        + '\''
        + ",base_tax_amount = '"
        + baseTaxAmount
        + '\''
        + "}";
  }
}