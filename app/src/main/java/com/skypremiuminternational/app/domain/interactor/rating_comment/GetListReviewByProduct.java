package com.skypremiuminternational.app.domain.interactor.rating_comment;
import com.skypremiuminternational.app.data.network.request.ProductReviewRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.comment_rating.ProductReviewResponse;

import javax.inject.Inject;

import rx.Observable;

public class GetListReviewByProduct extends UseCase<ProductReviewResponse, ProductReviewRequest> {



  @Inject
  protected GetListReviewByProduct(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ProductReviewResponse> provideObservable(ProductReviewRequest request) {
    return getDataManager().getListReviewByProduct(request).doOnNext(responseBody -> {

    });
  }
}
