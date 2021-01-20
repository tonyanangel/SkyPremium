package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationTimeResponse {

  @SerializedName("response")
  ReserveTime reserveTime;

  public ReserveTime getReserveTime() {
    return reserveTime;
  }

  public void setReserveTime(ReserveTime reserveTime) {
    this.reserveTime = reserveTime;
  }
}
