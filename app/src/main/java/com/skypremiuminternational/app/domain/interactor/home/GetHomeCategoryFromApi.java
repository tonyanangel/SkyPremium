package com.skypremiuminternational.app.domain.interactor.home;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetHomeCategoryFromApi extends UseCase<HomeCategoryResponse, Void> {

  @Inject
  protected GetHomeCategoryFromApi(DataManager dataManager, ThreadExecutor subscriberThread,
                                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<HomeCategoryResponse> provideObservable(Void avoid) {
    return getDataManager().getHomeCategoriesFromAPI()
        .doOnNext(new Action1<HomeCategoryResponse>() {
          @Override
          public void call(HomeCategoryResponse responseBody) {

        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
          }
        });
  }
}
