package com.skypremiuminternational.app.domain.models.popup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FirstTimePopup {


  @SerializedName("first_login")
  @Expose
  boolean isFirstLogin;

  @SerializedName("popup")
  @Expose
  List<PopupItem> popupItems;


  public boolean isFirstTime() {
    return isFirstLogin;
  }

  public void setFirstTime(boolean isFirstLogin) {
    isFirstLogin = isFirstLogin;
  }

  public List<PopupItem> getPopupItem() {
    return popupItems;
  }

  public void setPopupItem(List<PopupItem> popupItems) {
    this.popupItems = popupItems;
  }


}
