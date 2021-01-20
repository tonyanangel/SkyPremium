package com.skypremiuminternational.app.data.model.ean.suggestion;

import com.google.gson.annotations.SerializedName;

public class SuggestionsItem {

  @SerializedName("score")
  private int score;

  @SerializedName("suggestion")
  private String suggestion;

  @SerializedName("id")
  private String id;

  public void setScore(int score) {
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public void setSuggestion(String suggestion) {
    this.suggestion = suggestion;
  }

  public String getSuggestion() {
    return suggestion;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}