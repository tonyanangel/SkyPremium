package com.skypremiuminternational.app.app.features.travel.booking;

import com.skypremiuminternational.app.app.features.travel.ProductListView;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.domain.interactor.splash.CheckUserLoggedIn;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 10/6/17.
 */

public class HotelBookingPresenter extends BaseFragmentPresenter<ProductListView> {

  private final CheckUserLoggedIn checkUserLoggedIn;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public HotelBookingPresenter(CheckUserLoggedIn checkUserLoggedIn) {
    this.checkUserLoggedIn = checkUserLoggedIn;
    attachLoading(checkUserLoggedIn);
  }

  public void checkUserLoggedIn() {
    /*Subscription subscription = checkUserLoggedIn.execute(new BaseSubscriber<Boolean>() {
      @Override public void onSuccess(Boolean aBoolean) {
        SplashViewState viewState = SplashViewState.builder().setUserLoggedIn(aBoolean).build();
        getView().render(viewState);
      }

      @Override public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
      }
    });
    compositeSubscription.add(subscription);*/
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }
}
