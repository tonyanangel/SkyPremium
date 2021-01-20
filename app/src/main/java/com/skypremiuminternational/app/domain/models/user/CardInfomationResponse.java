package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardInfomationResponse {


  @SerializedName("code")
  @Expose
  private String code;
  @SerializedName("data")
  @Expose
  private List<CardData> data;


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<CardData> getData() {
    return data;
  }

  public void setData(List<CardData> data) {
    this.data = data;
  }
}
