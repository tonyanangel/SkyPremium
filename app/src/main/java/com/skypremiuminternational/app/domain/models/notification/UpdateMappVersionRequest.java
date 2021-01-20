package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class UpdateMappVersionRequest {
  @SerializedName("member_id")
  String memberId;
  @SerializedName("mapp_version")
  String mappVersion;


  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getMappVersion() {
    return mappVersion;
  }

  public void setMappVersion(String mappVersion) {
    this.mappVersion = mappVersion;
  }
}
