package com.skypremiuminternational.app.domain.models.faq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandi on 11/15/17.
 */

public class FaqResponse implements Serializable {

  @SerializedName("items")
  @Expose
  private List<FaqItem> items = new ArrayList<>();
  @SerializedName("total_records")
  @Expose
  private Integer totalRecords;

  public FaqResponse() {
  }

  public List<FaqItem> getItems() {
    return items;
  }

  public void setItems(List<FaqItem> items) {
    this.items = items;
  }

  public Integer getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
  }
}
