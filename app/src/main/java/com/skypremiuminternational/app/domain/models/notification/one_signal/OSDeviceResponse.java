package com.skypremiuminternational.app.domain.models.notification.one_signal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OSDeviceResponse {
  @SerializedName("total_count")
  long totalCount;
  @SerializedName("offset")
  long offset;
  @SerializedName("limit")
  long limit;
  @SerializedName("players")
  List<OSPlayer> playerList;





}
