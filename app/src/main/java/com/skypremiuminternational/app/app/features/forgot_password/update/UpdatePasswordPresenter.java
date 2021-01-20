package com.skypremiuminternational.app.app.features.forgot_password.update;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.PasswordHashRequest;
import com.skypremiuminternational.app.data.network.request.UpdatePasswordRequest;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.auth.GetPasswordHash;
import com.skypremiuminternational.app.domain.interactor.auth.UpdatePassword;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class UpdatePasswordPresenter extends BasePresenter<UpdatePasswordView> {

  private GetUserDetail getUserDetail;

  private UpdatePassword updatePassword;

  private GetPasswordHash getPasswordHash;

  UpdatePasswordViewState viewState;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public UpdatePasswordPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                                 GetUserDetail getUserDetail, UpdatePassword updatePassword,
                                 GetPasswordHash getPasswordHash) {
    super(getAppVersion, internalStorageManager);
    viewState = UpdatePasswordViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .userDetailResponse(null)
        .build();
    this.getUserDetail = getUserDetail;
    this.updatePassword = updatePassword;
    this.getPasswordHash = getPasswordHash;
    attachLoading(updatePassword);
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

  public void getUserDetail() {

    Subscription subscription = getUserDetail.execute(new BaseSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse response) {
        viewState = UpdatePasswordViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetailResponse(response)
            .build();

        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = UpdatePasswordViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetailResponse(null)
            .build();

        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getPasswordHash(final String new_password,
                              final UserDetailResponse userDetailResponse) {

    viewState = UpdatePasswordViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .userDetailResponse(viewState.userDetailResponse())
        .build();
    getView().render(viewState);

    PasswordHashRequest passwordHashRequest = new PasswordHashRequest(new_password);

    Subscription subscription = getPasswordHash.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {
        try {
          String passwordHash = response.string();
          String separated = passwordHash.substring(1, passwordHash.length() - 1);
          onSubmit(userDetailResponse, separated);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = UpdatePasswordViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .userDetailResponse(viewState.userDetailResponse())
            .build();
        getView().render(viewState);
      }
    }, passwordHashRequest);
    compositeSubscription.add(subscription);
  }

  public void onSubmit(UserDetailResponse userDetailResponse, String passwordHash) {

    String member_number = "";
    viewState = UpdatePasswordViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .userDetailResponse(viewState.userDetailResponse())
        .build();
    getView().render(viewState);

    List<UpdatePasswordRequest.CustomAttribute> customAttributeList = new ArrayList<>();

    for (com.skypremiuminternational.app.domain.models.user.CustomAttribute customAttribute : userDetailResponse
        .getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equals("member_number")) {
        member_number = customAttribute.getValue();
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("nationality")) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("contact_country_code")) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("address_country")) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("language")) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("salutation")) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("currency")) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      }

      /*if (customAttribute.getValue() != null) {
        customAttributeList.add(
            new UpdatePasswordRequest.CustomAttribute(customAttribute.getAttributeCode(),
                customAttribute.getValue()));
      }*/
    }

    UpdatePasswordRequest.Customer customer =
        new UpdatePasswordRequest.Customer(userDetailResponse.getWebsiteId(),
            userDetailResponse.getFirstname(), userDetailResponse.getLastname(),
            userDetailResponse.getEmail(), customAttributeList);

    UpdatePasswordRequest updatePasswordRequest =
        new UpdatePasswordRequest(customer, passwordHash, member_number);


      String finalMember_number = member_number;

    Subscription subscription = updatePassword.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {
        viewState = UpdatePasswordViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(null)
            .userDetailResponse(viewState.userDetailResponse())
            .build();
        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = UpdatePasswordViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(finalMember_number)
            .userDetailResponse(viewState.userDetailResponse())
            .build();
        getView().render(viewState);
      }
    }, updatePasswordRequest);
    compositeSubscription.add(subscription);
  }
}
