package com.skypremiuminternational.app.app.features.forgot_password;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.ForgotPasswordRequest;
import com.skypremiuminternational.app.domain.interactor.auth.ForgotPassword;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {

  private final ForgotPassword forgotPassword;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public ForgotPasswordPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                                 ForgotPassword forgotPassword) {
    super(getAppVersion, internalStorageManager);
    this.forgotPassword = forgotPassword;
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

  public void onSubmit(final String email) {

    ForgotPasswordViewState viewState = ForgotPasswordViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .build();
    getView().render(viewState);

    ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email);

    Subscription subscription = forgotPassword.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {
        ForgotPasswordViewState viewState = ForgotPasswordViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(null)
            .build();
        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        ForgotPasswordViewState viewState = ForgotPasswordViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .build();
        getView().render(viewState);
      }
    }, forgotPasswordRequest);
    compositeSubscription.add(subscription);
  }
}
