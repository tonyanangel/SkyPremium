package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;

import java.util.List;

public class NotificationRequest {


  @SerializedName("member_id")
  String memberId;
  @SerializedName("device_id")
  String deviceId;
  @SerializedName("notifications")
  List<NotificationItem> notifications;

  public List<NotificationItem> getNotifications() {
    return notifications;
  }

  public void setNotifications(List<NotificationItem> notifications) {
    this.notifications = notifications;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
}