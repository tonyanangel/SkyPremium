package com.skypremiuminternational.app.domain.models.crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class CrmTokenRequest {

  @SerializedName("grant_type")
  @Expose
  private String grantType;
  @SerializedName("client_id")
  @Expose
  private String clientId;
  @SerializedName("client_secret")
  @Expose
  @Nullable
  private String clientSecret ="";
  @SerializedName("username")
  @Expose
  private String username;
  @SerializedName("password")
  @Expose
  private String password;
  @SerializedName("platform")
  @Expose
  private String platform;


  public String getGrantType() {
    return grantType;
  }

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
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

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }
}
