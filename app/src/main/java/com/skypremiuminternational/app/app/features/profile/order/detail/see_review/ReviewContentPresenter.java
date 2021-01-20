package com.skypremiuminternational.app.app.features.profile.order.detail.see_review;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.rating_comment.AddRatingComment;
import com.skypremiuminternational.app.domain.interactor.rating_comment.DeleteReview;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetReviewDetail;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ReviewContentPresenter extends BaseFragmentPresenter<ReviewView> {

  private final CompositeSubscription compositeSubscription;
  private final GetReviewDetail getReviewDetail;
  private final DataManager dataManager;
  private final DeleteReview deleteReview;

  @Inject
  public ReviewContentPresenter( AddRatingComment addRatingComment, GetUserDetail getUserDetail
      , DataManager dataManager,GetReviewDetail getReviewDetail, DeleteReview deleteReview) {
    compositeSubscription = new CompositeSubscription();
    this.dataManager = dataManager;
    this.getReviewDetail = getReviewDetail;
    this.deleteReview = deleteReview;
  }


  public void getReviewDetail(ReviewDetailRequest request){
    request.setCustomerId(dataManager.getUserDetail().getId().toString());
    getView().showLoading();
    add(getReviewDetail.execute(new BaseSubscriber<ReviewDetailResponse>() {
      @Override
      public void onSuccess(ReviewDetailResponse value) {

      getView().hideLoading();
      getView().renderReviewDetail(value);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        getView().hideLoading();
      }
    },request));
  }

  public void deleteReview(String itemId){
    Map<String, String> mapRequest = new HashMap<>();
    mapRequest.put("item_id",itemId);

    deleteReview.execute(new BaseSubscriber<RatingResult>() {
      @Override
      public void onSuccess(RatingResult ratingResult) {
        getView().renderDelete(ratingResult);
        getView().hideLoading();
      }
      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    },mapRequest);
  }


  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

}

