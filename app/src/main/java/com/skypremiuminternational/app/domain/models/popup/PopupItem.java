package com.skypremiuminternational.app.domain.models.popup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopupItem{

  @SerializedName("url")
  @Expose
  String url;

  @SerializedName("type")
  @Expose
  String type;

  @SerializedName("video_id")
  @Expose
  String videoId;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getVideoId() {
    return videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }
}