package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.AddCartItemCountRequest;
import com.skypremiuminternational.app.domain.models.cart.CheckLimitResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;


public class AddToBuyNow extends UseCase<AddCartItemCountRequest.Cart, AddToBuyNow.Params> {

  @Inject
  protected AddToBuyNow(DataManager dataManager, ThreadExecutor subscriberThread,
                      PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<AddCartItemCountRequest.Cart> provideObservable(final Params params) {
    return getDataManager().createCart()
        .flatMap(cartId -> getDataManager().checkIfCartHasReachedLimit()
            .onErrorReturn(error -> { // add to cart directly when error occurred
              return new CheckLimitResponse(false, "No error", new ArrayList<>());
            })
            .flatMap(checkLimitResponse -> {
              if (checkLimitResponse.status_limit) {
                if (checkLimitResponse.limit_errors != null && checkLimitResponse.limit_errors.size() > 0) {
                  return Observable.error(new CartLimitException(checkLimitResponse.limit_errors));
                }
              }
              return addToBuyNow(cartId, params);
            })
        );
  }

  private Observable<AddCartItemCountRequest.Cart> addToBuyNow(String cartId, final Params params) {
    return getDataManager().addToBuyNow(createRequest(cartId, params));
  }

  private AddCartItemCountRequest createRequest(String cartId, Params params) {
    AddCartItemCountRequest.Cart cart =
        new AddCartItemCountRequest.Cart(null, params.sku, params.qty, params.name, params.product_type,
            cartId);
    return new AddCartItemCountRequest(cart);
  }

  public static class Params {
    public final String sku;
    public final int qty;
    public final String name;
    public final String product_type;

    public Params(String sku, int qty, String name, String product_type) {
      this.sku = sku;
      this.qty = qty;
      this.name = name;
      this.product_type = product_type;
    }
  }
}
