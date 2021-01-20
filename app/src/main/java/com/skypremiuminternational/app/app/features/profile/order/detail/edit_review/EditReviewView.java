package com.skypremiuminternational.app.app.features.profile.order.detail.edit_review;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

public interface EditReviewView <T extends Presentable> extends Viewable<T> {


  void renderReviewDetail(ReviewDetailResponse reviewDetailResponse);
  void renderGetUserDetailSuccess(UserDetailResponse userDetailResponse);
  void renderEditReviewSuccess(RatingResult result);
  void renderEditReviewFail(RatingResult result);
  void renderRatingStar(RatingOption ratingOption);
  void renderRatingStarChoose(RatingOption ratingOption);
  void renderDelete(RatingResult ratingResult);
  void renderReviewDetailAfterSubmit(ReviewDetailResponse reviewDetailResponse);
}

