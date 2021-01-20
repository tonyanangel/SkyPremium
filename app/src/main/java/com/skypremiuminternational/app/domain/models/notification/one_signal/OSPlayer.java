package com.skypremiuminternational.app.domain.models.notification.one_signal;

import com.google.gson.annotations.SerializedName;

public class OSPlayer {

  @SerializedName("last_active")
  long lastActive;
  @SerializedName("created_at")
  long createdAt;
  @SerializedName("session_count")
  long sessionCount;
  @SerializedName("playtime")
  long playtime;


  public long getLastActive() {
    return lastActive;
  }

  public void setLastActive(long lastActive) {
    this.lastActive = lastActive;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getSessionCount() {
    return sessionCount;
  }

  public void setSessionCount(long sessionCount) {
    this.sessionCount = sessionCount;
  }

  public long getPlaytime() {
    return playtime;
  }

  public void setPlaytime(long playtime) {
    this.playtime = playtime;
  }
}
