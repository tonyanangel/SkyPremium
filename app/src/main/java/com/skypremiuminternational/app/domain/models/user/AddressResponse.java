package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 3/6/18.
 */

public class AddressResponse implements Serializable {
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("customer_id")
  @Expose
  private Integer customerId;
  @SerializedName("region")
  @Expose
  private Region region;
  @SerializedName("region_id")
  @Expose
  private Integer regionId;
  @SerializedName("country_id")
  @Expose
  private String countryId;
  @SerializedName("street")
  @Expose
  private List<String> street = null;
  @SerializedName("telephone")
  @Expose
  private String telephone;
  @SerializedName("postcode")
  @Expose
  private String postcode;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("firstname")
  @Expose
  private String firstname;
  @SerializedName("lastname")
  @Expose
  private String lastname;
  @SerializedName("custom_attributes")
  @Expose
  private List<CustomAttribute> customAttributes;
  @SerializedName("default_shipping")
  @Expose
  private Boolean defaultShipping;
  @SerializedName("default_billing")
  @Expose
  private Boolean defaultBilling;
  private String countryValue;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Integer getRegionId() {
    return regionId;
  }

  public void setRegionId(Integer regionId) {
    this.regionId = regionId;
  }

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
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

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
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

  public List<CustomAttribute> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(
      List<CustomAttribute> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public Boolean getDefaultShipping() {
    return defaultShipping;
  }

  public void setDefaultShipping(Boolean defaultShipping) {
    this.defaultShipping = defaultShipping;
  }

  public Boolean getDefaultBilling() {
    return defaultBilling;
  }

  public void setDefaultBilling(Boolean defaultBilling) {
    this.defaultBilling = defaultBilling;
  }

  public String getCountryValue() {
    return countryValue;
  }

  public void setCountryValue(String countryValue) {
    this.countryValue = countryValue;
  }
}
