package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.user.Address;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class CartDetailCustomer implements Serializable {
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("group_id")
  @Expose
  private int groupId;
  @SerializedName("default_billing")
  @Expose
  private String defaultBilling;
  @SerializedName("default_shipping")
  @Expose
  private String defaultShipping;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("created_in")
  @Expose
  private String createdIn;
  @SerializedName("dob")
  @Expose
  private String dob;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("firstname")
  @Expose
  private String firstname;
  @SerializedName("lastname")
  @Expose
  private String lastname;
  @SerializedName("prefix")
  @Expose
  private String prefix;
  @SerializedName("gender")
  @Expose
  private int gender;
  @SerializedName("store_id")
  @Expose
  private int storeId;
  @SerializedName("website_id")
  @Expose
  private int websiteId;
  @SerializedName("addresses")
  @Expose
  private List<Address> addresses = null;
  @SerializedName("disable_auto_group_change")
  @Expose
  private int disableAutoGroupChange;
  @SerializedName("custom_attributes")
  @Expose
  private List<CustomAttribute> customAttributes = null;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getDefaultBilling() {
    return defaultBilling;
  }

  public void setDefaultBilling(String defaultBilling) {
    this.defaultBilling = defaultBilling;
  }

  public String getDefaultShipping() {
    return defaultShipping;
  }

  public void setDefaultShipping(String defaultShipping) {
    this.defaultShipping = defaultShipping;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCreatedIn() {
    return createdIn;
  }

  public void setCreatedIn(String createdIn) {
    this.createdIn = createdIn;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public int getStoreId() {
    return storeId;
  }

  public void setStoreId(int storeId) {
    this.storeId = storeId;
  }

  public int getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(int websiteId) {
    this.websiteId = websiteId;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public int getDisableAutoGroupChange() {
    return disableAutoGroupChange;
  }

  public void setDisableAutoGroupChange(int disableAutoGroupChange) {
    this.disableAutoGroupChange = disableAutoGroupChange;
  }

  public List<CustomAttribute> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(List<CustomAttribute> customAttributes) {
    this.customAttributes = customAttributes;
  }
}
