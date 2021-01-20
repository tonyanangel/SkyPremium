package com.skypremiuminternational.app.domain.models.myOrder.billingaddress;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillingAddress {

  @SerializedName("company")
  private String company;

  @SerializedName("email")
  private String email;

  @SerializedName("address_type")
  private String addressType;

  @SerializedName("region")
  private String region;

  @SerializedName("region_code")
  private String region_code;

  @SerializedName("entity_id")
  private int entityId;

  @SerializedName("street")
  private List<String> street;

  @SerializedName("postcode")
  private String postcode;

  @SerializedName("extension_attributes")
  private ExtensionAttributes extensionAttributes;

  @SerializedName("customer_address_id")
  private int customerAddressId;

  @SerializedName("prefix")
  private String prefix;

  @SerializedName("firstname")
  private String firstname;

  @SerializedName("city")
  private String city;

  @SerializedName("lastname")
  private String lastname;

  @SerializedName("telephone")
  private String telephone;

  @SerializedName("country_id")
  private String countryId;

  @SerializedName("parent_id")
  private int parentId;

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompany() {
    return company;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  public String getAddressType() {
    return addressType;
  }

  public void setEntityId(int entityId) {
    this.entityId = entityId;
  }

  public int getEntityId() {
    return entityId;
  }

  public void setStreet(List<String> street) {
    this.street = street;
  }

  public List<String> getStreet() {
    return street;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public ExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setCustomerAddressId(int customerAddressId) {
    this.customerAddressId = customerAddressId;
  }

  public int getCustomerAddressId() {
    return customerAddressId;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }

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

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getCountryId() {
    return countryId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public int getParentId() {
    return parentId;
  }

  public String getRegion_code() {
    return region_code;
  }

  public void setRegion_code(String region_code) {
    this.region_code = region_code;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }
}