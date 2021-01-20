package com.skypremiuminternational.app.domain.models.crm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrmTokenResponse {

  @SerializedName("access_token")
  @Expose
  String accessToken;
  @SerializedName("expires_in")
  @Expose
  int expiresIn;
  @SerializedName("token_type")
  @Expose
  String tokenType;
  @SerializedName("scope")
  @Expose
  String scope;
  @SerializedName("refresh_token")
  @Expose
  String refreshToken;
  @SerializedName("refresh_expires_in")
  @Expose
  long refreshExpiresIn;
  @SerializedName("download_token")
  @Expose
  String downloadToken;


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public long getRefreshExpiresIn() {
    return refreshExpiresIn;
  }

  public void setRefreshExpiresIn(long refreshExpiresIn) {
    this.refreshExpiresIn = refreshExpiresIn;
  }

  public String getDownloadToken() {
    return downloadToken;
  }

  public void setDownloadToken(String downloadToken) {
    this.downloadToken = downloadToken;
  }
}
