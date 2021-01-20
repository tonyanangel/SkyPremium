package com.skypremiuminternational.app.data.model.billingaddress;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillingAddress {

  @SerializedName("firstname")
  private String firstname;

  @SerializedName("city")
  private String city;

  @SerializedName("region_id")
  private String regionId;

  @SerializedName("postcode")
  private String postcode;

  @SerializedName("telephone")
  private String telephone;

  @SerializedName("lastname")
  private String lastname;

  @SerializedName("unit_number")
  private String unitNumber;

  @SerializedName("street")
  private List<String> street;

  @SerializedName("crm_address_order")
  private String crmAddressOrder;

  @SerializedName("company")
  private String company;

  @SerializedName("id")
  private String id;

  @SerializedName("salutation")
  private String salutation;

  @SerializedName("customer_id")
  private String customerId;

  @SerializedName("region")
  private List<RegionItem> region;

  @SerializedName("default_billing")
  private boolean defaultBilling;

  @SerializedName("email")
  private String email;

  @SerializedName("country_id")
  private String countryId;

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setStreet(List<String> street) {
    this.street = street;
  }

  public List<String> getStreet() {
    return street;
  }

  public void setCrmAddressOrder(String crmAddressOrder) {
    this.crmAddressOrder = crmAddressOrder;
  }

  public String getCrmAddressOrder() {
    return crmAddressOrder;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompany() {
    return company;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }

  public String getSalutation() {
    return salutation;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setRegion(List<RegionItem> region) {
    this.region = region;
  }

  public List<RegionItem> getRegion() {
    return region;
  }

  public void setDefaultBilling(boolean defaultBilling) {
    this.defaultBilling = defaultBilling;
  }

  public boolean isDefaultBilling() {
    return defaultBilling;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getCountryId() {
    return countryId;
  }

  /*20201707 - WIKI Viet Nguyen - fix bug set default billing address*/
  private Boolean isBillingAddressSelected = false;

  public Boolean isBillingAddressSelected() {
    return isBillingAddressSelected;
  }

  public void setBillingAddressSelected(Boolean billingAddressSelected) {
    isBillingAddressSelected = billingAddressSelected;
  }
}
