package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by wmw on 2/15/2018.
 */

public class GetCartCount extends UseCase<String, GetCartCount.Params> {

  @Inject
  public GetCartCount(DataManager dataManager, ThreadExecutor subscribeThread,
                      PostExecutionThread observerThread) {
    super(dataManager, subscribeThread, observerThread);
  }

  @Override
  public Observable<String> provideObservable(Params params) {

    return getDataManager().getCartItems().map(cartDetailItems -> {
      int countInt= 0;
      String count = "";
      if(cartDetailItems!=null&&cartDetailItems.size()>0){
        for(CartDetailItem item : cartDetailItems){
          countInt += item.getQty();
        }
      }
      count = ""+countInt;

      getDataManager().saveCartCount(count);
      return getDataManager().getCartCount();
    }).onErrorReturn(error -> "0").doOnNext(getDataManager()::saveCartCount)
        .startWith(Observable.just(getDataManager().getCartCount()));
  }

  public static class Params {
    public final Boolean createCartId;

    public Params(Boolean createCartId) {
      this.createCartId = createCartId;
    }
  }
}
