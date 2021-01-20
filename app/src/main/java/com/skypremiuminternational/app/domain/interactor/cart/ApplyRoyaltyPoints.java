package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.RoyaltyResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 24/2/18.
 */

public class ApplyRoyaltyPoints extends UseCase<RoyaltyResponse, ApplyRoyaltyPoints.Params> {

  @Inject
  protected ApplyRoyaltyPoints(DataManager dataManager, ThreadExecutor subscriberThread,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<RoyaltyResponse> provideObservable(final Params params) {
    return getDataManager().applyRoyaltyPts(params);
  }

  public static class Params {
    public final String points;

    public Params(String points) {
      this.points = points;
    }
  }
}
