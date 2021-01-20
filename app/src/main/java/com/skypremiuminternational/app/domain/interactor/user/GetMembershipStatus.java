package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 1/30/18.
 */

public class GetMembershipStatus extends UseCase<List<CustomAttribute>, Void> {
  @Inject
  protected GetMembershipStatus(DataManager dataManager,
                                ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<CustomAttribute>> provideObservable(Void aVoid) {
    return Observable.just(getDataManager().getUserDetail().getCustomAttributes());
  }
}
