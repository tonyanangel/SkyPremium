package com.skypremiuminternational.app.domain.models.travel;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class TravelProduct {

  String title;
  String subTitle;
  Integer img;

  public TravelProduct(String title, String subTitle, Integer img) {
    this.title = title;
    this.subTitle = subTitle;
    this.img = img;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public Integer getImg() {
    return img;
  }

  public void setImg(Integer img) {
    this.img = img;
  }
}
