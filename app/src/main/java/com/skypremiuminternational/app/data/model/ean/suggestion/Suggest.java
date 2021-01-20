package com.skypremiuminternational.app.data.model.ean.suggestion;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Suggest {

  @SerializedName("found")
  private int found;

  @SerializedName("query")
  private String query;

  @SerializedName("suggestions")
  private List<SuggestionsItem> suggestions;

  public void setFound(int found) {
    this.found = found;
  }

  public int getFound() {
    return found;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public void setSuggestions(List<SuggestionsItem> suggestions) {
    this.suggestions = suggestions;
  }

  public List<SuggestionsItem> getSuggestions() {
    return suggestions;
  }
}