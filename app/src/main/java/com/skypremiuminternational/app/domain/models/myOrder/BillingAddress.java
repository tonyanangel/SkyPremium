package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class BillingAddress implements Serializable {
  @SerializedName("address_type")
  @Expose
  private String addressType;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("company")
  @Expose
  private String company;
  @SerializedName("country_id")
  @Expose
  private String countryId;
  @SerializedName("customer_address_id")
  @Expose
  private Integer customerAddressId;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("entity_id")
  @Expose
  private Integer entityId;
  @SerializedName("firstname")
  @Expose
  private String firstname;
  @SerializedName("lastname")
  @Expose
  private String lastname;
  @SerializedName("parent_id")
  @Expose
  private Integer parentId;
  @SerializedName("postcode")
  @Expose
  private String postcode;
  @SerializedName("street")
  @Expose
  private List<String> street = null;
  @SerializedName("telephone")
  @Expose
  private String telephone;
  @SerializedName("region")
  @Expose
  private String region;
  @SerializedName("region_code")
  @Expose
  private String regionCode;
  @SerializedName("region_id")
  @Expose
  private Integer regionId;

  public String getAddressType() {
    return addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public Integer getCustomerAddressId() {
    return customerAddressId;
  }

  public void setCustomerAddressId(Integer customerAddressId) {
    this.customerAddressId = customerAddressId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public List<String> getStreet() {
    return street;
  }

  public void setStreet(List<String> street) {
    this.street = street;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public Integer getRegionId() {
    return regionId;
  }

  public void setRegionId(Integer regionId) {
    this.regionId = regionId;
  }
}
