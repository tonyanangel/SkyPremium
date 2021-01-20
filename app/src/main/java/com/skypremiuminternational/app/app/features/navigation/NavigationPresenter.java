package com.skypremiuminternational.app.app.features.navigation;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.domain.interactor.auth.ClearUserData;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.interactor.user.UploadUserAvatar;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class NavigationPresenter extends BaseFragmentPresenter<NavigationView> {

  private GetUserDetailFromApi getUserDetailFromApi;

  private ClearUserData clearUserData;

  private NavigationViewState viewState;

  private UploadUserAvatar uploadUserAvatar;

  private InternalStorageManager internalStorageManager;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public NavigationPresenter(GetUserDetailFromApi getUserDetailFromApi,
                             ClearUserData clearUserData, UploadUserAvatar uploadUserAvatar,
                             InternalStorageManager internalStorageManager) {
    viewState = NavigationViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .build();
    this.getUserDetailFromApi = getUserDetailFromApi;
    this.clearUserData = clearUserData;
    this.uploadUserAvatar = uploadUserAvatar;
    this.internalStorageManager = internalStorageManager;
    attachLoading(getUserDetailFromApi);
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void getUserDetailFromApi() {

    viewState = NavigationViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .txtLoading(null)
        .build();
    getView().render(viewState);

    Subscription subscription =
        getUserDetailFromApi.execute(new BaseSubscriber<UserDetailResponse>() {
          @Override
          public void onSuccess(UserDetailResponse response) {
            String avatarLink = parseAvatarLink(response);
            String amtLoyalty = parseAmtLoyalty(response);

            viewState = NavigationViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message(response)
                .avatarLink(avatarLink)
                .amtLoyalty(amtLoyalty)
                .build();

            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            super.onError(error);
            error.printStackTrace();
            viewState = NavigationViewState.builder()
                .error(new Exception("Failed to load user detail"))
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .build();
            getView().render(viewState);
          }
        } );

    compositeSubscription.add(subscription);
  }

  private String parseAmtLoyalty(UserDetailResponse response) {
    String amtLoyalty = "";
    for (CustomAttribute customAttribute : response.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("member_loyalty_point")) {
        //amtLoyalty = String.format("%,d", Long.parseLong(customAttribute.getValue()));
        amtLoyalty = customAttribute.getValue();
        return amtLoyalty;
      }
    }
    return amtLoyalty;
  }

  private String parseAvatarLink(UserDetailResponse response) {
    String avatarLink = "";
    for (CustomAttribute customAttribute : response.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("avatar")) {
        return customAttribute.getValue();
      }
    }
    return avatarLink;
  }

  public void onLogout() {

    OneSignal.setSubscription(false);

    Subscription subscription = clearUserData.execute(new BaseSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {

      }
    } );
    compositeSubscription.add(subscription);
  }

  public void uploadImageToServer(String uploadImageUrl, String imageType, String imageName) {
    viewState = viewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .amtLoyalty(viewState.amtLoyalty())
        .avatarLink(viewState.avatarLink())
        .txtLoading("avatar")
        .build();
    getView().render(viewState);

    UploadAvatarRequest uploadAvatarRequest = buildRequest(uploadImageUrl, imageType, imageName);
    Subscription subscription =
        uploadUserAvatar.execute(new BaseSubscriber<UploadAvatarResponse>() {
          @Override
          public void onSuccess(UploadAvatarResponse value) {
            getUserDetailFromApi();
          }

          @Override
          public void onError(Throwable error) {
            viewState = NavigationViewState.builder()
                .error(new Exception("Failed to upload image"))
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .build();
            getView().render(viewState);
          }
        }, uploadAvatarRequest);

    compositeSubscription.add(subscription);
  }

  private UploadAvatarRequest buildRequest(String uploadImageUrl, String imageType,
                                           String imageName) {

    UploadAvatarRequest.Value value =
        new UploadAvatarRequest.Value(uploadImageUrl, imageType, imageName);

    UploadAvatarRequest.CustomAttribute customAttribute =
        new UploadAvatarRequest.CustomAttribute("avatar", value);

    List<UploadAvatarRequest.CustomAttribute> customAttributeList = new ArrayList<>();
    customAttributeList.add(customAttribute);

    UploadAvatarRequest.Customer customer = new UploadAvatarRequest.Customer(customAttributeList);

    return new UploadAvatarRequest(customer);
  }
}
