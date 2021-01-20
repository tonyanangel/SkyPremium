package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.TotalSegment;

import javax.inject.Inject;

import rx.Observable;

public class GetCostBuyNow extends UseCase<CartAllInformationResponse, Void> {

  @Inject
  protected GetCostBuyNow(DataManager dataManager, ThreadExecutor subscriberThread,
                          PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CartAllInformationResponse> provideObservable(Void mVoid) {
    return getDataManager().getAllCartInformationBuyNow().map(cartAllInformationResponse -> {
      flatInfo(cartAllInformationResponse);
      return cartAllInformationResponse;
    });
  }

  private void flatInfo(CartAllInformationResponse all) {
    for (TotalSegment total : all.getTotalSegments()) {
      if (total.getCode().equalsIgnoreCase("subtotal")) {
        all.setSubTotal(total.getValue());
      } else if (total.getCode().equalsIgnoreCase("grand_total")) {
        all.setGrandTotal(total.getValue());
      } else if (total.getCode().equalsIgnoreCase("shipping")) {
        all.setShippingFee(total.getValue());
      } else if (total.getCode().equalsIgnoreCase("total_delivery_fee")) {
        all.setDeliveryFee(total.getValue());
      } else if (total.getCode().equalsIgnoreCase("total_loyalty_value_redeemed")) {
        all.setRedeemedSkyDollars(total.getValue());
      } else if (total.getCode().equalsIgnoreCase("discount")) {
        all.setDiscountByCoupon(total.getValue());
      } else if (total.getCode().equalsIgnoreCase("tax")) {
        all.setTax(total.getValue());
      }
    }
  }
}