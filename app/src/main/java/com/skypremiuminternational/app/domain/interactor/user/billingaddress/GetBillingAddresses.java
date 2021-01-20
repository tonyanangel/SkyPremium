package com.skypremiuminternational.app.domain.interactor.user.billingaddress;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetBillingAddresses extends UseCase<List<BillingAddress>, Void> {

  @Inject
  protected GetBillingAddresses(DataManager dataManager,
                                ThreadExecutor subscriberThread,
                                PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
  }

  @Override
  public Observable<List<BillingAddress>> provideObservable(Void aVoid) {
    return getDataManager().getBillingAddresses().flatMapIterable(addresses -> addresses)
        .filter(address -> address.getCrmAddressOrder() == null || !address.getCrmAddressOrder()
            .equalsIgnoreCase("postal_address"))
        .toList();
  }
}
