package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class CartAllInformationResponse implements Serializable {

  private List<OrderedItem> items;

  @SerializedName("grand_total")
  @Expose
  private String grandTotal;
  @SerializedName("base_grand_total")
  @Expose
  private String baseGrandTotal;
  @SerializedName("subtotal")
  @Expose
  private String subtotal;
  @SerializedName("base_subtotal")
  @Expose
  private String baseSubtotal;
  @SerializedName("discount_amount")
  @Expose
  private String discountAmount;
  @SerializedName("base_discount_amount")
  @Expose
  private String baseDiscountAmount;
  @SerializedName("subtotal_with_discount")
  @Expose
  private String subtotalWithDiscount;
  @SerializedName("base_subtotal_with_discount")
  @Expose
  private String baseSubtotalWithDiscount;
  @SerializedName("shipping_amount")
  @Expose
  private String shippingAmount;
  @SerializedName("base_shipping_amount")
  @Expose
  private String baseShippingAmount;
  @SerializedName("shipping_discount_amount")
  @Expose
  private String shippingDiscountAmount;
  @SerializedName("base_shipping_discount_amount")
  @Expose
  private String baseShippingDiscountAmount;
  @SerializedName("tax_amount")
  @Expose
  private String taxAmount;
  @SerializedName("base_tax_amount")
  @Expose
  private String baseTaxAmount;
  @SerializedName("weee_tax_applied_amount")
  @Expose
  private Object weeeTaxAppliedAmount;
  @SerializedName("shipping_tax_amount")
  @Expose
  private String shippingTaxAmount;
  @SerializedName("base_shipping_tax_amount")
  @Expose
  private String baseShippingTaxAmount;
  @SerializedName("subtotal_incl_tax")
  @Expose
  private String subtotalInclTax;
  @SerializedName("shipping_incl_tax")
  @Expose
  private String shippingInclTax;
  @SerializedName("base_shipping_incl_tax")
  @Expose
  private String baseShippingInclTax;
  @SerializedName("base_currency_code")
  @Expose
  private String baseCurrencyCode;
  @SerializedName("quote_currency_code")
  @Expose
  private String quoteCurrencyCode;
  @SerializedName("items_qty")
  @Expose
  private int itemsQty;
  @SerializedName("total_segments")
  @Expose
  private List<TotalSegment> totalSegments = null;
  @SerializedName("extension_attributes")
  @Expose
  private CartAllExtensionAttributes extensionAttributes;

  private String subTotal;
  private String shippingFee;
  private String deliveryFee;
  private String tax;
  private String redeemedSkyDollars;
  private String discountByCoupon;

  public String getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(String grandTotal) {
    this.grandTotal = grandTotal;
  }

  public String getBaseGrandTotal() {
    return baseGrandTotal;
  }

  public void setBaseGrandTotal(String baseGrandTotal) {
    this.baseGrandTotal = baseGrandTotal;
  }

  public String getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(String subtotal) {
    this.subtotal = subtotal;
  }

  public String getBaseSubtotal() {
    return baseSubtotal;
  }

  public void setBaseSubtotal(String baseSubtotal) {
    this.baseSubtotal = baseSubtotal;
  }

  public String getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(String discountAmount) {
    this.discountAmount = discountAmount;
  }

  public String getBaseDiscountAmount() {
    return baseDiscountAmount;
  }

  public void setBaseDiscountAmount(String baseDiscountAmount) {
    this.baseDiscountAmount = baseDiscountAmount;
  }

  public String getSubtotalWithDiscount() {
    return subtotalWithDiscount;
  }

  public void setSubtotalWithDiscount(String subtotalWithDiscount) {
    this.subtotalWithDiscount = subtotalWithDiscount;
  }

  public String getBaseSubtotalWithDiscount() {
    return baseSubtotalWithDiscount;
  }

  public void setBaseSubtotalWithDiscount(String baseSubtotalWithDiscount) {
    this.baseSubtotalWithDiscount = baseSubtotalWithDiscount;
  }

  public String getShippingAmount() {
    return shippingAmount;
  }

  public void setShippingAmount(String shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public String getBaseShippingAmount() {
    return baseShippingAmount;
  }

  public void setBaseShippingAmount(String baseShippingAmount) {
    this.baseShippingAmount = baseShippingAmount;
  }

  public String getShippingDiscountAmount() {
    return shippingDiscountAmount;
  }

  public void setShippingDiscountAmount(String shippingDiscountAmount) {
    this.shippingDiscountAmount = shippingDiscountAmount;
  }

  public String getBaseShippingDiscountAmount() {
    return baseShippingDiscountAmount;
  }

  public void setBaseShippingDiscountAmount(String baseShippingDiscountAmount) {
    this.baseShippingDiscountAmount = baseShippingDiscountAmount;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getBaseTaxAmount() {
    return baseTaxAmount;
  }

  public void setBaseTaxAmount(String baseTaxAmount) {
    this.baseTaxAmount = baseTaxAmount;
  }

  public Object getWeeeTaxAppliedAmount() {
    return weeeTaxAppliedAmount;
  }

  public void setWeeeTaxAppliedAmount(Object weeeTaxAppliedAmount) {
    this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
  }

  public String getShippingTaxAmount() {
    return shippingTaxAmount;
  }

  public void setShippingTaxAmount(String shippingTaxAmount) {
    this.shippingTaxAmount = shippingTaxAmount;
  }

  public String getBaseShippingTaxAmount() {
    return baseShippingTaxAmount;
  }

  public void setBaseShippingTaxAmount(String baseShippingTaxAmount) {
    this.baseShippingTaxAmount = baseShippingTaxAmount;
  }

  public String getSubtotalInclTax() {
    return subtotalInclTax;
  }

  public void setSubtotalInclTax(String subtotalInclTax) {
    this.subtotalInclTax = subtotalInclTax;
  }

  public String getShippingInclTax() {
    return shippingInclTax;
  }

  public void setShippingInclTax(String shippingInclTax) {
    this.shippingInclTax = shippingInclTax;
  }

  public String getBaseShippingInclTax() {
    return baseShippingInclTax;
  }

  public void setBaseShippingInclTax(String baseShippingInclTax) {
    this.baseShippingInclTax = baseShippingInclTax;
  }

  public String getBaseCurrencyCode() {
    return baseCurrencyCode;
  }

  public void setBaseCurrencyCode(String baseCurrencyCode) {
    this.baseCurrencyCode = baseCurrencyCode;
  }

  public String getQuoteCurrencyCode() {
    return quoteCurrencyCode;
  }

  public void setQuoteCurrencyCode(String quoteCurrencyCode) {
    this.quoteCurrencyCode = quoteCurrencyCode;
  }

  public int getItemsQty() {
    return itemsQty;
  }

  public void setItemsQty(int itemsQty) {
    this.itemsQty = itemsQty;
  }

  public List<TotalSegment> getTotalSegments() {
    return totalSegments;
  }

  public void setTotalSegments(
      List<TotalSegment> totalSegments) {
    this.totalSegments = totalSegments;
  }

  public CartAllExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(
      CartAllExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public String getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(String subTotal) {
    this.subTotal = subTotal;
  }

  public String getShippingFee() {
    return shippingFee;
  }

  public void setShippingFee(String shippingFee) {
    this.shippingFee = shippingFee;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public String getTax() {
    return tax;
  }

  public void setTax(String tax) {
    this.tax = tax;
  }

  public String getRedeemedSkyDollars() {
    return redeemedSkyDollars;
  }

  public void setRedeemedSkyDollars(String redeemedSkyDollars) {
    this.redeemedSkyDollars = redeemedSkyDollars;
  }

  public String getDiscountByCoupon() {
    return discountByCoupon;
  }

  public void setDiscountByCoupon(String discountByCoupon) {
    this.discountByCoupon = discountByCoupon;
  }

  public List<OrderedItem> getItems() {
    return items;
  }

  public void setItems(List<OrderedItem> items) {
    this.items = items;
  }
}
