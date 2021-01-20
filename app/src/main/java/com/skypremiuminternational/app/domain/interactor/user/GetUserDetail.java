package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.UserUtil;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetUserDetail extends UseCase<UserDetailResponse, Void> {

  private final UserUtil userUtil;

  @Inject
  protected GetUserDetail(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread, UserUtil userUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.userUtil = userUtil;
  }

  @Override
  public Observable<UserDetailResponse> provideObservable(Void avoid) {
    return Observable.just(getDataManager().getUserDetail()).map(userUtil::flatInfo);
  }
}
