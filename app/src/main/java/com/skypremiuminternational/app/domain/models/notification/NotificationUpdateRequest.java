package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationUpdateRequest {
  @SerializedName("notifications")
  List<UpdateReadItem> updateItem;


  public List<UpdateReadItem> getUpdateItem() {
    return updateItem;
  }

  public void setUpdateItem(List<UpdateReadItem> updateItem) {
    this.updateItem = updateItem;
  }
}
