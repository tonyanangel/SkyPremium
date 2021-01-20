package com.skypremiuminternational.app.domain.interactor.auth;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import javax.inject.Inject;

import rx.Observable;

public class CreateCrmToken extends UseCase<CrmTokenResponse, Void> {
  @Inject
  protected CreateCrmToken(DataManager dataManager, ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CrmTokenResponse> provideObservable(Void v) {
    return getDataManager().createCrmToken();
  }
}
