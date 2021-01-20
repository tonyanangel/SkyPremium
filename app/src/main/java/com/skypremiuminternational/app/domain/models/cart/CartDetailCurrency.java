package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailCurrency implements Serializable {
  @SerializedName("global_currency_code")
  @Expose
  private String globalCurrencyCode;
  @SerializedName("base_currency_code")
  @Expose
  private String baseCurrencyCode;
  @SerializedName("store_currency_code")
  @Expose
  private String storeCurrencyCode;
  @SerializedName("quote_currency_code")
  @Expose
  private String quoteCurrencyCode;
  @SerializedName("store_to_base_rate")
  @Expose
  private int storeToBaseRate;
  @SerializedName("store_to_quote_rate")
  @Expose
  private int storeToQuoteRate;
  @SerializedName("base_to_global_rate")
  @Expose
  private int baseToGlobalRate;
  @SerializedName("base_to_quote_rate")
  @Expose
  private int baseToQuoteRate;

  public String getGlobalCurrencyCode() {
    return globalCurrencyCode;
  }

  public void setGlobalCurrencyCode(String globalCurrencyCode) {
    this.globalCurrencyCode = globalCurrencyCode;
  }

  public String getBaseCurrencyCode() {
    return baseCurrencyCode;
  }

  public void setBaseCurrencyCode(String baseCurrencyCode) {
    this.baseCurrencyCode = baseCurrencyCode;
  }

  public String getStoreCurrencyCode() {
    return storeCurrencyCode;
  }

  public void setStoreCurrencyCode(String storeCurrencyCode) {
    this.storeCurrencyCode = storeCurrencyCode;
  }

  public String getQuoteCurrencyCode() {
    return quoteCurrencyCode;
  }

  public void setQuoteCurrencyCode(String quoteCurrencyCode) {
    this.quoteCurrencyCode = quoteCurrencyCode;
  }

  public int getStoreToBaseRate() {
    return storeToBaseRate;
  }

  public void setStoreToBaseRate(int storeToBaseRate) {
    this.storeToBaseRate = storeToBaseRate;
  }

  public int getStoreToQuoteRate() {
    return storeToQuoteRate;
  }

  public void setStoreToQuoteRate(int storeToQuoteRate) {
    this.storeToQuoteRate = storeToQuoteRate;
  }

  public int getBaseToGlobalRate() {
    return baseToGlobalRate;
  }

  public void setBaseToGlobalRate(int baseToGlobalRate) {
    this.baseToGlobalRate = baseToGlobalRate;
  }

  public int getBaseToQuoteRate() {
    return baseToQuoteRate;
  }

  public void setBaseToQuoteRate(int baseToQuoteRate) {
    this.baseToQuoteRate = baseToQuoteRate;
  }
}
