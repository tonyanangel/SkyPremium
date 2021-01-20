package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class MyOrderItem implements Serializable {
  @SerializedName("base_currency_code")
  @Expose
  private String baseCurrencyCode;
  @SerializedName("base_discount_amount")
  @Expose
  private String baseDiscountAmount;
  @SerializedName("base_grand_total")
  @Expose
  private String baseGrandTotal;
  @SerializedName("base_discount_tax_compensation_amount")
  @Expose
  private String baseDiscountTaxCompensationAmount;
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
  @SerializedName("base_subtotal")
  @Expose
  private String baseSubtotal;
  @SerializedName("base_subtotal_incl_tax")
  @Expose
  private String baseSubtotalInclTax;
  @SerializedName("base_tax_amount")
  @Expose
  private String baseTaxAmount;
  @SerializedName("base_total_due")
  @Expose
  private String baseTotalDue;
  @SerializedName("base_to_global_rate")
  @Expose
  private String baseToGlobalRate;
  @SerializedName("base_to_order_rate")
  @Expose
  private String baseToOrderRate;
  @SerializedName("billing_address_id")
  @Expose
  private String billingAddressId;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("total_loyalty_value_redeemed")
  @Expose
  private String loyaltyValueRedeemed;
  @SerializedName("customer_dob")
  @Expose
  private String customerDob;
  @SerializedName("customer_email")
  @Expose
  private String customerEmail;
  @SerializedName("customer_firstname")
  @Expose
  private String customerFirstname;
  @SerializedName("customer_gender")
  @Expose
  private String customerGender;
  @SerializedName("customer_group_id")
  @Expose
  private String customerGroupId;
  @SerializedName("customer_id")
  @Expose
  private String customerId;
  @SerializedName("customer_is_guest")
  @Expose
  private String customerIsGuest;
  @SerializedName("customer_lastname")
  @Expose
  private String customerLastname;
  @SerializedName("customer_note_notify")
  @Expose
  private String customerNoteNotify;
  @SerializedName("customer_prefix")
  @Expose
  private String customerPrefix;
  @SerializedName("discount_amount")
  @Expose
  private String discountAmount;
  @SerializedName("email_sent")
  @Expose
  private String emailSent;
  @SerializedName("entity_id")
  @Expose
  private Integer entityId;
  @SerializedName("global_currency_code")
  @Expose
  private String globalCurrencyCode;
  @SerializedName("grand_total")
  @Expose
  private String grandTotal;
  @SerializedName("discount_tax_compensation_amount")
  @Expose
  private String discountTaxCompensationAmount;
  @SerializedName("increment_id")
  @Expose
  private String incrementId;
  @SerializedName("is_virtual")
  @Expose
  private String isVirtual;
  @SerializedName("order_currency_code")
  @Expose
  private String orderCurrencyCode;
  @SerializedName("protect_code")
  @Expose
  private String protectCode;
  @SerializedName("quote_id")
  @Expose
  private String quoteId;
  @SerializedName("remote_ip")
  @Expose
  private String remoteIp;
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
  @SerializedName("state")
  @Expose
  private String state;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("payment_status")
  @Expose
  private String paymentStatus;
  @SerializedName("store_currency_code")
  @Expose
  private String storeCurrencyCode;
  @SerializedName("store_id")
  @Expose
  private String storeId;
  @SerializedName("store_name")
  @Expose
  private String storeName;
  @SerializedName("store_to_base_rate")
  @Expose
  private String storeToBaseRate;
  @SerializedName("store_to_order_rate")
  @Expose
  private String storeToOrderRate;
  @SerializedName("subtotal")
  @Expose
  private String subtotal;
  @SerializedName("subtotal_incl_tax")
  @Expose
  private String subtotalInclTax;
  @SerializedName("tax_amount")
  @Expose
  private String taxAmount;
  @SerializedName("total_due")
  @Expose
  private String totalDue;
  @SerializedName("total_item_count")
  @Expose
  private String totalItemCount;
  @SerializedName("total_qty_ordered")
  @Expose
  private String totalQtyOrdered;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("weight")
  @Expose
  private String weight;
  @SerializedName("items")
  @Expose
  private List<MyOrderSubItem> items = null;
  @SerializedName("billing_address")
  @Expose
  private BillingAddress billingAddress;
  @SerializedName("payment")
  @Expose
  private Payment payment;
  @SerializedName("status_histories")
  @Expose
  private List<StatusHistory> statusHistories = null;
  @SerializedName("extension_attributes")
  @Expose
  private ExtensionAttributes extensionAttributes;
  @SerializedName("applied_rule_ids")
  @Expose
  private String appliedRuleIds;
  @SerializedName("base_discount_invoiced")
  @Expose
  private String baseDiscountInvoiced;
  @SerializedName("base_discount_tax_compensation_invoiced")
  @Expose
  private String baseDiscountTaxCompensationInvoiced;
  @SerializedName("base_shipping_invoiced")
  @Expose
  private String baseShippingInvoiced;
  @SerializedName("base_subtotal_invoiced")
  @Expose
  private String baseSubtotalInvoiced;
  @SerializedName("base_tax_invoiced")
  @Expose
  private String baseTaxInvoiced;
  @SerializedName("base_total_invoiced")
  @Expose
  private String baseTotalInvoiced;
  @SerializedName("base_total_invoiced_cost")
  @Expose
  private String baseTotalInvoicedCost;
  @SerializedName("base_total_paid")
  @Expose
  private String baseTotalPaid;
  @SerializedName("coupon_code")
  @Expose
  private String couponCode;
  @SerializedName("discount_description")
  @Expose
  private String discountDescription;
  @SerializedName("discount_invoiced")
  @Expose
  private String discountInvoiced;
  @SerializedName("discount_tax_compensation_invoiced")
  @Expose
  private String discountTaxCompensationInvoiced;
  @SerializedName("shipping_description")
  @Expose
  private String shippingDescription;
  @SerializedName("shipping_invoiced")
  @Expose
  private String shippingInvoiced;
  @SerializedName("subtotal_invoiced")
  @Expose
  private String subtotalInvoiced;
  @SerializedName("tax_invoiced")
  @Expose
  private String taxInvoiced;
  @SerializedName("total_invoiced")
  @Expose
  private String totalInvoiced;
  @SerializedName("total_paid")
  @Expose
  private String totalPaid;


  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getBaseCurrencyCode() {
    return baseCurrencyCode;
  }

  public void setBaseCurrencyCode(String baseCurrencyCode) {
    this.baseCurrencyCode = baseCurrencyCode;
  }

  public String getBaseDiscountAmount() {
    return baseDiscountAmount;
  }

  public void setBaseDiscountAmount(String baseDiscountAmount) {
    this.baseDiscountAmount = baseDiscountAmount;
  }

  public String getBaseGrandTotal() {
    return baseGrandTotal;
  }

  public void setBaseGrandTotal(String baseGrandTotal) {
    this.baseGrandTotal = baseGrandTotal;
  }

  public String getBaseDiscountTaxCompensationAmount() {
    return baseDiscountTaxCompensationAmount;
  }

  public void setBaseDiscountTaxCompensationAmount(String baseDiscountTaxCompensationAmount) {
    this.baseDiscountTaxCompensationAmount = baseDiscountTaxCompensationAmount;
  }

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

  public String getBaseSubtotal() {
    return baseSubtotal;
  }

  public void setBaseSubtotal(String baseSubtotal) {
    this.baseSubtotal = baseSubtotal;
  }

  public String getBaseSubtotalInclTax() {
    return baseSubtotalInclTax;
  }

  public void setBaseSubtotalInclTax(String baseSubtotalInclTax) {
    this.baseSubtotalInclTax = baseSubtotalInclTax;
  }

  public String getBaseTaxAmount() {
    return baseTaxAmount;
  }

  public void setBaseTaxAmount(String baseTaxAmount) {
    this.baseTaxAmount = baseTaxAmount;
  }

  public String getBaseTotalDue() {
    return baseTotalDue;
  }

  public void setBaseTotalDue(String baseTotalDue) {
    this.baseTotalDue = baseTotalDue;
  }

  public String getBaseToGlobalRate() {
    return baseToGlobalRate;
  }

  public void setBaseToGlobalRate(String baseToGlobalRate) {
    this.baseToGlobalRate = baseToGlobalRate;
  }

  public String getBaseToOrderRate() {
    return baseToOrderRate;
  }

  public void setBaseToOrderRate(String baseToOrderRate) {
    this.baseToOrderRate = baseToOrderRate;
  }

  public String getBillingAddressId() {
    return billingAddressId;
  }

  public void setBillingAddressId(String billingAddressId) {
    this.billingAddressId = billingAddressId;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getCustomerDob() {
    return customerDob;
  }

  public void setCustomerDob(String customerDob) {
    this.customerDob = customerDob;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public String getCustomerFirstname() {
    return customerFirstname;
  }

  public void setCustomerFirstname(String customerFirstname) {
    this.customerFirstname = customerFirstname;
  }

  public String getCustomerGender() {
    return customerGender;
  }

  public void setCustomerGender(String customerGender) {
    this.customerGender = customerGender;
  }

  public String getCustomerGroupId() {
    return customerGroupId;
  }

  public void setCustomerGroupId(String customerGroupId) {
    this.customerGroupId = customerGroupId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerIsGuest() {
    return customerIsGuest;
  }

  public void setCustomerIsGuest(String customerIsGuest) {
    this.customerIsGuest = customerIsGuest;
  }

  public String getCustomerLastname() {
    return customerLastname;
  }

  public void setCustomerLastname(String customerLastname) {
    this.customerLastname = customerLastname;
  }

  public String getCustomerNoteNotify() {
    return customerNoteNotify;
  }

  public void setCustomerNoteNotify(String customerNoteNotify) {
    this.customerNoteNotify = customerNoteNotify;
  }

  public String getCustomerPrefix() {
    return customerPrefix;
  }

  public void setCustomerPrefix(String customerPrefix) {
    this.customerPrefix = customerPrefix;
  }

  public String getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(String discountAmount) {
    this.discountAmount = discountAmount;
  }

  public String getEmailSent() {
    return emailSent;
  }

  public void setEmailSent(String emailSent) {
    this.emailSent = emailSent;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
  }

  public String getGlobalCurrencyCode() {
    return globalCurrencyCode;
  }

  public void setGlobalCurrencyCode(String globalCurrencyCode) {
    this.globalCurrencyCode = globalCurrencyCode;
  }

  public String getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(String grandTotal) {
    this.grandTotal = grandTotal;
  }

  public String getDiscountTaxCompensationAmount() {
    return discountTaxCompensationAmount;
  }

  public void setDiscountTaxCompensationAmount(String discountTaxCompensationAmount) {
    this.discountTaxCompensationAmount = discountTaxCompensationAmount;
  }

  public String getIncrementId() {
    return incrementId;
  }

  public void setIncrementId(String incrementId) {
    this.incrementId = incrementId;
  }

  public String getIsVirtual() {
    return isVirtual;
  }

  public void setIsVirtual(String isVirtual) {
    this.isVirtual = isVirtual;
  }

  public String getOrderCurrencyCode() {
    return orderCurrencyCode;
  }

  public void setOrderCurrencyCode(String orderCurrencyCode) {
    this.orderCurrencyCode = orderCurrencyCode;
  }

  public String getProtectCode() {
    return protectCode;
  }

  public void setProtectCode(String protectCode) {
    this.protectCode = protectCode;
  }

  public String getQuoteId() {
    return quoteId;
  }

  public void setQuoteId(String quoteId) {
    this.quoteId = quoteId;
  }

  public String getRemoteIp() {
    return remoteIp;
  }

  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
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

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStoreCurrencyCode() {
    return storeCurrencyCode;
  }

  public void setStoreCurrencyCode(String storeCurrencyCode) {
    this.storeCurrencyCode = storeCurrencyCode;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getStoreToBaseRate() {
    return storeToBaseRate;
  }

  public void setStoreToBaseRate(String storeToBaseRate) {
    this.storeToBaseRate = storeToBaseRate;
  }

  public String getStoreToOrderRate() {
    return storeToOrderRate;
  }

  public void setStoreToOrderRate(String storeToOrderRate) {
    this.storeToOrderRate = storeToOrderRate;
  }

  public String getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(String subtotal) {
    this.subtotal = subtotal;
  }

  public String getSubtotalInclTax() {
    return subtotalInclTax;
  }

  public void setSubtotalInclTax(String subtotalInclTax) {
    this.subtotalInclTax = subtotalInclTax;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public String getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(String totalDue) {
    this.totalDue = totalDue;
  }

  public String getTotalItemCount() {
    return totalItemCount;
  }

  public void setTotalItemCount(String totalItemCount) {
    this.totalItemCount = totalItemCount;
  }

  public String getTotalQtyOrdered() {
    return totalQtyOrdered;
  }

  public void setTotalQtyOrdered(String totalQtyOrdered) {
    this.totalQtyOrdered = totalQtyOrdered;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public List<MyOrderSubItem> getItems() {
    return items;
  }

  public void setItems(
      List<MyOrderSubItem> items) {
    this.items = items;
  }

  public BillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(
      BillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public List<StatusHistory> getStatusHistories() {
    return statusHistories;
  }

  public void setStatusHistories(
      List<StatusHistory> statusHistories) {
    this.statusHistories = statusHistories;
  }

  public ExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(
      ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public String getAppliedRuleIds() {
    return appliedRuleIds;
  }

  public void setAppliedRuleIds(String appliedRuleIds) {
    this.appliedRuleIds = appliedRuleIds;
  }

  public String getBaseDiscountInvoiced() {
    return baseDiscountInvoiced;
  }

  public void setBaseDiscountInvoiced(String baseDiscountInvoiced) {
    this.baseDiscountInvoiced = baseDiscountInvoiced;
  }

  public String getBaseDiscountTaxCompensationInvoiced() {
    return baseDiscountTaxCompensationInvoiced;
  }

  public void setBaseDiscountTaxCompensationInvoiced(String baseDiscountTaxCompensationInvoiced) {
    this.baseDiscountTaxCompensationInvoiced = baseDiscountTaxCompensationInvoiced;
  }

  public String getBaseShippingInvoiced() {
    return baseShippingInvoiced;
  }

  public void setBaseShippingInvoiced(String baseShippingInvoiced) {
    this.baseShippingInvoiced = baseShippingInvoiced;
  }

  public String getBaseSubtotalInvoiced() {
    return baseSubtotalInvoiced;
  }

  public void setBaseSubtotalInvoiced(String baseSubtotalInvoiced) {
    this.baseSubtotalInvoiced = baseSubtotalInvoiced;
  }

  public String getBaseTaxInvoiced() {
    return baseTaxInvoiced;
  }

  public void setBaseTaxInvoiced(String baseTaxInvoiced) {
    this.baseTaxInvoiced = baseTaxInvoiced;
  }

  public String getBaseTotalInvoiced() {
    return baseTotalInvoiced;
  }

  public void setBaseTotalInvoiced(String baseTotalInvoiced) {
    this.baseTotalInvoiced = baseTotalInvoiced;
  }

  public String getBaseTotalInvoicedCost() {
    return baseTotalInvoicedCost;
  }

  public void setBaseTotalInvoicedCost(String baseTotalInvoicedCost) {
    this.baseTotalInvoicedCost = baseTotalInvoicedCost;
  }

  public String getBaseTotalPaid() {
    return baseTotalPaid;
  }

  public void setBaseTotalPaid(String baseTotalPaid) {
    this.baseTotalPaid = baseTotalPaid;
  }

  public String getCouponCode() {
    return couponCode;
  }

  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  public String getDiscountDescription() {
    return discountDescription;
  }

  public void setDiscountDescription(String discountDescription) {
    this.discountDescription = discountDescription;
  }

  public String getDiscountInvoiced() {
    return discountInvoiced;
  }

  public void setDiscountInvoiced(String discountInvoiced) {
    this.discountInvoiced = discountInvoiced;
  }

  public String getDiscountTaxCompensationInvoiced() {
    return discountTaxCompensationInvoiced;
  }

  public void setDiscountTaxCompensationInvoiced(String discountTaxCompensationInvoiced) {
    this.discountTaxCompensationInvoiced = discountTaxCompensationInvoiced;
  }

  public String getShippingDescription() {
    return shippingDescription;
  }

  public void setShippingDescription(String shippingDescription) {
    this.shippingDescription = shippingDescription;
  }

  public String getShippingInvoiced() {
    return shippingInvoiced;
  }

  public void setShippingInvoiced(String shippingInvoiced) {
    this.shippingInvoiced = shippingInvoiced;
  }

  public String getSubtotalInvoiced() {
    return subtotalInvoiced;
  }

  public void setSubtotalInvoiced(String subtotalInvoiced) {
    this.subtotalInvoiced = subtotalInvoiced;
  }

  public String getTaxInvoiced() {
    return taxInvoiced;
  }

  public void setTaxInvoiced(String taxInvoiced) {
    this.taxInvoiced = taxInvoiced;
  }

  public String getTotalInvoiced() {
    return totalInvoiced;
  }

  public void setTotalInvoiced(String totalInvoiced) {
    this.totalInvoiced = totalInvoiced;
  }

  public String getTotalPaid() {
    return totalPaid;
  }

  public void setTotalPaid(String totalPaid) {
    this.totalPaid = totalPaid;
  }

  public String getLoyaltyValueRedeemed() {
    return loyaltyValueRedeemed;
  }

  public void setLoyaltyValueRedeemed(String loyaltyValueRedeemed) {
    this.loyaltyValueRedeemed = loyaltyValueRedeemed;
  }
}
