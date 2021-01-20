package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class CheckFirstLoginData {
  @SerializedName("is_first_login_mapp")
  boolean isFirstLoginMapp;

  public boolean isFirstLoginMapp() {
    return isFirstLoginMapp;
  }

  public void setIsFirstLoginMapp(boolean isFirstLoginMapp) {
    this.isFirstLoginMapp = isFirstLoginMapp;
  }
}
