package com.skypremiuminternational.app.domain.models.comment_rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewDetailResponse {

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
  @SerializedName("product_id")
  @Expose
  String productId;
  @SerializedName("status")
  @Expose
  String status;
  @SerializedName("url_image")
  @Expose
  String urlImage;
  @SerializedName("detail")
  @Expose
  String detail;
  @SerializedName("rating_id")
  @Expose
  String ratingId;
  @SerializedName("rating_value")
  @Expose
  String ratingValue;


  public String getRatingId() {
    return ratingId;
  }

  public void setRatingId(String ratingId) {
    this.ratingId = ratingId;
  }

  public String getRatingValue() {
    return ratingValue;
  }

  public void setRatingValue(String ratingValue) {
    this.ratingValue = ratingValue;
  }

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

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
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

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public List<String> getFullLinkImage(){
    List<String> urlList  = new ArrayList<>();
    for (String name :  getImage()){
      urlList.add(getUrlImage()+name);
    }
    return urlList;
  }
}

