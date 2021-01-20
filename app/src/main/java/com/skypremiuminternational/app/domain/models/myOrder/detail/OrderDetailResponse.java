package com.skypremiuminternational.app.domain.models.myOrder.detail;

import com.skypremiuminternational.app.domain.models.myOrder.Payment;
import com.skypremiuminternational.app.domain.models.myOrder.billingaddress.BillingAddress;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderDetailResponse implements Serializable {

  @SerializedName("tax_amount")
  private String taxAmount;

  @SerializedName("tax_invoiced")
  private String taxInvoiced;

  @SerializedName("customer_note_notify")
  private int customerNoteNotify;

  @SerializedName("shipping_discount_amount")
  private String shippingDiscountAmount;

  @SerializedName("base_discount_tax_compensation_invoiced")
  private String baseDiscountTaxCompensationInvoiced;

  @SerializedName("customer_firstname")
  private String customerFirstname;

  @SerializedName("discount_amount")
  private String discountAmount;

  @SerializedName("billing_address")
  private BillingAddress billingAddress;

  @SerializedName("remote_ip")
  private String remoteIp;

  @SerializedName("total_paid")
  private String totalPaid;

  @SerializedName("increment_id")
  private String incrementId;

  @SerializedName("payment")
  private Payment payment;

  @SerializedName("state")
  private String state;

  @SerializedName("base_shipping_tax_amount")
  private String baseShippingTaxAmount;

  @SerializedName("base_grand_total")
  private String baseGrandTotal;

  @SerializedName("billing_address_id")
  private int billingAddressId;

  @SerializedName("customer_lastname")
  private String customerLastname;

  @SerializedName("quote_id")
  private int quoteId;

  @SerializedName("shipping_invoiced")
  private String shippingInvoiced;

  @SerializedName("discount_tax_compensation_amount")
  private String discountTaxCompensationAmount;

  @SerializedName("weight")
  private int weight;

  @SerializedName("entity_id")
  private int entityId;

  @SerializedName("base_total_invoiced")
  private String baseTotalInvoiced;

  @SerializedName("base_shipping_amount")
  private String baseShippingAmount;

  @SerializedName("base_subtotal_invoiced")
  private String baseSubtotalInvoiced;

  @SerializedName("subtotal_incl_tax")
  private String subtotalInclTax;

  @SerializedName("subtotal")
  private String subtotal;

  @SerializedName("base_shipping_incl_tax")
  private String baseShippingInclTax;

  @SerializedName("customer_email")
  private String customerEmail;

  @SerializedName("base_discount_invoiced")
  private String baseDiscountInvoiced;

  @SerializedName("total_invoiced")
  private String totalInvoiced;

  @SerializedName("base_to_global_rate")
  private String baseToGlobalRate;

  @SerializedName("customer_is_guest")
  private int customerIsGuest;

  @SerializedName("items")
  private List<ItemsItem> items;

  @SerializedName("global_currency_code")
  private String globalCurrencyCode;

  @SerializedName("status")
  private String status;
  @SerializedName("payment_status")
  private String paymentStatus;

  @SerializedName("is_virtual")
  private int isVirtual;

  @SerializedName("base_total_invoiced_cost")
  private String baseTotalInvoicedCost;

  @SerializedName("email_sent")
  private int emailSent;

  @SerializedName("discount_tax_compensation_invoiced")
  private String discountTaxCompensationInvoiced;

  @SerializedName("status_histories")
  private List<StatusHistoriesItem> statusHistories;

  @SerializedName("store_currency_code")
  private String storeCurrencyCode;

  @SerializedName("created_at")
  private String createdAt;

  @SerializedName("total_item_count")
  private int totalItemCount;

  @SerializedName("shipping_tax_amount")
  private String shippingTaxAmount;

  @SerializedName("store_to_base_rate")
  private int storeToBaseRate;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("base_shipping_discount_amount")
  private String baseShippingDiscountAmount;

  @SerializedName("customer_prefix")
  private String customerPrefix;

  @SerializedName("store_name")
  private String storeName;

  @SerializedName("grand_total")
  private String grandTotal;

  @SerializedName("base_currency_code")
  private String baseCurrencyCode;

  @SerializedName("base_total_paid")
  private String baseTotalPaid;

  @SerializedName("base_tax_amount")
  private String baseTaxAmount;

  @SerializedName("store_id")
  private int storeId;

  @SerializedName("shipping_discount_tax_compensation_amount")
  private String shippingDiscountTaxCompensationAmount;

  @SerializedName("total_due")
  private String totalDue;

  @SerializedName("total_qty_ordered")
  private int totalQtyOrdered;

  @SerializedName("base_discount_amount")
  private String baseDiscountAmount;

  @SerializedName("extension_attributes")
  private ExtensionAttributes extensionAttributes;

  private String coupon_code;

  @SerializedName("shipping_description")
  private String shippingDescription;

  @SerializedName("store_to_order_rate")
  private int storeToOrderRate;

  @SerializedName("shipping_amount")
  private String shippingAmount;

  @SerializedName("base_discount_tax_compensation_amount")
  private String baseDiscountTaxCompensationAmount;

  @SerializedName("subtotal_invoiced")
  private String subtotalInvoiced;

  @SerializedName("base_to_order_rate")
  private int baseToOrderRate;

  @SerializedName("base_subtotal")
  private String baseSubtotal;

  @SerializedName("protect_code")
  private String protectCode;

  @SerializedName("customer_dob")
  private String customerDob;

  @SerializedName("base_total_due")
  private String baseTotalDue;

  @SerializedName("base_subtotal_incl_tax")
  private String baseSubtotalInclTax;

  @SerializedName("customer_id")
  private int customerId;

  @SerializedName("customer_group_id")
  private int customerGroupId;

  @SerializedName("discount_invoiced")
  private String discountInvoiced;

  @SerializedName("order_currency_code")
  private String orderCurrencyCode;

  @SerializedName("base_tax_invoiced")
  private String baseTaxInvoiced;

  @SerializedName("customer_gender")
  private int customerGender;

  @SerializedName("shipping_incl_tax")
  private String shippingInclTax;

  @SerializedName("base_shipping_invoiced")
  private String baseShippingInvoiced;

  private String shippingFee;
  private String deliveryFee;
  private String totalLoyaltyValueRedeemed;
  private String totalLoyaltyRenewProductRedeemed;


  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
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

  public String getTotalLoyaltyValueRedeemed() {
    return totalLoyaltyValueRedeemed;
  }

  public void setTotalLoyaltyValueRedeemed(String totalLoyaltyValueRedeemed) {
    this.totalLoyaltyValueRedeemed = totalLoyaltyValueRedeemed;
  }

  public String getTotalLoyaltyRenewProductRedeemed() {
    return totalLoyaltyRenewProductRedeemed;
  }

  public void setTotalLoyaltyRenewProductRedeemed(String totalLoyaltyRenewProductRedeemed) {
    this.totalLoyaltyRenewProductRedeemed = totalLoyaltyRenewProductRedeemed;
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

  public void setCustomerNoteNotify(int customerNoteNotify) {
    this.customerNoteNotify = customerNoteNotify;
  }

  public int getCustomerNoteNotify() {
    return customerNoteNotify;
  }

  public void setShippingDiscountAmount(String shippingDiscountAmount) {
    this.shippingDiscountAmount = shippingDiscountAmount;
  }

  public String getShippingDiscountAmount() {
    return shippingDiscountAmount;
  }

  public void setBaseDiscountTaxCompensationInvoiced(String baseDiscountTaxCompensationInvoiced) {
    this.baseDiscountTaxCompensationInvoiced = baseDiscountTaxCompensationInvoiced;
  }

  public String getBaseDiscountTaxCompensationInvoiced() {
    return baseDiscountTaxCompensationInvoiced;
  }

  public void setCustomerFirstname(String customerFirstname) {
    this.customerFirstname = customerFirstname;
  }

  public String getCustomerFirstname() {
    return customerFirstname;
  }

  public void setDiscountAmount(String discountAmount) {
    this.discountAmount = discountAmount;
  }

  public String getDiscountAmount() {
    return discountAmount;
  }

  public void setBillingAddress(BillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public BillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  public String getRemoteIp() {
    return remoteIp;
  }

  public void setTotalPaid(String totalPaid) {
    this.totalPaid = totalPaid;
  }

  public String getTotalPaid() {
    return totalPaid;
  }

  public void setIncrementId(String incrementId) {
    this.incrementId = incrementId;
  }

  public String getIncrementId() {
    return incrementId;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  public void setBaseShippingTaxAmount(String baseShippingTaxAmount) {
    this.baseShippingTaxAmount = baseShippingTaxAmount;
  }

  public String getBaseShippingTaxAmount() {
    return baseShippingTaxAmount;
  }

  public void setBaseGrandTotal(String baseGrandTotal) {
    this.baseGrandTotal = baseGrandTotal;
  }

  public String getBaseGrandTotal() {
    return baseGrandTotal;
  }

  public void setBillingAddressId(int billingAddressId) {
    this.billingAddressId = billingAddressId;
  }

  public int getBillingAddressId() {
    return billingAddressId;
  }

  public void setCustomerLastname(String customerLastname) {
    this.customerLastname = customerLastname;
  }

  public String getCustomerLastname() {
    return customerLastname;
  }

  public void setQuoteId(int quoteId) {
    this.quoteId = quoteId;
  }

  public int getQuoteId() {
    return quoteId;
  }

  public void setShippingInvoiced(String shippingInvoiced) {
    this.shippingInvoiced = shippingInvoiced;
  }

  public String getShippingInvoiced() {
    return shippingInvoiced;
  }

  public void setDiscountTaxCompensationAmount(String discountTaxCompensationAmount) {
    this.discountTaxCompensationAmount = discountTaxCompensationAmount;
  }

  public String getDiscountTaxCompensationAmount() {
    return discountTaxCompensationAmount;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  public void setEntityId(int entityId) {
    this.entityId = entityId;
  }

  public int getEntityId() {
    return entityId;
  }

  public void setBaseTotalInvoiced(String baseTotalInvoiced) {
    this.baseTotalInvoiced = baseTotalInvoiced;
  }

  public String getBaseTotalInvoiced() {
    return baseTotalInvoiced;
  }

  public void setBaseShippingAmount(String baseShippingAmount) {
    this.baseShippingAmount = baseShippingAmount;
  }

  public String getBaseShippingAmount() {
    return baseShippingAmount;
  }

  public void setBaseSubtotalInvoiced(String baseSubtotalInvoiced) {
    this.baseSubtotalInvoiced = baseSubtotalInvoiced;
  }

  public String getBaseSubtotalInvoiced() {
    return baseSubtotalInvoiced;
  }

  public void setSubtotalInclTax(String subtotalInclTax) {
    this.subtotalInclTax = subtotalInclTax;
  }

  public String getSubtotalInclTax() {
    return subtotalInclTax;
  }

  public void setSubtotal(String subtotal) {
    this.subtotal = subtotal;
  }

  public String getSubtotal() {
    return subtotal;
  }

  public void setBaseShippingInclTax(String baseShippingInclTax) {
    this.baseShippingInclTax = baseShippingInclTax;
  }

  public String getBaseShippingInclTax() {
    return baseShippingInclTax;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setBaseDiscountInvoiced(String baseDiscountInvoiced) {
    this.baseDiscountInvoiced = baseDiscountInvoiced;
  }

  public String getBaseDiscountInvoiced() {
    return baseDiscountInvoiced;
  }

  public void setTotalInvoiced(String totalInvoiced) {
    this.totalInvoiced = totalInvoiced;
  }

  public String getTotalInvoiced() {
    return totalInvoiced;
  }

  public void setBaseToGlobalRate(String baseToGlobalRate) {
    this.baseToGlobalRate = baseToGlobalRate;
  }

  public String getBaseToGlobalRate() {
    return baseToGlobalRate;
  }

  public void setCustomerIsGuest(int customerIsGuest) {
    this.customerIsGuest = customerIsGuest;
  }

  public int getCustomerIsGuest() {
    return customerIsGuest;
  }

  public void setItems(List<ItemsItem> items) {
    this.items = items;
  }

  public List<ItemsItem> getItems() {
    return items;
  }

  public void setGlobalCurrencyCode(String globalCurrencyCode) {
    this.globalCurrencyCode = globalCurrencyCode;
  }

  public String getGlobalCurrencyCode() {
    return globalCurrencyCode;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setIsVirtual(int isVirtual) {
    this.isVirtual = isVirtual;
  }

  public int getIsVirtual() {
    return isVirtual;
  }

  public void setBaseTotalInvoicedCost(String baseTotalInvoicedCost) {
    this.baseTotalInvoicedCost = baseTotalInvoicedCost;
  }

  public String getBaseTotalInvoicedCost() {
    return baseTotalInvoicedCost;
  }

  public void setEmailSent(int emailSent) {
    this.emailSent = emailSent;
  }

  public int getEmailSent() {
    return emailSent;
  }

  public void setDiscountTaxCompensationInvoiced(String discountTaxCompensationInvoiced) {
    this.discountTaxCompensationInvoiced = discountTaxCompensationInvoiced;
  }

  public String getDiscountTaxCompensationInvoiced() {
    return discountTaxCompensationInvoiced;
  }

  public void setStatusHistories(List<StatusHistoriesItem> statusHistories) {
    this.statusHistories = statusHistories;
  }

  public List<StatusHistoriesItem> getStatusHistories() {
    return statusHistories;
  }

  public void setStoreCurrencyCode(String storeCurrencyCode) {
    this.storeCurrencyCode = storeCurrencyCode;
  }

  public String getStoreCurrencyCode() {
    return storeCurrencyCode;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setTotalItemCount(int totalItemCount) {
    this.totalItemCount = totalItemCount;
  }

  public int getTotalItemCount() {
    return totalItemCount;
  }

  public void setShippingTaxAmount(String shippingTaxAmount) {
    this.shippingTaxAmount = shippingTaxAmount;
  }

  public String getShippingTaxAmount() {
    return shippingTaxAmount;
  }

  public void setStoreToBaseRate(int storeToBaseRate) {
    this.storeToBaseRate = storeToBaseRate;
  }

  public int getStoreToBaseRate() {
    return storeToBaseRate;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setBaseShippingDiscountAmount(String baseShippingDiscountAmount) {
    this.baseShippingDiscountAmount = baseShippingDiscountAmount;
  }

  public String getBaseShippingDiscountAmount() {
    return baseShippingDiscountAmount;
  }

  public void setCustomerPrefix(String customerPrefix) {
    this.customerPrefix = customerPrefix;
  }

  public String getCustomerPrefix() {
    return customerPrefix;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setGrandTotal(String grandTotal) {
    this.grandTotal = grandTotal;
  }

  public String getGrandTotal() {
    return grandTotal;
  }

  public void setBaseCurrencyCode(String baseCurrencyCode) {
    this.baseCurrencyCode = baseCurrencyCode;
  }

  public String getBaseCurrencyCode() {
    return baseCurrencyCode;
  }

  public void setBaseTotalPaid(String baseTotalPaid) {
    this.baseTotalPaid = baseTotalPaid;
  }

  public String getBaseTotalPaid() {
    return baseTotalPaid;
  }

  public void setBaseTaxAmount(String baseTaxAmount) {
    this.baseTaxAmount = baseTaxAmount;
  }

  public String getBaseTaxAmount() {
    return baseTaxAmount;
  }

  public void setStoreId(int storeId) {
    this.storeId = storeId;
  }

  public int getStoreId() {
    return storeId;
  }

  public void setShippingDiscountTaxCompensationAmount(
      String shippingDiscountTaxCompensationAmount) {
    this.shippingDiscountTaxCompensationAmount = shippingDiscountTaxCompensationAmount;
  }

  public String getShippingDiscountTaxCompensationAmount() {
    return shippingDiscountTaxCompensationAmount;
  }

  public void setTotalDue(String totalDue) {
    this.totalDue = totalDue;
  }

  public String getTotalDue() {
    return totalDue;
  }

  public void setTotalQtyOrdered(int totalQtyOrdered) {
    this.totalQtyOrdered = totalQtyOrdered;
  }

  public int getTotalQtyOrdered() {
    return totalQtyOrdered;
  }

  public void setBaseDiscountAmount(String baseDiscountAmount) {
    this.baseDiscountAmount = baseDiscountAmount;
  }

  public String getBaseDiscountAmount() {
    return baseDiscountAmount;
  }

  public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public ExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setShippingDescription(String shippingDescription) {
    this.shippingDescription = shippingDescription;
  }

  public String getShippingDescription() {
    return shippingDescription;
  }

  public void setStoreToOrderRate(int storeToOrderRate) {
    this.storeToOrderRate = storeToOrderRate;
  }

  public int getStoreToOrderRate() {
    return storeToOrderRate;
  }

  public void setShippingAmount(String shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public String getShippingAmount() {
    return shippingAmount;
  }

  public void setBaseDiscountTaxCompensationAmount(String baseDiscountTaxCompensationAmount) {
    this.baseDiscountTaxCompensationAmount = baseDiscountTaxCompensationAmount;
  }

  public String getBaseDiscountTaxCompensationAmount() {
    return baseDiscountTaxCompensationAmount;
  }

  public void setSubtotalInvoiced(String subtotalInvoiced) {
    this.subtotalInvoiced = subtotalInvoiced;
  }

  public String getSubtotalInvoiced() {
    return subtotalInvoiced;
  }

  public void setBaseToOrderRate(int baseToOrderRate) {
    this.baseToOrderRate = baseToOrderRate;
  }

  public int getBaseToOrderRate() {
    return baseToOrderRate;
  }

  public void setBaseSubtotal(String baseSubtotal) {
    this.baseSubtotal = baseSubtotal;
  }

  public String getBaseSubtotal() {
    return baseSubtotal;
  }

  public void setProtectCode(String protectCode) {
    this.protectCode = protectCode;
  }

  public String getProtectCode() {
    return protectCode;
  }

  public void setCustomerDob(String customerDob) {
    this.customerDob = customerDob;
  }

  public String getCustomerDob() {
    return customerDob;
  }

  public void setBaseTotalDue(String baseTotalDue) {
    this.baseTotalDue = baseTotalDue;
  }

  public String getBaseTotalDue() {
    return baseTotalDue;
  }

  public void setBaseSubtotalInclTax(String baseSubtotalInclTax) {
    this.baseSubtotalInclTax = baseSubtotalInclTax;
  }

  public String getBaseSubtotalInclTax() {
    return baseSubtotalInclTax;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerGroupId(int customerGroupId) {
    this.customerGroupId = customerGroupId;
  }

  public int getCustomerGroupId() {
    return customerGroupId;
  }

  public void setDiscountInvoiced(String discountInvoiced) {
    this.discountInvoiced = discountInvoiced;
  }

  public String getDiscountInvoiced() {
    return discountInvoiced;
  }

  public void setOrderCurrencyCode(String orderCurrencyCode) {
    this.orderCurrencyCode = orderCurrencyCode;
  }

  public String getOrderCurrencyCode() {
    return orderCurrencyCode;
  }

  public void setBaseTaxInvoiced(String baseTaxInvoiced) {
    this.baseTaxInvoiced = baseTaxInvoiced;
  }

  public String getBaseTaxInvoiced() {
    return baseTaxInvoiced;
  }

  public void setCustomerGender(int customerGender) {
    this.customerGender = customerGender;
  }

  public int getCustomerGender() {
    return customerGender;
  }

  public void setShippingInclTax(String shippingInclTax) {
    this.shippingInclTax = shippingInclTax;
  }

  public String getShippingInclTax() {
    return shippingInclTax;
  }

  public void setBaseShippingInvoiced(String baseShippingInvoiced) {
    this.baseShippingInvoiced = baseShippingInvoiced;
  }

  public String getBaseShippingInvoiced() {
    return baseShippingInvoiced;
  }

  @Override
  public String toString() {
    return
        "OrderDetailResponse{"
            +
            "tax_amount = '"
            + taxAmount
            + '\''
            +
            ",tax_invoiced = '"
            + taxInvoiced
            + '\''
            +
            ",customer_note_notify = '"
            + customerNoteNotify
            + '\''
            +
            ",shipping_discount_amount = '"
            + shippingDiscountAmount
            + '\''
            +
            ",base_discount_tax_compensation_invoiced = '"
            + baseDiscountTaxCompensationInvoiced
            + '\''
            +
            ",customer_firstname = '"
            + customerFirstname
            + '\''
            +
            ",discount_amount = '"
            + discountAmount
            + '\''
            +
            ",billing_address = '"
            + billingAddress
            + '\''
            +
            ",remote_ip = '"
            + remoteIp
            + '\''
            +
            ",total_paid = '"
            + totalPaid
            + '\''
            +
            ",increment_id = '"
            + incrementId
            + '\''
            +
            ",payment = '"
            + payment
            + '\''
            +
            ",state = '"
            + state
            + '\''
            +
            ",base_shipping_tax_amount = '"
            + baseShippingTaxAmount
            + '\''
            +
            ",base_grand_total = '"
            + baseGrandTotal
            + '\''
            +
            ",billing_address_id = '"
            + billingAddressId
            + '\''
            +
            ",customer_lastname = '"
            + customerLastname
            + '\''
            +
            ",quote_id = '"
            + quoteId
            + '\''
            +
            ",shipping_invoiced = '"
            + shippingInvoiced
            + '\''
            +
            ",discount_tax_compensation_amount = '"
            + discountTaxCompensationAmount
            + '\''
            +
            ",weight = '"
            + weight
            + '\''
            +
            ",entity_id = '"
            + entityId
            + '\''
            +
            ",base_total_invoiced = '"
            + baseTotalInvoiced
            + '\''
            +
            ",base_shipping_amount = '"
            + baseShippingAmount
            + '\''
            +
            ",base_subtotal_invoiced = '"
            + baseSubtotalInvoiced
            + '\''
            +
            ",subtotal_incl_tax = '"
            + subtotalInclTax
            + '\''
            +
            ",subtotal = '"
            + subtotal
            + '\''
            +
            ",base_shipping_incl_tax = '"
            + baseShippingInclTax
            + '\''
            +
            ",customer_email = '"
            + customerEmail
            + '\''
            +
            ",base_discount_invoiced = '"
            + baseDiscountInvoiced
            + '\''
            +
            ",total_invoiced = '"
            + totalInvoiced
            + '\''
            +
            ",base_to_global_rate = '"
            + baseToGlobalRate
            + '\''
            +
            ",customer_is_guest = '"
            + customerIsGuest
            + '\''
            +
            ",items = '"
            + items
            + '\''
            +
            ",global_currency_code = '"
            + globalCurrencyCode
            + '\''
            +
            ",status = '"
            + status
            + '\''
            +
            ",is_virtual = '"
            + isVirtual
            + '\''
            +
            ",base_total_invoiced_cost = '"
            + baseTotalInvoicedCost
            + '\''
            +
            ",email_sent = '"
            + emailSent
            + '\''
            +
            ",discount_tax_compensation_invoiced = '"
            + discountTaxCompensationInvoiced
            + '\''
            +
            ",status_histories = '"
            + statusHistories
            + '\''
            +
            ",store_currency_code = '"
            + storeCurrencyCode
            + '\''
            +
            ",created_at = '"
            + createdAt
            + '\''
            +
            ",total_item_count = '"
            + totalItemCount
            + '\''
            +
            ",shipping_tax_amount = '"
            + shippingTaxAmount
            + '\''
            +
            ",store_to_base_rate = '"
            + storeToBaseRate
            + '\''
            +
            ",updated_at = '"
            + updatedAt
            + '\''
            +
            ",base_shipping_discount_amount = '"
            + baseShippingDiscountAmount
            + '\''
            +
            ",customer_prefix = '"
            + customerPrefix
            + '\''
            +
            ",store_name = '"
            + storeName
            + '\''
            +
            ",grand_total = '"
            + grandTotal
            + '\''
            +
            ",base_currency_code = '"
            + baseCurrencyCode
            + '\''
            +
            ",base_total_paid = '"
            + baseTotalPaid
            + '\''
            +
            ",base_tax_amount = '"
            + baseTaxAmount
            + '\''
            +
            ",store_id = '"
            + storeId
            + '\''
            +
            ",shipping_discount_tax_compensation_amount = '"
            + shippingDiscountTaxCompensationAmount
            + '\''
            +
            ",total_due = '"
            + totalDue
            + '\''
            +
            ",total_qty_ordered = '"
            + totalQtyOrdered
            + '\''
            +
            ",base_discount_amount = '"
            + baseDiscountAmount
            + '\''
            +
            ",extension_attributes = '"
            + extensionAttributes
            + '\''
            +
            ",shipping_description = '"
            + shippingDescription
            + '\''
            +
            ",store_to_order_rate = '"
            + storeToOrderRate
            + '\''
            +
            ",shipping_amount = '"
            + shippingAmount
            + '\''
            +
            ",base_discount_tax_compensation_amount = '"
            + baseDiscountTaxCompensationAmount
            + '\''
            +
            ",subtotal_invoiced = '"
            + subtotalInvoiced
            + '\''
            +
            ",base_to_order_rate = '"
            + baseToOrderRate
            + '\''
            +
            ",base_subtotal = '"
            + baseSubtotal
            + '\''
            +
            ",protect_code = '"
            + protectCode
            + '\''
            +
            ",customer_dob = '"
            + customerDob
            + '\''
            +
            ",base_total_due = '"
            + baseTotalDue
            + '\''
            +
            ",base_subtotal_incl_tax = '"
            + baseSubtotalInclTax
            + '\''
            +
            ",customer_id = '"
            + customerId
            + '\''
            +
            ",customer_group_id = '"
            + customerGroupId
            + '\''
            +
            ",discount_invoiced = '"
            + discountInvoiced
            + '\''
            +
            ",order_currency_code = '"
            + orderCurrencyCode
            + '\''
            +
            ",base_tax_invoiced = '"
            + baseTaxInvoiced
            + '\''
            +
            ",customer_gender = '"
            + customerGender
            + '\''
            +
            ",shipping_incl_tax = '"
            + shippingInclTax
            + '\''
            +
            ",base_shipping_invoiced = '"
            + baseShippingInvoiced
            + '\''
            +
            "}";
  }

  public String getCoupon_code() {
    return coupon_code;
  }

  public void setCoupon_code(String coupon_code) {
    this.coupon_code = coupon_code;
  }
}