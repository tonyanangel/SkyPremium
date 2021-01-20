package com.skypremiuminternational.app.domain.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchHomeResponse implements Serializable {

  @SerializedName("items")
  @Expose
  private List<Item> items = new ArrayList<>();

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public class Item implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = 8455279856041727905L;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }
}

