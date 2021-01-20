package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class Policies {

  @SerializedName("know_before_you_go")
  private String knowBeforeYouGo;

  public void setKnowBeforeYouGo(String knowBeforeYouGo) {
    this.knowBeforeYouGo = knowBeforeYouGo;
  }

  public String getKnowBeforeYouGo() {
    return knowBeforeYouGo;
  }
}