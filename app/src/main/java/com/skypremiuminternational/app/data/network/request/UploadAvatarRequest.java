package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aeindraaung on 1/22/18.
 */

public class UploadAvatarRequest implements Serializable {
  @SerializedName("customer")
  @Expose
  private Customer customer;

  public UploadAvatarRequest(Customer customer) {
    this.customer = customer;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public static class Customer implements Serializable {
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;

    public Customer(List<CustomAttribute> customAttributes) {
      this.customAttributes = customAttributes;
    }

    public List<CustomAttribute> getCustomAttributes() {
      return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
      this.customAttributes = customAttributes;
    }
  }

  public static class CustomAttribute implements Serializable {
    @SerializedName("attribute_code")
    @Expose
    private String attributeCode;
    @SerializedName("value")
    @Expose
    private Value value;

    public CustomAttribute(String attributeCode, Value value) {
      this.attributeCode = attributeCode;
      this.value = value;
    }

    public String getAttributeCode() {
      return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
      this.attributeCode = attributeCode;
    }

    public Value getValue() {
      return value;
    }

    public void setValue(Value value) {
      this.value = value;
    }
  }

  public static class Value implements Serializable {
    @SerializedName("base64EncodedData")
    @Expose
    private String base64EncodedData;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;

    public Value(String base64EncodedData, String type, String name) {
      this.base64EncodedData = base64EncodedData;
      this.type = type;
      this.name = name;
    }

    public String getBase64EncodedData() {
      return base64EncodedData;
    }

    public void setBase64EncodedData(String base64EncodedData) {
      this.base64EncodedData = base64EncodedData;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
