package com.skypremiuminternational.app.data.model.ean.content;

import com.google.gson.annotations.SerializedName;

public class ImagesItem {

  @SerializedName("hero_image")
  private boolean heroImage;

  @SerializedName("caption")
  private String caption;

  @SerializedName("links")
  private Links links;

  @SerializedName("category")
  private int category;

  public void setHeroImage(boolean heroImage) {
    this.heroImage = heroImage;
  }

  public boolean isHeroImage() {
    return heroImage;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getCaption() {
    return caption;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Links getLinks() {
    return links;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public int getCategory() {
    return category;
  }
}