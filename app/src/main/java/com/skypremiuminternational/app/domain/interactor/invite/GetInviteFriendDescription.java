package com.skypremiuminternational.app.domain.interactor.invite;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.InviteFriendDescription;

import javax.inject.Inject;

import rx.Observable;

public class GetInviteFriendDescription extends UseCase<InviteFriendDescription, Void> {
  @Inject
  protected GetInviteFriendDescription(DataManager dataManager,
                                       ThreadExecutor subscriberThread,
                                       PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<InviteFriendDescription> provideObservable(Void aVoid) {
    return getDataManager().getInviteFriendDescription();
  }
}
