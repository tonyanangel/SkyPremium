package com.skypremiuminternational.app.app.features.profile.order.detail.review;

import com.skypremiuminternational.app.app.internal.mvp.contract.Presentable;
import com.skypremiuminternational.app.app.internal.mvp.contract.Viewable;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

public interface ReviewView <T extends Presentable> extends Viewable<T> {

  void render();

  void renderSubmitSuccess(RatingResult addResult);
  void renderSubmitFailed(RatingResult addResult);
  void renderSubmitError();
  void renderGetUserDetailSuccess(UserDetailResponse response);
  void renderGetUserDetailFail();
  void renderRatingStar(RatingOption ratingOption);
  void renderReviewDetail(ReviewDetailResponse reviewDetailResponse);
}

