package com.skypremiuminternational.app.data.model.billingaddress;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AddBillingAddressRequest {

  @SerializedName("billingAddress")
  private BillingAddress billingAddress;

  public void setBillingAddress(BillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public BillingAddress getBillingAddress() {
    return billingAddress;
  }

  public static class BillingAddress {

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

    @SerializedName("company")
    private String company;

    @SerializedName("salutation")
    private String salutation;

    @SerializedName("region")
    private String region;

    @SerializedName("default_billing")
    private boolean defaultBilling;

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

    public void setCompany(String company) {
      this.company = company;
    }

    public String getCompany() {
      return company;
    }

    public void setSalutation(String salutation) {
      this.salutation = salutation;
    }

    public String getSalutation() {
      return salutation;
    }

    public void setRegion(String region) {
      this.region = region;
    }

    public String getRegion() {
      return region;
    }

    public void setDefaultBilling(boolean defaultBilling) {
      this.defaultBilling = defaultBilling;
    }

    public boolean isDefaultBilling() {
      return defaultBilling;
    }

    public void setCountryId(String countryId) {
      this.countryId = countryId;
    }

    public String getCountryId() {
      return countryId;
    }
  }
}