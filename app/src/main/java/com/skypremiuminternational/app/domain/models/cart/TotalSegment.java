package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class TotalSegment implements Serializable {
  @SerializedName("code")
  @Expose
  private String code;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("value")
  @Expose
  private String value;
  @SerializedName("extension_attributes")
  @Expose
  private TotalSegmentExtensionAttributes extensionAttributes;
  @SerializedName("area")
  @Expose
  private String area;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public TotalSegmentExtensionAttributes getExtensionAttributes() {
    return extensionAttributes;
  }

  public void setExtensionAttributes(
      TotalSegmentExtensionAttributes extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
