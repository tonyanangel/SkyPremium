package com.skypremiuminternational.app.domain.interactor.user;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.user.UpdateUserResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 3/5/18.
 */

public class SetDefaultCreditCard extends UseCase<UpdateUserResponse, SetDefaultCreditCard.Params> {
  @Inject
  protected SetDefaultCreditCard(DataManager dataManager,
                                 ThreadExecutor subscriberThread,
                                 PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<UpdateUserResponse> provideObservable(Params params) {
    return getDataManager().setDefaultCreditCard(params.cardId);
  }

  public static class Params {
    public final String cardId;

    public Params(String cardId) {
      this.cardId = cardId;
    }
  }
}
