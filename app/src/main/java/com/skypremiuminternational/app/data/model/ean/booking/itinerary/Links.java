package com.skypremiuminternational.app.data.model.ean.booking.itinerary;

import com.google.gson.annotations.SerializedName;
import com.skypremiuminternational.app.data.model.ean.booking.history.Cancel;

public class Links {

  @SerializedName("cancel")
  private Cancel cancel;

  public void setCancel(Cancel cancel) {
    this.cancel = cancel;
  }

  public Cancel getCancel() {
    return cancel;
  }
}