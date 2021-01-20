package com.skypremiuminternational.app.domain.models.membership;

/**
 * Created by sandi on 10/1/17.
 */

public class Title {

  String title;
  int icon, icon_expended;

  public Title(String title, int icon, int icon_expended) {
    this.title = title;
    this.icon = icon;
    this.icon_expended = icon_expended;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getIcon() {
    return icon;
  }

  public void setIcon(int icon) {
    this.icon = icon;
  }

  public int getIcon_expended() {
    return icon_expended;
  }

  public void setIcon_expended(int icon_expended) {
    this.icon_expended = icon_expended;
  }
}
