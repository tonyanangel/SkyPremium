package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 2/22/18.
 */

public class DeleteCartItem
    extends UseCase<Boolean, DeleteCartItem.Params> {
  @Inject
  protected DeleteCartItem(DataManager dataManager,
                           ThreadExecutor subscriberThread,
                           PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Boolean> provideObservable(
      Params params) {
    return getDataManager().removeShoppingCart(params.cartItemId);
  }

  public static class Params {
    public final String cartItemId;

    public Params(String cartItemId) {
      this.cartItemId = cartItemId;
    }
  }
}
