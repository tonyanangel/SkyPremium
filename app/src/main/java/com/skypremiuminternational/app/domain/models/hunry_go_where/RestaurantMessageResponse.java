package com.skypremiuminternational.app.domain.models.hunry_go_where;

import com.google.gson.annotations.SerializedName;

public class RestaurantMessageResponse {

  @SerializedName("response")
  RestaurantMessage message;

  public RestaurantMessage getMessage() {
    return message;
  }

  public void setMessage(RestaurantMessage message) {
    this.message = message;
  }
}
