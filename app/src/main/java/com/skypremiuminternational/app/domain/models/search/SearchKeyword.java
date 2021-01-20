package com.skypremiuminternational.app.domain.models.search;

/**
 * Created by johnsonmaung on 10/1/17.
 */

public class SearchKeyword {
  String text;
  String sortBy;

  public SearchKeyword(String text) {
    this.text = text;
  }

  public SearchKeyword(String text,String sortBy) {
    this.text = text;
    this.sortBy =  sortBy;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }
}
