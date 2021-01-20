package com.skypremiuminternational.app.domain.interactor.rating_comment;

import com.skypremiuminternational.app.data.network.request.AddCommentRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingResult;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class DeleteReview extends UseCase<RatingResult, Map<String,String> > {

  @Inject
  protected DeleteReview(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<RatingResult> provideObservable(Map<String,String> request) {
    return getDataManager().deleteReview(request).doOnNext(responseBody -> {

    });
  }
}
