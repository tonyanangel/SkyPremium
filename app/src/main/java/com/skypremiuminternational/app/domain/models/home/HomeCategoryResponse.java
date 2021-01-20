package com.skypremiuminternational.app.domain.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class HomeCategoryResponse implements Serializable {

  @SerializedName("banners")
  @Expose
  private List<Banner> banners = new ArrayList<>();

  public List<Banner> getBanners() {
    return banners;
  }

  public void setBanners(List<Banner> banners) {
    this.banners = banners;
  }
}
