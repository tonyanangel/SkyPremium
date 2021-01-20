package com.skypremiuminternational.app.domain.interactor.user.billingaddress;

import com.skypremiuminternational.app.data.model.billingaddress.BillingAddress;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.interactor.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class DeleteBillingAddress
    extends UseCase<List<BillingAddress>, DeleteBillingAddress.Params> {

  private final GetBillingAddresses getBillingAddresses;

  @Inject
  protected DeleteBillingAddress(DataManager dataManager,
                                 ThreadExecutor subscriberThread,
                                 GetBillingAddresses getBillingAddresses,
                                 PostExecutionThread observerThread) {
    super(dataManager, subscriberThread, observerThread);
    this.getBillingAddresses = getBillingAddresses;
  }

  @Override
  public Observable<List<BillingAddress>> provideObservable(Params params) {
    return getDataManager().deleteBillingAddress(params).flatMap(succeeded -> {
      if (succeeded) {
        return getBillingAddresses.asObservable();
      } else {
        return Observable.error(new Exception("Failed to delete billing address"));
      }
    });
  }

  public static final class Params {
    public final String addressId;

    public Params(String addressId) {
      this.addressId = addressId;
    }
  }
}
