package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class TotalSegmentExtensionAttributes implements Serializable {
  @SerializedName("tax_grandtotal_details")
  @Expose
  private List<TaxGrandtotalDetail> taxGrandtotalDetails = null;
  @SerializedName("gw_item_ids")
  @Expose
  private List<Object> gwItemIds = null;
  @SerializedName("gw_price")
  @Expose
  private String gwPrice;
  @SerializedName("gw_base_price")
  @Expose
  private String gwBasePrice;
  @SerializedName("gw_items_price")
  @Expose
  private String gwItemsPrice;
  @SerializedName("gw_items_base_price")
  @Expose
  private String gwItemsBasePrice;
  @SerializedName("gw_card_price")
  @Expose
  private String gwCardPrice;
  @SerializedName("gw_card_base_price")
  @Expose
  private String gwCardBasePrice;
  @SerializedName("gw_base_tax_amount")
  @Expose
  private String gwBaseTaxAmount;
  @SerializedName("gw_tax_amount")
  @Expose
  private String gwTaxAmount;
  @SerializedName("gw_items_base_tax_amount")
  @Expose
  private String gwItemsBaseTaxAmount;
  @SerializedName("gw_items_tax_amount")
  @Expose
  private String gwItemsTaxAmount;
  @SerializedName("gw_card_base_tax_amount")
  @Expose
  private String gwCardBaseTaxAmount;
  @SerializedName("gw_card_tax_amount")
  @Expose
  private String gwCardTaxAmount;
  @SerializedName("gw_price_incl_tax")
  @Expose
  private String gwPriceInclTax;
  @SerializedName("gw_base_price_incl_tax")
  @Expose
  private String gwBasePriceInclTax;
  @SerializedName("gw_card_price_incl_tax")
  @Expose
  private String gwCardPriceInclTax;
  @SerializedName("gw_card_base_price_incl_tax")
  @Expose
  private String gwCardBasePriceInclTax;
  @SerializedName("gw_items_price_incl_tax")
  @Expose
  private String gwItemsPriceInclTax;
  @SerializedName("gw_items_base_price_incl_tax")
  @Expose
  private String gwItemsBasePriceInclTax;

  private List<TaxGrandtotalDetail> getTaxGrandtotalDetails() {
    return taxGrandtotalDetails;
  }

  private void setTaxGrandtotalDetails(List<TaxGrandtotalDetail> taxGrandtotalDetails) {
    this.taxGrandtotalDetails = taxGrandtotalDetails;
  }

  private List<Object> getGwItemIds() {
    return gwItemIds;
  }

  private void setGwItemIds(List<Object> gwItemIds) {
    this.gwItemIds = gwItemIds;
  }

  private String getGwPrice() {
    return gwPrice;
  }

  private void setGwPrice(String gwPrice) {
    this.gwPrice = gwPrice;
  }

  private String getGwBasePrice() {
    return gwBasePrice;
  }

  private void setGwBasePrice(String gwBasePrice) {
    this.gwBasePrice = gwBasePrice;
  }

  private String getGwItemsPrice() {
    return gwItemsPrice;
  }

  private void setGwItemsPrice(String gwItemsPrice) {
    this.gwItemsPrice = gwItemsPrice;
  }

  private String getGwItemsBasePrice() {
    return gwItemsBasePrice;
  }

  private void setGwItemsBasePrice(String gwItemsBasePrice) {
    this.gwItemsBasePrice = gwItemsBasePrice;
  }

  private String getGwCardPrice() {
    return gwCardPrice;
  }

  private void setGwCardPrice(String gwCardPrice) {
    this.gwCardPrice = gwCardPrice;
  }

  private String getGwCardBasePrice() {
    return gwCardBasePrice;
  }

  private void setGwCardBasePrice(String gwCardBasePrice) {
    this.gwCardBasePrice = gwCardBasePrice;
  }

  private String getGwBaseTaxAmount() {
    return gwBaseTaxAmount;
  }

  private void setGwBaseTaxAmount(String gwBaseTaxAmount) {
    this.gwBaseTaxAmount = gwBaseTaxAmount;
  }

  private String getGwTaxAmount() {
    return gwTaxAmount;
  }

  private void setGwTaxAmount(String gwTaxAmount) {
    this.gwTaxAmount = gwTaxAmount;
  }

  private String getGwItemsBaseTaxAmount() {
    return gwItemsBaseTaxAmount;
  }

  private void setGwItemsBaseTaxAmount(String gwItemsBaseTaxAmount) {
    this.gwItemsBaseTaxAmount = gwItemsBaseTaxAmount;
  }

  private String getGwItemsTaxAmount() {
    return gwItemsTaxAmount;
  }

  private void setGwItemsTaxAmount(String gwItemsTaxAmount) {
    this.gwItemsTaxAmount = gwItemsTaxAmount;
  }

  private String getGwCardBaseTaxAmount() {
    return gwCardBaseTaxAmount;
  }

  private void setGwCardBaseTaxAmount(String gwCardBaseTaxAmount) {
    this.gwCardBaseTaxAmount = gwCardBaseTaxAmount;
  }

  private String getGwCardTaxAmount() {
    return gwCardTaxAmount;
  }

  private void setGwCardTaxAmount(String gwCardTaxAmount) {
    this.gwCardTaxAmount = gwCardTaxAmount;
  }

  private String getGwPriceInclTax() {
    return gwPriceInclTax;
  }

  private void setGwPriceInclTax(String gwPriceInclTax) {
    this.gwPriceInclTax = gwPriceInclTax;
  }

  private String getGwBasePriceInclTax() {
    return gwBasePriceInclTax;
  }

  private void setGwBasePriceInclTax(String gwBasePriceInclTax) {
    this.gwBasePriceInclTax = gwBasePriceInclTax;
  }

  private String getGwCardPriceInclTax() {
    return gwCardPriceInclTax;
  }

  private void setGwCardPriceInclTax(String gwCardPriceInclTax) {
    this.gwCardPriceInclTax = gwCardPriceInclTax;
  }

  private String getGwCardBasePriceInclTax() {
    return gwCardBasePriceInclTax;
  }

  private void setGwCardBasePriceInclTax(String gwCardBasePriceInclTax) {
    this.gwCardBasePriceInclTax = gwCardBasePriceInclTax;
  }

  private String getGwItemsPriceInclTax() {
    return gwItemsPriceInclTax;
  }

  private void setGwItemsPriceInclTax(String gwItemsPriceInclTax) {
    this.gwItemsPriceInclTax = gwItemsPriceInclTax;
  }

  private String getGwItemsBasePriceInclTax() {
    return gwItemsBasePriceInclTax;
  }

  private void setGwItemsBasePriceInclTax(String gwItemsBasePriceInclTax) {
    this.gwItemsBasePriceInclTax = gwItemsBasePriceInclTax;
  }
}
