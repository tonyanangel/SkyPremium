package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class SettingNotificationRequest {



  @SerializedName("member_id")
  String memberId;

  @SerializedName("settings")
  SettingDataRequest settings;


  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public SettingDataRequest getSettings() {
    return settings;
  }

  public void setSettings(SettingDataRequest settings) {
    this.settings = settings;
  }
}
