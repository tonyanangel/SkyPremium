package com.skypremiuminternational.app.domain.interactor.user.billingaddress;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class EditBillingAddress extends UseCase<List<BillingAddress>, EditBillingAddress.Params> {

  private final GetBillingAddresses getBillingAddresses;

  @Inject
  protected EditBillingAddress(DataManager dataManager,
                               ThreadExecutor subscriberThread,
                               GetBillingAddresses getBillingAddresses,
                               PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
    this.getBillingAddresses = getBillingAddresses;
  }

  @Override
  public Observable<List<BillingAddress>> provideObservable(Params params) {
    return getDataManager().editBillingAddress(params)
        .flatMap(ignored -> getBillingAddresses.asObservable());
  }

  public static final class Params {
    public final BillingAddress billingAddress;

    public Params(
        BillingAddress billingAddress) {
      this.billingAddress = billingAddress;
    }
  }
}
