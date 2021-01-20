package com.skypremiuminternational.app.domain.interactor.category;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetCategory extends UseCase<CategoryResponse, Void> {

  @Inject
  protected GetCategory(DataManager dataManager, ThreadExecutor subscriberThread,
                        PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CategoryResponse> provideObservable(Void avoid) {
    return Observable.just(getDataManager().getCategories());
  }
}
