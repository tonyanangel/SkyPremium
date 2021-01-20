package com.skypremiuminternational.app.app.features.profile.order.detail.review;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.rating_comment.AddRatingComment;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetReviewDetail;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ReviewPresenter extends BaseFragmentPresenter<ReviewView> {

  private final CompositeSubscription compositeSubscription;
  private final AddRatingComment addRatingComment;
  private final GetUserDetail getUserDetail;
  private final GetReviewDetail getReviewDetail;
  private final DataManager dataManager;

  @Inject
  public ReviewPresenter( AddRatingComment addRatingComment, GetUserDetail getUserDetail
      , DataManager dataManager,GetReviewDetail getReviewDetail) {
    compositeSubscription = new CompositeSubscription();
    this.addRatingComment = addRatingComment;
    this.getUserDetail = getUserDetail;
    this.dataManager = dataManager;
    this.getReviewDetail = getReviewDetail;
  }

  public void submitComment(AddCommentRequest request){
    getView().showLoading();
    add(addRatingComment.execute(new BaseSubscriber<RatingResult>() {
      @Override
      public void onSuccess(RatingResult value) {
        getView().hideLoading();
        if(value.getStatus().equalsIgnoreCase("success"))
          getView().renderSubmitSuccess(value);
        else
          getView().renderSubmitFailed(value);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        getView().hideLoading();
      }
    },request));
  }


  public void getReviewDetail(ReviewDetailRequest request){
    request.setCustomerId(dataManager.getUserDetail().getId().toString());
    getView().showLoading();
    add(getReviewDetail.execute(new BaseSubscriber<ReviewDetailResponse>() {
      @Override
      public void onSuccess(ReviewDetailResponse value) {
        getView().renderReviewDetail(value);
        getView().hideLoading();
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        getView().hideLoading();
      }
    },request));
  }



  public void getUserDetail(){
    getView().showLoading();
    getUserDetail.execute(new BaseSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse response) {
        getView().renderGetUserDetailSuccess(response);
        getView().hideLoading();
      }
      @Override
      public void onError(Throwable error) {

      }
    } );
  }


  public void rating(){
    getView().renderRatingStar(dataManager.getRatingOption());
  }
  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

}

