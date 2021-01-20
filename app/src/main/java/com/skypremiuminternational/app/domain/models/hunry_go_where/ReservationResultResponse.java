package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationResultResponse {
  @SerializedName("code")
  String code;

  @SerializedName("data")
  List<ReserveResultDataItem> reserveResultDataItem;

  @SerializedName("message")
  String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<ReserveResultDataItem> getReserveResultDataItem() {
    return reserveResultDataItem;
  }

  public void setReserveResultDataItem(List<ReserveResultDataItem> reserveResultDataItem) {
    this.reserveResultDataItem = reserveResultDataItem;
  }
}
