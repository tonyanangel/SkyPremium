package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UpdatePasswordRequest implements Serializable {

  @SerializedName("customer")
  @Expose
  private Customer customer;
  @SerializedName("passwordHash")
  @Expose
  private String passwordHash;
  private transient String member_number;

  public UpdatePasswordRequest(Customer customer, String passwordHash, String member_number) {
    this.customer = customer;
    this.passwordHash = passwordHash;
    this.member_number = member_number;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getMember_number() {
    return member_number;
  }

  public void setMember_number(String member_number) {
    this.member_number = member_number;
  }

  public static class Customer implements Serializable {

    @SerializedName("website_id")
    @Expose
    private Integer websiteId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes =
        null;

    public Customer(Integer websiteId, String firstname, String lastname, String email,
                    List<CustomAttribute> customAttributes) {
      this.websiteId = websiteId;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
      this.customAttributes = customAttributes;
    }

    public Integer getWebsiteId() {
      return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
      this.websiteId = websiteId;
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

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public List<CustomAttribute> getCustomAttributes() {
      return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
      this.customAttributes = customAttributes;
    }
  }

  public static class CustomAttribute implements Serializable {

    public CustomAttribute(String attributeCode, String value) {
      this.attributeCode = attributeCode;
      this.value = value;
    }

    @SerializedName("attribute_code")
    @Expose
    private String attributeCode;
    @SerializedName("value")
    @Expose
    private String value;

    public String getAttributeCode() {
      return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
      this.attributeCode = attributeCode;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}