package com.skypremiuminternational.app.app.features.checkout;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aeindraaung on 1/29/18.
 */

public class CheckoutPresenter extends BasePresenter<CheckoutView> {

  private final CompositeSubscription compositeSubscription;

  @Inject
  public CheckoutPresenter(GetAppVersion getAppVersion,
                           InternalStorageManager internalStorageManager) {
    super(getAppVersion, internalStorageManager);
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeSubscription.clear();
  }
}
