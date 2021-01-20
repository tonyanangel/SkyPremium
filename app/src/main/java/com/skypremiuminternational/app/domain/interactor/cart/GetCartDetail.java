package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;
import com.skypremiuminternational.app.domain.models.cart.CartDetailResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by aeindraaung on 2/14/18.
 */

public class GetCartDetail extends UseCase<CartDetailResponse, Void> {

  @Inject
  protected GetCartDetail(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CartDetailResponse> provideObservable(Void aVoid) {

    return getDataManager().createCart()
        .flatMap(ignored -> getDataManager().getCartDetail()
            .map(this::flatInfo)
            .flatMap(cartDetail -> Observable.from(cartDetail.getItems())
                .toList()
                .map(cartDetailItems -> {
                  cartDetail.setItems(cartDetailItems);
                  return cartDetail;
                })
                .onErrorReturn(error -> cartDetail)));
  }

  private CartDetailResponse flatInfo(CartDetailResponse cartDetailResponse) {
    cartDetailResponse.setContainVirtualProduct(check(cartDetailResponse.getItems()));
    return cartDetailResponse;
  }

  private boolean check(List<CartDetailItem> cartDetailItems) {
    return cartDetailItems != null && cartDetailItems.size() == 1 && cartDetailItems.get(0)
        .getSku()
        .equalsIgnoreCase(Constants.SKU_MEMBERSHIP_RENEWAL);
  }
}
