package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReserveHistoryRespone {

  @SerializedName("items")
  List<ReserveHistoryItem> reserveHistoryItemList;


  public List<ReserveHistoryItem> getReserveHistoryItemList() {
    return reserveHistoryItemList;
  }

  public void setReserveHistoryItemList(List<ReserveHistoryItem> reserveHistoryItemList) {
    this.reserveHistoryItemList = reserveHistoryItemList;
  }
}
