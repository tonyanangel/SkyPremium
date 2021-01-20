package com.skypremiuminternational.app.domain.models.comment_rating;

import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RatingResult implements Serializable {

  @SerializedName("status")
  @Expose
  private String status;

  @SerializedName("message")
  @Expose
  private String message;


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
