package com.skypremiuminternational.app.domain.interactor.search;

import com.skypremiuminternational.app.data.network.request.SearchProductRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.search.SearchProductResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetSearchAll extends UseCase<SearchProductResponse, SearchProductRequest> {

  @Inject
  protected GetSearchAll(DataManager dataManager, ThreadExecutor subscriberThread,
                         PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<SearchProductResponse> provideObservable(SearchProductRequest params) {
    return getDataManager().searchAll(params).doOnNext(new Action1<SearchProductResponse>() {
      @Override
      public void call(SearchProductResponse responseBody) {
        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
      }
    });
    /*return Single.create(new Single.OnSubscribe<ResponseBody>() {
      @Override public void call(SingleSubscriber<? super ResponseBody> singleSubscriber) {
        //getDataManager().saveToken();
        //singleSubscriber.onSuccess(Boolean.TRUE);
      }
    }).toObservable();*/
  }
}
