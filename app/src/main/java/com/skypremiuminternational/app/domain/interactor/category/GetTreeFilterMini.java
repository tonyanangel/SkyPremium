package com.skypremiuminternational.app.domain.interactor.category;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Toan Tran on 02/25/2020.
 */


public class GetTreeFilterMini extends UseCase<ResponseBody, String> {

  @Inject
  protected GetTreeFilterMini(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<ResponseBody> provideObservable(String request) {
    return getDataManager().getTreeCategoryMini(request);
  }
}
