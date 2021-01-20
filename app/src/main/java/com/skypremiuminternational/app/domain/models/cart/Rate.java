package com.skypremiuminternational.app.domain.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class Rate {
  @SerializedName("percent")
  @Expose
  private String percent;
  @SerializedName("title")
  @Expose
  private String title;

  public String getPercent() {
    return percent;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
