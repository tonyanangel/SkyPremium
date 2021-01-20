package com.skypremiuminternational.app.domain.interactor.category;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetCategoryFromApi extends UseCase<CategoryResponse, Void> {

  @Inject
  protected GetCategoryFromApi(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CategoryResponse> provideObservable(Void avoid) {
    return getDataManager().getCategoriesFromAPI().doOnNext(new Action1<CategoryResponse>() {
      @Override
      public void call(CategoryResponse responseBody) {

        getDataManager().saveCategories(responseBody);
        /*try {
          Timber.e(responseBody.string());
        } catch (IOException e) {
          e.printStackTrace();
        }*/
      }
    });
  }
}
