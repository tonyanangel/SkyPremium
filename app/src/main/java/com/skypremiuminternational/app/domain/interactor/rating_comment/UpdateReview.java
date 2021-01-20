package com.skypremiuminternational.app.domain.interactor.rating_comment;

import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.data.network.request.UpdateCommentRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;

import javax.inject.Inject;

import rx.Observable;

public class UpdateReview extends UseCase<RatingResult, UpdateCommentRequest> {



  @Inject
  protected UpdateReview(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<RatingResult> provideObservable(UpdateCommentRequest request) {
    return getDataManager().updateReview(request).doOnNext(responseBody -> {

    });
  }
}
