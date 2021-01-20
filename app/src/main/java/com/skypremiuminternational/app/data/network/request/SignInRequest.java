package com.skypremiuminternational.app.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignInRequest implements Serializable {

  @SerializedName("username")
  @Expose
  private String username;
  @SerializedName("password")
  @Expose
  private String password;

  /**
   * No args constructor for use in serialization
   */
  public SignInRequest() {
  }

  /**
   * @param username
   * @param password
   */
  public SignInRequest(String username, String password) {
    super();
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}