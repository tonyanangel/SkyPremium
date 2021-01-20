package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.data.network.request.UpdateCreditCardRequest;
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

public class UpdateCreditCard extends UseCase<UpdateUserResponse, UpdateCreditCard.Params> {
  @Inject
  protected UpdateCreditCard(DataManager dataManager,
                             ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UpdateUserResponse> provideObservable(Params params) {
    if (params.setAsDefault) {
      return getDataManager().updateCreditCard(params.request)
          .flatMap(ignored -> getDataManager().setDefaultCreditCard(
              params.request.getCreditCardItemToken().getCardId()));
    }
    return getDataManager().updateCreditCard(params.request);
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
