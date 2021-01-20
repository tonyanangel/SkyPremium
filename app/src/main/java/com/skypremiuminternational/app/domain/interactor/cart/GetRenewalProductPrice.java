package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 4/4/18.
 */

public class GetRenewalProductPrice extends UseCase<Double, Void> {

  @Inject
  protected GetRenewalProductPrice(DataManager dataManager, ThreadExecutor subscriberThread,
                                   PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Double> provideObservable(Void aVoid) {
    return getDataManager().getRenewalProduct().map(this::parsePrice);
  }

  private Double parsePrice(ItemsItem item) {
    try {
      return Double.parseDouble(item.getPrice());
    } catch (Exception e) {
      return 0d;
    }
  }
}
