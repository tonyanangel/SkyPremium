package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class CheckLoginResponse {


  @SerializedName("code")
  String code;
  @SerializedName("data")
  CheckFirstLoginData data;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public CheckFirstLoginData getData() {
    return data;
  }

  public void setData(CheckFirstLoginData data) {
    this.data = data;
  }


}
