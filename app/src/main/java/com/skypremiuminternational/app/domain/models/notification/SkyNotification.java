package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class SkyNotification {

  @SerializedName("notification_id")
  String notificationId;
  @SerializedName("title")
  String title;
  @SerializedName("body")
  String body;
  @SerializedName("launch_url")
  String launchUrl;
  @SerializedName("additional_data")
  AdditionData additionalData;
  @SerializedName("receive_time")
  String receiveTime;

  boolean isRead;

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean read) {
    isRead = read;
  }

  public String getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(String notificationId) {
    this.notificationId = notificationId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public AdditionData getAdditionalData() {
    return additionalData;
  }

  public void setAdditionalData(AdditionData additionalData) {
    this.additionalData = additionalData;
  }

  public String getReceiveTime() {
    return receiveTime;
  }

  public void setReceiveTime(String receiveTime) {
    this.receiveTime = receiveTime;
  }

  public String getLaunchUrl() {
    return launchUrl;
  }

  public void setLaunchUrl(String launchUrl) {
    this.launchUrl = launchUrl;
  }
}
