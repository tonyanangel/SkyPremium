package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class Total implements Serializable {
  @SerializedName("base_shipping_amount")
  @Expose
  private String baseShippingAmount;
  @SerializedName("base_shipping_discount_amount")
  @Expose
  private String baseShippingDiscountAmount;
  @SerializedName("base_shipping_incl_tax")
  @Expose
  private String baseShippingInclTax;
  @SerializedName("base_shipping_tax_amount")
  @Expose
  private String baseShippingTaxAmount;
  @SerializedName("shipping_amount")
  @Expose
  private String shippingAmount;
  @SerializedName("shipping_discount_amount")
  @Expose
  private String shippingDiscountAmount;
  @SerializedName("shipping_discount_tax_compensation_amount")
  @Expose
  private String shippingDiscountTaxCompensationAmount;
  @SerializedName("shipping_incl_tax")
  @Expose
  private String shippingInclTax;
  @SerializedName("shipping_tax_amount")
  @Expose
  private String shippingTaxAmount;
  @SerializedName("base_shipping_invoiced")
  @Expose
  private String baseShippingInvoiced;
  @SerializedName("shipping_invoiced")
  @Expose
  private String shippingInvoiced;

  public String getBaseShippingAmount() {
    return baseShippingAmount;
  }

  public void setBaseShippingAmount(String baseShippingAmount) {
    this.baseShippingAmount = baseShippingAmount;
  }

  public String getBaseShippingDiscountAmount() {
    return baseShippingDiscountAmount;
  }

  public void setBaseShippingDiscountAmount(String baseShippingDiscountAmount) {
    this.baseShippingDiscountAmount = baseShippingDiscountAmount;
  }

  public String getBaseShippingInclTax() {
    return baseShippingInclTax;
  }

  public void setBaseShippingInclTax(String baseShippingInclTax) {
    this.baseShippingInclTax = baseShippingInclTax;
  }

  public String getBaseShippingTaxAmount() {
    return baseShippingTaxAmount;
  }

  public void setBaseShippingTaxAmount(String baseShippingTaxAmount) {
    this.baseShippingTaxAmount = baseShippingTaxAmount;
  }

  public String getShippingAmount() {
    return shippingAmount;
  }

  public void setShippingAmount(String shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public String getShippingDiscountAmount() {
    return shippingDiscountAmount;
  }

  public void setShippingDiscountAmount(String shippingDiscountAmount) {
    this.shippingDiscountAmount = shippingDiscountAmount;
  }

  public String getShippingDiscountTaxCompensationAmount() {
    return shippingDiscountTaxCompensationAmount;
  }

  public void setShippingDiscountTaxCompensationAmount(
      String shippingDiscountTaxCompensationAmount) {
    this.shippingDiscountTaxCompensationAmount = shippingDiscountTaxCompensationAmount;
  }

  public String getShippingInclTax() {
    return shippingInclTax;
  }

  public void setShippingInclTax(String shippingInclTax) {
    this.shippingInclTax = shippingInclTax;
  }

  public String getShippingTaxAmount() {
    return shippingTaxAmount;
  }

  public void setShippingTaxAmount(String shippingTaxAmount) {
    this.shippingTaxAmount = shippingTaxAmount;
  }

  public String getBaseShippingInvoiced() {
    return baseShippingInvoiced;
  }

  public void setBaseShippingInvoiced(String baseShippingInvoiced) {
    this.baseShippingInvoiced = baseShippingInvoiced;
  }

  public String getShippingInvoiced() {
    return shippingInvoiced;
  }

  public void setShippingInvoiced(String shippingInvoiced) {
    this.shippingInvoiced = shippingInvoiced;
  }
}
