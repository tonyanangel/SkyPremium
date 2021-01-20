package com.skypremiuminternational.app.domain.models.comment_rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewItem {

  @SerializedName("review_id")
  @Expose
  String reviewId;
  @SerializedName("detail_id")
  @Expose
  String detailId;
  @SerializedName("title")
  @Expose
  String title;
  @SerializedName("nick_name")
  @Expose
  String nickName;
  @SerializedName("customer_id")
  @Expose
  String customerId;
  @SerializedName("image")
  @Expose
  List<String> image;
  @SerializedName("create_at")
  @Expose
  String createAt;
  @SerializedName("status")
  @Expose
  String status;
  @SerializedName("url_image")
  @Expose
  String urlImage;


  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getDetailId() {
    return detailId;
  }

  public void setDetailId(String detailId) {
    this.detailId = detailId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public List<String> getImage() {
    return image;
  }

  public void setImage(List<String> image) {
    this.image = image;
  }

  public String getCreateAt() {
    return createAt;
  }

  public void setCreateAt(String createAt) {
    this.createAt = createAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUrlImage() {
    return urlImage;
  }

  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }
}
