package com.skypremiuminternational.app.domain.interactor.rating_comment;

import com.skypremiuminternational.app.data.network.request.ReviewDetailRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;
import com.skypremiuminternational.app.domain.models.comment_rating.ReviewDetailResponse;

import javax.inject.Inject;

import rx.Observable;

public class GetRatingOption extends UseCase<RatingOption,Void> {



  @Inject
  protected GetRatingOption(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<RatingOption> provideObservable(Void aVoid) {
    return getDataManager().getRatingOptionFormAPI().doOnNext(ratingOption -> {

    });
  }

}
