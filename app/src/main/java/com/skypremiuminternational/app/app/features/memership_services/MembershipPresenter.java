package com.skypremiuminternational.app.app.features.memership_services;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class MembershipPresenter extends BasePresenter<MembershipView> {

  private final GetUserDetail getUserDetail;
  private final GetCartCount getCartCount;

  private MembershipViewState viewState;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public MembershipPresenter(GetAppVersion getAppVersion, GetCartCount getCartCount,
                             InternalStorageManager internalStorageManager, GetUserDetail getUserDetail) {
    super(getAppVersion, internalStorageManager);
    this.getUserDetail = getUserDetail;
    this.getCartCount = getCartCount;
    attachLoading(getUserDetail);
  }

  public void getUserDetail() {

    viewState = MembershipViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .build();
    getView().render(viewState);

    Subscription subscription = getUserDetail.execute(new BaseSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse response) {
        viewState = MembershipViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(response)
            .build();

        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = MembershipViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(s -> getView().render(s), Timber::e));
  }
}
