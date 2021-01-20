package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 1/31/18.
 */

public class GetUserDatailForExpiredDateFromApi extends UseCase<UserDetailResponse, Void> {
  @Inject
  protected GetUserDatailForExpiredDateFromApi(DataManager dataManager,
                                               ThreadExecutor subscriberThread,
                                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UserDetailResponse> provideObservable(Void aVoid) {
    return getDataManager().getUserDetailFromAPI();
  }
}
