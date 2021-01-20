package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationItem {


  @SerializedName("id")
  String id;

  @SerializedName("notification_id")
  String notificationId;

  @SerializedName("title")
  String title;

  @SerializedName("body")
  String body;

  @SerializedName("subtitle")
  String subtitle;

  @SerializedName("launch_url")
  String launchUrl;

  @SerializedName("additional_data")
  AdditionData additionalData;

  @SerializedName("action_buttons")
  List<SkyActionButton> actionButtons;

  @SerializedName("raw_payload")
  String rawPayload;

  @SerializedName("is_read")
  boolean isRead;

  @SerializedName("receive_time")
  String receiveTime;


  String memberNumber;

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean read) {
    isRead = read;
  }

  public String getMemberNumber() {
    return memberNumber;
  }

  public void setMemberNumber(String memberNumber) {
    this.memberNumber = memberNumber;
  }

  private boolean isChecked;


  public boolean isChecked() {
    return this.isChecked;
  }

  public void setChecked(boolean checked) {
    this.isChecked = checked;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getLaunchUrl() {
    return launchUrl;
  }

  public void setLaunchUrl(String launchUrl) {
    this.launchUrl = launchUrl;
  }

  public AdditionData getAdditionalData() {
    return additionalData;
  }

  public void setAdditionalData(AdditionData additionalData) {
    this.additionalData = additionalData;
  }

  public List<SkyActionButton> getActionButtons() {
    return actionButtons;
  }

  public void setActionButtons(List<SkyActionButton> actionButtons) {
    this.actionButtons = actionButtons;
  }

  public String getRawPayload() {
    return rawPayload;
  }

  public void setRawPayload(String rawPayload) {
    this.rawPayload = rawPayload;
  }

  public boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(boolean isRead) {
    this.isRead = isRead;
  }

  public String getReceiveTime() {
    return receiveTime;
  }

  public void setReceiveTime(String receiveTime) {
    this.receiveTime = receiveTime;
  }
}
