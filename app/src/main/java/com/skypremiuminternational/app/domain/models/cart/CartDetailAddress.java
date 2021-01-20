package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailAddress implements Serializable {
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("region")
  @Expose
  private String region;
  @SerializedName("region_id")
  @Expose
  private String regionId;
  @SerializedName("region_code")
  @Expose
  private String regionCode;
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
  @SerializedName("customer_id")
  @Expose
  private int customerId;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("same_as_billing")
  @Expose
  private int sameAsBilling;
  @SerializedName("save_in_address_book")
  @Expose
  private int saveInAddressBook;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
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

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getSameAsBilling() {
    return sameAsBilling;
  }

  public void setSameAsBilling(int sameAsBilling) {
    this.sameAsBilling = sameAsBilling;
  }

  public int getSaveInAddressBook() {
    return saveInAddressBook;
  }

  public void setSaveInAddressBook(int saveInAddressBook) {
    this.saveInAddressBook = saveInAddressBook;
  }
}
