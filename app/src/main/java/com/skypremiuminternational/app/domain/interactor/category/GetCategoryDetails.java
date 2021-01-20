package com.skypremiuminternational.app.domain.interactor.category;

import com.skypremiuminternational.app.data.network.request.CaregoryDetailsRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetCategoryDetails extends UseCase<CategoryDetailsResponse, CaregoryDetailsRequest> {

  @Inject
  protected GetCategoryDetails(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CategoryDetailsResponse> provideObservable(CaregoryDetailsRequest request) {
    return getDataManager().getCategoryDetails(request)
        .doOnNext(new Action1<CategoryDetailsResponse>() {
          @Override
          public void call(CategoryDetailsResponse responseBody) {

        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
          }
        });
  }
}
