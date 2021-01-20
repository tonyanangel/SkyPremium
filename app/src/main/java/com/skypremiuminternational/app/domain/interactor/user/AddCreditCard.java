package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.data.network.request.AddCreditCardRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 2/28/18.
 */

public class AddCreditCard extends UseCase<UpdateUserResponse, AddCreditCardRequest> {
  @Inject
  protected AddCreditCard(DataManager dataManager,
                          ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UpdateUserResponse> provideObservable(AddCreditCardRequest addCreditCardRequest) {
    return getDataManager().addCreditCards(addCreditCardRequest);
  }
}
