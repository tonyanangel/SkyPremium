package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationDeleteRequest {
  @SerializedName("notifications")
  List<UpdateDelItem> updateItem;


  public List<UpdateDelItem> getUpdateItem() {
    return updateItem;
  }

  public void setUpdateItem(List<UpdateDelItem> updateItem) {
    this.updateItem = updateItem;
  }
}
