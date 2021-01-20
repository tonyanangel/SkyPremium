package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.CartAllInformationResponse;
import com.skypremiuminternational.app.domain.models.cart.OrderedItem;
import com.skypremiuminternational.app.domain.models.cart.TotalSegment;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 24/2/18.
 */

public class GetAllCost extends UseCase<CartAllInformationResponse, Void> {

  @Inject
  protected GetAllCost(DataManager dataManager, ThreadExecutor subscriberThread,
                       PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<CartAllInformationResponse> provideObservable(Void mVoid) {
    return getDataManager().getAllCartInformation().map(cartAllInformationResponse -> {
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
