package com.skypremiuminternational.app.domain.models.comment_rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductReviewResponse {

  @SerializedName("items")
  @Expose
  List<Comment> commentList;

  @SerializedName("total_count")
  @Expose
  String totalCount;


  public  List<Comment> getCommentList() {
    return commentList;
  }

  public void setCommentList( List<Comment> reviewItem) {
    this.commentList = reviewItem;
  }

  public String getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(String totalCount) {
    this.totalCount = totalCount;
  }
}
