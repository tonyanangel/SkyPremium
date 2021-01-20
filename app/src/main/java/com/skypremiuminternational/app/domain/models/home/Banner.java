package com.skypremiuminternational.app.domain.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by johnsonmaung on 11/17/17.
 */

public class Banner implements Serializable {

  @SerializedName("banner_id")
  @Expose
  private String bannerId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("order_banner")
  @Expose
  private String orderBanner;
  @SerializedName("is_main")
  @Expose
  private String isMain;
  @SerializedName("target_url")
  @Expose
  private String targetUrl;
  @SerializedName("header")
  @Expose
  private String header;
  @SerializedName("main_content")
  @Expose
  private String mainContent;
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("image_alt")
  @Expose
  private Object imageAlt;

  public Banner() {
  }

  public String getBannerId() {
    return bannerId;
  }

  public void setBannerId(String bannerId) {
    this.bannerId = bannerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrderBanner() {
    return orderBanner;
  }

  public void setOrderBanner(String orderBanner) {
    this.orderBanner = orderBanner;
  }

  public String getIsMain() {
    return isMain;
  }

  public void setIsMain(String isMain) {
    this.isMain = isMain;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getMainContent() {
    return mainContent;
  }

  public void setMainContent(String mainContent) {
    this.mainContent = mainContent;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Object getImageAlt() {
    return imageAlt;
  }

  public void setImageAlt(Object imageAlt) {
    this.imageAlt = imageAlt;
  }
}
