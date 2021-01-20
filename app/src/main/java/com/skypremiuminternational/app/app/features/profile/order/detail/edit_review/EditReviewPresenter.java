package com.skypremiuminternational.app.app.features.profile.order.detail.edit_review;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCommentRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.rating_comment.DeleteReview;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetReviewDetail;
import com.skypremiuminternational.app.domain.interactor.rating_comment.UpdateReview;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class EditReviewPresenter extends BaseFragmentPresenter<EditReviewView> {

  private final  GetReviewDetail getReviewDetail;
  private final GetUserDetail getUserDetail;
  private final UpdateReview updateReview;
  private final DeleteReview deleteReview;

  private final CompositeSubscription compositeSubscription;
  private final DataManager dataManager;

  String nameCustomer;

  @Inject
  public EditReviewPresenter(GetReviewDetail getReviewDetail, DataManager dataManager
      ,UpdateReview updateReview,GetUserDetail getUserDetail,DeleteReview deleteReview) {
    this.getReviewDetail =getReviewDetail;
    compositeSubscription = new CompositeSubscription();
    this.dataManager = dataManager;
    this.updateReview = updateReview;
    this.getUserDetail = getUserDetail;
    this.deleteReview = deleteReview;
  }



  public void getReviewDetail(ReviewDetailRequest request){
    getView().showLoading();
    request.setCustomerId(dataManager.getUserDetail().getId().toString());

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


  public void getReviewDetailAfterSubmit(ReviewDetailRequest request){
    getView().showLoading();
    request.setCustomerId(dataManager.getUserDetail().getId().toString());

    add(getReviewDetail.execute(new BaseSubscriber<ReviewDetailResponse>() {
      @Override
      public void onSuccess(ReviewDetailResponse value) {
        getView().renderReviewDetailAfterSubmit(value);
        getView().hideLoading();
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);

        getView().hideLoading();
      }
    },request));
  }


  public void updateReview(UpdateCommentRequest request){

    getView().showLoading();
    add(updateReview.execute(new BaseSubscriber<RatingResult>() {
      @Override
      public void onSuccess(RatingResult value) {
        getView().hideLoading();
        if(value.getStatus().equalsIgnoreCase("success"))
          getView().renderEditReviewSuccess(value);
        else
          getView().renderEditReviewFail(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        super.onError(error);
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
        getView().hideLoading();
      }
    } );
  }

  public void deleteReview(String itemId){
    Map<String, String>  mapRequest = new HashMap<>();
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
  public void rating(){
    getView().renderRatingStarChoose(dataManager.getRatingOption());
  }

  private void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }





}

