package com.skypremiuminternational.app.domain.models.comment_rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatingOption {

  @SerializedName("items")
  @Expose
  List<RatingItem> items;

  public List<RatingItem> getItems() {
    return items;
  }

  public void setItems(List<RatingItem> items) {
    this.items = items;
  }

  public class RatingItem{
    @SerializedName("rating_id")
    @Expose
    String ratingId;
    @SerializedName("rating_code")
    @Expose
    String ratingCode;
    @SerializedName("is_active")
    @Expose
    String isActive;
    @SerializedName("value")
    @Expose
    String value;
    @SerializedName("option_id")
    @Expose
    String optionId;

    public String getOptionId() {
      return optionId;
    }

    public void setOptionId(String optionId) {
      this.optionId = optionId;
    }

    public String getRatingId() {
      return ratingId;
    }

    public void setRatingId(String ratingId) {
      this.ratingId = ratingId;
    }

    public String getRatingCode() {
      return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
      this.ratingCode = ratingCode;
    }

    public String getIsActive() {
      return isActive;
    }

    public void setIsActive(String isActive) {
      this.isActive = isActive;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}
