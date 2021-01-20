package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class Payment implements Serializable {
  @SerializedName("account_status")
  @Expose
  private Object accountStatus;
  @SerializedName("additional_information")
  @Expose
  private List<String> additionalInformation = null;
  @SerializedName("amount_ordered")
  @Expose
  private String amountOrdered;
  @SerializedName("base_amount_ordered")
  @Expose
  private String baseAmountOrdered;
  @SerializedName("base_shipping_amount")
  @Expose
  private String baseShippingAmount;
  @SerializedName("cc_last4")
  @Expose
  private String ccLast4;
  @SerializedName("entity_id")
  @Expose
  private Integer entityId;
  @SerializedName("method")
  @Expose
  private String method;
  @SerializedName("parent_id")
  @Expose
  private Integer parentId;
  @SerializedName("shipping_amount")
  @Expose
  private String shippingAmount;
  @SerializedName("extension_attributes")
  @Expose
  private List<Object> extensionAttributes = null;
  @SerializedName("amount_authorized")
  @Expose
  private String amountAuthorized;
  @SerializedName("amount_paid")
  @Expose
  private String amountPaid;
  @SerializedName("base_amount_authorized")
  @Expose
  private String baseAmountAuthorized;
  @SerializedName("base_amount_paid")
  @Expose
  private String baseAmountPaid;
  @SerializedName("base_amount_paid_online")
  @Expose
  private String baseAmountPaidOnline;
  @SerializedName("base_shipping_captured")
  @Expose
  private String baseShippingCaptured;
  @SerializedName("cc_trans_id")
  @Expose
  private String ccTransId;
  @SerializedName("cc_type")
  @Expose
  private String ccType;
  @SerializedName("last_trans_id")
  @Expose
  private String lastTransId;
  @SerializedName("shipping_captured")
  @Expose
  private String shippingCaptured;

  public Object getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(Object accountStatus) {
    this.accountStatus = accountStatus;
  }

  public List<String> getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(List<String> additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public String getAmountOrdered() {
    return amountOrdered;
  }

  public void setAmountOrdered(String amountOrdered) {
    this.amountOrdered = amountOrdered;
  }

  public String getBaseAmountOrdered() {
    return baseAmountOrdered;
  }

  public void setBaseAmountOrdered(String baseAmountOrdered) {
    this.baseAmountOrdered = baseAmountOrdered;
  }

  public String getBaseShippingAmount() {
    return baseShippingAmount;
  }

  public void setBaseShippingAmount(String baseShippingAmount) {
    this.baseShippingAmount = baseShippingAmount;
  }

  public String getCcLast4() {
    return ccLast4;
  }

  public void setCcLast4(String ccLast4) {
    this.ccLast4 = ccLast4;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getShippingAmount() {
    return shippingAmount;
  }

  public void setShippingAmount(String shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public List<Object> getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(List<Object> extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public String getAmountAuthorized() {
    return amountAuthorized;
  }

  public void setAmountAuthorized(String amountAuthorized) {
    this.amountAuthorized = amountAuthorized;
  }

  public String getAmountPaid() {
    return amountPaid;
  }

  public void setAmountPaid(String amountPaid) {
    this.amountPaid = amountPaid;
  }

  public String getBaseAmountAuthorized() {
    return baseAmountAuthorized;
  }

  public void setBaseAmountAuthorized(String baseAmountAuthorized) {
    this.baseAmountAuthorized = baseAmountAuthorized;
  }

  public String getBaseAmountPaid() {
    return baseAmountPaid;
  }

  public void setBaseAmountPaid(String baseAmountPaid) {
    this.baseAmountPaid = baseAmountPaid;
  }

  public String getBaseAmountPaidOnline() {
    return baseAmountPaidOnline;
  }

  public void setBaseAmountPaidOnline(String baseAmountPaidOnline) {
    this.baseAmountPaidOnline = baseAmountPaidOnline;
  }

  public String getBaseShippingCaptured() {
    return baseShippingCaptured;
  }

  public void setBaseShippingCaptured(String baseShippingCaptured) {
    this.baseShippingCaptured = baseShippingCaptured;
  }

  public String getCcTransId() {
    return ccTransId;
  }

  public void setCcTransId(String ccTransId) {
    this.ccTransId = ccTransId;
  }

  public String getCcType() {
    return ccType;
  }

  public void setCcType(String ccType) {
    this.ccType = ccType;
  }

  public String getLastTransId() {
    return lastTransId;
  }

  public void setLastTransId(String lastTransId) {
    this.lastTransId = lastTransId;
  }

  public String getShippingCaptured() {
    return shippingCaptured;
  }

  public void setShippingCaptured(String shippingCaptured) {
    this.shippingCaptured = shippingCaptured;
  }
}
