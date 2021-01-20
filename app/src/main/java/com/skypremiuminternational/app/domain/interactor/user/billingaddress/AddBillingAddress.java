package com.skypremiuminternational.app.domain.interactor.user.billingaddress;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class AddBillingAddress extends UseCase<List<BillingAddress>, AddBillingAddress.Params> {

  private final GetBillingAddresses getBillingAddresses;

  @Inject
  protected AddBillingAddress(DataManager dataManager,
                              ThreadExecutor subscriberThread,
                              GetBillingAddresses getBillingAddresses,
                              PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
    this.getBillingAddresses = getBillingAddresses;
  }

  @Override
  public Observable<List<BillingAddress>> provideObservable(Params params) {
    return getDataManager().addBillingAddress(params)
        .flatMap(ignored -> getBillingAddresses.asObservable());
  }

  public static class Params {
    public final BillingAddress address;

    public Params(BillingAddress address) {
      this.address = address;
    }
  }
}
