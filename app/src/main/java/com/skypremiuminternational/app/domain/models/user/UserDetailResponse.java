package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 11/12/17.
 */

public class UserDetailResponse implements Serializable {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("group_id")
  @Expose
  private Integer groupId;
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
  private Integer gender;
  @SerializedName("store_id")
  @Expose
  private Integer storeId;
  @SerializedName("website_id")
  @Expose
  private Integer websiteId;
  @SerializedName("addresses")
  @Expose
  private List<Address> addresses = null;
  @SerializedName("disable_auto_group_change")
  @Expose
  private Integer disableAutoGroupChange;
  @SerializedName("custom_attributes")
  @Expose
  private List<CustomAttribute> customAttributes =
      null;

  private boolean isActive = false;
  private String salutation;
  private String referralCode;
  private String avatar;
  private Double loyaltyPoints;

  public String getLoyaltyPointExpiryDate() {
    return loyaltyPointExpiryDate;
  }

  public void setLoyaltyPointExpiryDate(String loyaltyPointExpiryDate) {
    this.loyaltyPointExpiryDate = loyaltyPointExpiryDate;
  }

  private String loyaltyPointExpiryDate;
  private long daysLeft;
  private boolean showMemberExpiryPrompt;
  private String expiryDate;

  public UserDetailResponse() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
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

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Integer getStoreId() {
    return storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public Integer getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(Integer websiteId) {
    this.websiteId = websiteId;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Integer getDisableAutoGroupChange() {
    return disableAutoGroupChange;
  }

  public void setDisableAutoGroupChange(Integer disableAutoGroupChange) {
    this.disableAutoGroupChange = disableAutoGroupChange;
  }

  public List<CustomAttribute> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(List<CustomAttribute> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getSalutation() {
    return salutation;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }

  public void setReferralCode(String referralCode) {
    this.referralCode = referralCode;
  }

  public String getReferralCode() {
    return referralCode;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setLoyaltyPoints(Double loyaltyPoints) {
    this.loyaltyPoints = loyaltyPoints;
  }

  public Double getLoyaltyPoints() {
    return loyaltyPoints;
  }

  public void setDaysLeft(long daysLeft) {
    this.daysLeft = daysLeft;
  }

  public long getDaysLeft() {
    return daysLeft;
  }

  public void setShowMemberExpiryPrompt(boolean showMemberExpiryPrompt) {
    this.showMemberExpiryPrompt = showMemberExpiryPrompt;
  }

  public boolean isShowMemberExpiryPrompt() {
    return showMemberExpiryPrompt;
  }

  public void setExpiryDate(String formattedExpiryDate) {
    this.expiryDate = formattedExpiryDate;
  }

  public String getExpiryDate() {
    return expiryDate;
  }


  public String getMemberNumber(){
    for(CustomAttribute customAttribute :  this.getCustomAttributes()){
      if(customAttribute.getAttributeCode().equalsIgnoreCase(CustomAttribute.MEMBER_NUMBER)){
        return customAttribute.getValue().toString();
      }
    }

    return "";
  }
}
