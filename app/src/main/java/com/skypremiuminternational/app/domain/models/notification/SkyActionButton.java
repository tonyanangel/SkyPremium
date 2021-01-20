package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;
import com.onesignal.OSNotificationPayload;

import org.json.JSONObject;

public class SkyActionButton {

  @SerializedName("id")
  String id;

  @SerializedName("text")
  String text;

  @SerializedName("icon")
  String icon;


  public SkyActionButton() {
  }

  public SkyActionButton(String id, String text, String icon) {
    this.id = id;
    this.text = text;
    this.icon = icon;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
