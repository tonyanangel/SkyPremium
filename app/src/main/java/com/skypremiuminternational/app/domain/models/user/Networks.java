package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Networks {


  @SerializedName("available")
  @Expose
  private List<String> available;
  @SerializedName("preferred")
  @Expose
  private String preferred;

  public List<String> getAvailable() {
    return available;
  }

  public void setAvailable(List<String> available) {
    this.available = available;
  }

  public String getPreferred() {
    return preferred;
  }

  public void setPreferred(String preferred) {
    this.preferred = preferred;
  }
}
