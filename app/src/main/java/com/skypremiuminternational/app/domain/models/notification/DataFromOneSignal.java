package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class DataFromOneSignal {


  /*  "dataFromOneSignal" : {
        "first_session" : 1603943160,
        "last_session" :  1603976515,
        "session_count": 3,
        "total_usage_duration" : 77
    }*/
  @SerializedName("first_session")
  long firstSession;
  @SerializedName("last_session")
  long lastSession;
  @SerializedName("session_count")
  long sessionCount;
  @SerializedName("total_usage_duration")
  long totalUsageDuration;


  public long getFirstSession() {
    return firstSession;
  }

  public void setFirstSession(long firstSession) {
    this.firstSession = firstSession;
  }

  public long getLastSession() {
    return lastSession;
  }

  public void setLastSession(long lastSession) {
    this.lastSession = lastSession;
  }

  public long getSessionCount() {
    return sessionCount;
  }

  public void setSessionCount(long sessionCount) {
    this.sessionCount = sessionCount;
  }

  public long getTotalUsageDuration() {
    return totalUsageDuration;
  }

  public void setTotalUsageDuration(long totalUsageDuration) {
    this.totalUsageDuration = totalUsageDuration;
  }
}
