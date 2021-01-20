package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class FirstTimeToTrueRequest {


    @SerializedName("member_id")
    String memberId ;
    @SerializedName("is_first_login_mapp")
    boolean isLogin ;


  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public boolean getIsLogin() {
    return isLogin;
  }

  public void setIsLogin(boolean isLogin) {
    this.isLogin = isLogin;
  }
}
