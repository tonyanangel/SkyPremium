package com.skypremiuminternational.app.domain.interactor.cart;

import android.text.TextUtils;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 9/3/18.
 */

public class AddRenewalToCart extends UseCase<Boolean, AddRenewalToCart.Params> {

  @Inject
  protected AddRenewalToCart(DataManager dataManager, ThreadExecutor subscriberThread,
                             PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<Boolean> provideObservable(Params params) {
    return getDataManager().createCart()
        .flatMap(cartId -> getDataManager().getCartItems()
            .flatMap(items -> {
              if (items == null || items.size() == 0) {
                return addToCart(params.usePoints);
              } else {
                return Observable.from(items)
                    .filter(detailItem -> detailItem.getSku().equals(Constants.SKU_MEMBERSHIP_RENEWAL))
                    .map(detailItem -> String.valueOf(detailItem.getItemId()))
                    .defaultIfEmpty("")
                    .flatMap(virtualItemId -> {
                      if (TextUtils.isEmpty(virtualItemId)) {
                        return addToCart(params.usePoints);
                      } else {
                        return getDataManager().removeShoppingCart(virtualItemId)
                            .flatMap(ignored -> addToCart(params.usePoints));
                      }
                    });
              }
            }));
  }

  private Observable<Boolean> addToCart(boolean usePoints) {
    if (usePoints) {
      return getDataManager().addRenewalToCartWithPoints();
    } else {
      return getDataManager().addRenewalToCartWithCredit();
    }
  }

  public static class Params {
    public final boolean usePoints;

    public Params(boolean usePoints) {
      this.usePoints = usePoints;
    }
  }
}
