package com.skypremiuminternational.app.domain.interactor.rating_comment;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

public class GetRatingSummary  extends UseCase<ResponseBody,Map<String,String>> {


  @Inject
  protected GetRatingSummary(DataManager dataManager, ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }


  @Override
  public Observable<ResponseBody> provideObservable(Map<String, String> request) {
    return getDataManager().getRatingSummaryByProduct(request);
  }
}
