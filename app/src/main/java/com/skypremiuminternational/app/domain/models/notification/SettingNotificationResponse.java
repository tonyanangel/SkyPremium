package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingNotificationResponse {



  @SerializedName("code")
  String code;
  @SerializedName("data")
  SettingData data;
  @SerializedName("is_login")
  boolean is_login;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public SettingData getData() {
    return data;
  }

  public void setData(SettingData data) {
    this.data = data;
  }

  public boolean isIs_login() {
    return is_login;
  }

  public void setIs_login(boolean is_login) {
    this.is_login = is_login;
  }
}
