package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;

import javax.inject.Inject;

import rx.Observable;

public class UpdateCreditCardV2 extends UseCase<UpdateUserResponse, UpdateCreditCardV2.Params> {
  @Inject
  protected UpdateCreditCardV2(DataManager dataManager,
                             ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UpdateUserResponse> provideObservable(Params params) {
    if (params.setAsDefault) {
      return getDataManager().updateCreditCardV2(params.request)
          .flatMap(ignored -> getDataManager().setDefaultCreditCardV2(
              params.request.getCreditCardItemToken().getCardId()));
    }
    return getDataManager().updateCreditCardV2(params.request);
  }

  public static class Params {
    public final UpdateCreditCardRequest request;
    public final boolean setAsDefault;

    public Params(UpdateCreditCardRequest request, boolean setAsDefault) {
      this.request = request;
      this.setAsDefault = setAsDefault;
    }
  }
}
