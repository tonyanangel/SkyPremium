package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.domain.util.UserUtil;

import java.util.StringTokenizer;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by johnsonmaung on 10/22/17.
 */

public class GetUserDetailFromApi extends UseCase<UserDetailResponse, Void> {

  private final UserUtil userUtil;

  @Inject
  protected GetUserDetailFromApi(DataManager dataManager,
                                 ThreadExecutor subscriberThread,
                                 PostExecutionThread observerThread,
                                 UserUtil userUtil) {
    super(dataManager, subscriberThread, observerThread);
    this.userUtil = userUtil;
  }

  @Override
  public Observable<UserDetailResponse> provideObservable(Void avoid) {
    return getDataManager().getUserDetailFromAPI()
        .map(userUtil::flatInfo)
        .doOnNext(responseBody -> getDataManager().saveUserDetail(responseBody));
  }
}
