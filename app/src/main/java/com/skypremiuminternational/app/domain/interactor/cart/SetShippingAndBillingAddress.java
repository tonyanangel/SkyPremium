package com.skypremiuminternational.app.domain.interactor.cart;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;
import com.skypremiuminternational.app.domain.models.cart.SetShippingAndBillingResponse;
import com.skypremiuminternational.app.domain.models.user.Address;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by codigo on 7/3/18.
 */

public class SetShippingAndBillingAddress
    extends UseCase<SetShippingAndBillingResponse, SetShippingAndBillingAddress.Params> {

  @Inject
  protected SetShippingAndBillingAddress(DataManager dataManager, ThreadExecutor subscriberThread,
                                         PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<SetShippingAndBillingResponse> provideObservable(Params params) {
    return getDataManager().setShippingAndBillingAddress(params);
  }

  public static class Params {
    public final BillingAddress defaultBillingAddress;
    public final Address defaultDeliveryAddress;
    public final String shippingCarrierCode;
    public final String shippingMethodCode;
    public final String email;

    public Params(
        BillingAddress defaultBillingAddress,
        Address defaultDeliveryAddress, String shippingCarrierCode,
        String shippingMethodCode, String email) {
      this.defaultBillingAddress = defaultBillingAddress;
      this.email = email;
      this.defaultDeliveryAddress = defaultDeliveryAddress;
      this.shippingCarrierCode = shippingCarrierCode;
      this.shippingMethodCode = shippingMethodCode;
    }
  }
}
