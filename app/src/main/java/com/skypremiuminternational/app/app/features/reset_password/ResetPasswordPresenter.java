package com.skypremiuminternational.app.app.features.reset_password;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.signin.SignIn;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class ResetPasswordPresenter extends BasePresenter<ResetPasswordView> {

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public ResetPasswordPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager, SignIn signIn) {
    super(getAppVersion, internalStorageManager);
    SignIn signIn1 = signIn;
    attachLoading();
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

  public void onLogin(final String username, final String password) {

    /*SignInViewState viewState = SignInViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .build();
    getView().render(viewState);

    Subscription subscription = login.execute(new BaseSubscriber<LoginResponse>() {
      @Override public void onSuccess(LoginResponse response) {
        SignInViewState viewState = SignInViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(null)
            .build();
        getView().render(viewState);
      }

      @Override public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        SignInViewState viewState = SignInViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .build();
        getView().render(viewState);
      }
    }, loginRequest);
    compositeSubscription.add(subscription);*/
  }
}
