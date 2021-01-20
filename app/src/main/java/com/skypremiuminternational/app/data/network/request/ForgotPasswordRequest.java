package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForgotPasswordRequest implements Serializable {

  @SerializedName("template")
  @Expose
  private String template;
  @SerializedName("email")
  @Expose
  private String email;

  public ForgotPasswordRequest(String email) {
    this.template = "email_reset";
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}