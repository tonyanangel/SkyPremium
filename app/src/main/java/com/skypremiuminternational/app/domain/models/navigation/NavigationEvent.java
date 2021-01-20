package com.skypremiuminternational.app.domain.models.navigation;

/**
 * Created by johnsonmaung on 10/1/17.
 */

public class NavigationEvent {
  int deeplink;

  public NavigationEvent(int deeplink) {
    this.deeplink = deeplink;
  }

  public int getDeeplink() {
    return deeplink;
  }

  public void setDeeplink(int deeplink) {
    this.deeplink = deeplink;
  }
}
