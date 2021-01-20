package com.skypremiuminternational.app.domain.models.myOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.myOrder.detail.OrderDetailAddExtenshion;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 3/5/18.
 */

public class Address implements Serializable {
  @SerializedName("address_type")
  @Expose
  private String addressType;
  @SerializedName("city")
  @Expose
  private String city;
  @SerializedName("country_id")
  @Expose
  private String countryId;
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
  @SerializedName("company")
  @Expose
  private String company;
  @SerializedName("extension_attributes")
  @Expose
  private OrderDetailAddExtenshion orderDetailAddExtenshion;

  private String region;
  private String region_code;
  private String region_id;

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

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

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
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

  public String getRegion_id() {
    return region_id;
  }

  public void setRegion_id(String region_id) {
    this.region_id = region_id;
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

  public OrderDetailAddExtenshion getOrderDetailAddExtenshion() {
    return orderDetailAddExtenshion;
  }

  public void setOrderDetailAddExtenshion(
      OrderDetailAddExtenshion orderDetailAddExtenshion) {
    this.orderDetailAddExtenshion = orderDetailAddExtenshion;
  }
}
