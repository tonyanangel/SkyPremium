package com.skypremiuminternational.app.domain.models.notification;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {
  @SerializedName("code")
  String code;

  @SerializedName("data")
  List<NotificationItem> datas;


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<NotificationItem> getDatas() {
    return datas;
  }

  public void setDatas(List<NotificationItem> datas) {
    this.datas = datas;
  }
}
