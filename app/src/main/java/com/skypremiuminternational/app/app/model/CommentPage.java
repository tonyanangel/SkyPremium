package com.skypremiuminternational.app.app.model;

public class CommentPage {
  int numberPage;
  boolean  isHightLight;


  public boolean isHightLight() {
    return isHightLight;
  }

  public void setHightLight(boolean hightLight) {
    isHightLight = hightLight;
  }

  public int getNumberPage() {
    return numberPage;
  }

  public void setNumberPage(int numberPage) {
    this.numberPage = numberPage;
  }
}
