package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;
import com.skypremiuminternational.app.domain.models.cart.UpdateItemCountRequest;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class UpdateCartItemCount extends UseCase<CartDetailItem, UpdateCartItemCount.Params> {
  @Inject
  protected UpdateCartItemCount(DataManager dataManager, ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  boolean limit  = false;

  @Override
  public Observable<CartDetailItem> provideObservable(final Params params) {

    final int updatingQyt = params.request.cartItem.qty;

    if (updatingQyt == params.originItem.getQty()) { // item count the same
      return Observable.error(new Exception("Item count is the same"));
    } else if (updatingQyt > params.originItem.getQty()) { //increasing item count
      return getDataManager().checkIfCartHasReachedLimit()
          .flatMap(new Func1<CheckLimitResponse, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(CheckLimitResponse checkLimitResponse) {
//              limit = checkLimitResponse.status_limit;
//              if (checkLimitResponse.status_limit) {
//                return Observable.error(new CartLimitException(checkLimitResponse.limit_errors));
//              }
              return Observable.just(checkLimitResponse.status_limit);
            }
          })
          .flatMap(new Func1<Boolean, Observable<CartDetailItem>>() {
            @Override
            public Observable<CartDetailItem> call(Boolean ignored) {
//              if(limit){
                return getDataManager().addCartDetailItemCount(params.request,
                    String.valueOf(params.originItem.getItemId()));
//                }
//              params.request.cartItem.qty =  params.originItem.getQty();
//              return getDataManager().addCartDetailItemCount(params.request,
//                      String.valueOf(params.originItem.getItemId()));
            }
          });
    } else { //reducing item count
      return getDataManager().addCartDetailItemCount(params.request,
          String.valueOf(params.originItem.getItemId()));
    }
  }

  public static class Params {
    public final CartDetailItem originItem;
    public final UpdateItemCountRequest request;
    public Params(CartDetailItem originItem, UpdateItemCountRequest request) {
      this.originItem = originItem;
      this.request = request;
    }
  }
}
