package com.skypremiuminternational.app.domain.models.nationality;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sandi on 11/16/17.
 */

public class Nationality implements Serializable {

  @SerializedName("value")
  @Expose
  private Integer id;
  @SerializedName("label")
  @Expose
  private String name;

  public Nationality() {
  }

  public Nationality(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
