package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class OneSignalDeviceDataRequest {
  @SerializedName("member_id")
  String member_id;

  @SerializedName("dataFromOneSignal")
  DataFromOneSignal dataFromOneSignal;


  public String getMember_id() {
    return member_id;
  }

  public void setMember_id(String member_id) {
    this.member_id = member_id;
  }

  public DataFromOneSignal getDataFromOneSignal() {
    return dataFromOneSignal;
  }

  public void setDataFromOneSignal(DataFromOneSignal dataFromOneSignal) {
    this.dataFromOneSignal = dataFromOneSignal;
  }
}
