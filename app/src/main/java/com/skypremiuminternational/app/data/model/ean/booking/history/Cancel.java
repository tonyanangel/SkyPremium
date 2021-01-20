package com.skypremiuminternational.app.data.model.ean.booking.history;

import com.google.gson.annotations.SerializedName;

public class Cancel {

  @SerializedName("method")
  private String method;

  @SerializedName("href")
  private String href;

  public void setMethod(String method) {
    this.method = method;
  }

  public String getMethod() {
    return method;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getHref() {
    return href;
  }
}