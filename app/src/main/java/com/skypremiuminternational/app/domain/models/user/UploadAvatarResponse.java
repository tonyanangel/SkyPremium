package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aeindraaung on 1/23/18.
 */

public class UploadAvatarResponse implements Serializable {
  @SerializedName("success")
  @Expose
  private boolean isSuccess;
  @SerializedName("message")
  @Expose
  private String message;

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
