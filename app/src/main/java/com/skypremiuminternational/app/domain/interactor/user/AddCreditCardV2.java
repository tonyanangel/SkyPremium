
package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;

import javax.inject.Inject;

import rx.Observable;

public class AddCreditCardV2 extends UseCase<UpdateUserResponse, AddCreditCardRequest> {
  @Inject
  protected AddCreditCardV2(DataManager dataManager,
                            ThreadExecutor subscriberThread,
                            PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UpdateUserResponse> provideObservable(AddCreditCardRequest addCreditCardRequest) {
    return getDataManager().addCreditCardsV2(addCreditCardRequest);
  }
}
