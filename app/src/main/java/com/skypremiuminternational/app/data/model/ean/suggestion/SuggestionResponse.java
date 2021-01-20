package com.skypremiuminternational.app.data.model.ean.suggestion;

import com.google.gson.annotations.SerializedName;

public class SuggestionResponse {

  @SerializedName("suggest")
  private Suggest suggest;

  @SerializedName("status")
  private Status status;

  public void setSuggest(Suggest suggest) {
    this.suggest = suggest;
  }

  public Suggest getSuggest() {
    return suggest;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Status getStatus() {
    return status;
  }
}