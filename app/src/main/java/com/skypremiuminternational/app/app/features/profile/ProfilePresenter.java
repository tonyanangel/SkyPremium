package com.skypremiuminternational.app.app.features.profile;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.UploadAvatarRequest;
import com.skypremiuminternational.app.domain.interactor.auth.ClearUserData;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.first_time_popup.GetFirstTimePopup;
import com.skypremiuminternational.app.domain.interactor.invite.GetInviteFriendDescription;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetail;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.interactor.user.UploadUserAvatar;
import com.skypremiuminternational.app.domain.models.InviteFriendDescription;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.user.UploadAvatarResponse;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class ProfilePresenter extends BasePresenter<ProfileView> {

  private final GetUserDetail getUserDetail;
  private final GetInviteFriendDescription getInviteFriendDescription;

  private GetUserDetailFromApi getUserDetailFromApi;

  private ClearUserData clearUserData;

  private ProfileViewState viewState;

  private UploadUserAvatar uploadUserAvatar;

  private GetCartCount getCartCount;

  private GetFirstTimePopup getFirstTimePopup;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public ProfilePresenter(GetInviteFriendDescription getInviteFriendDescription,
                          GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                          GetUserDetail getUserDetail, ClearUserData clearUserData,
                          UploadUserAvatar uploadUserAvatar, GetUserDetailFromApi getUserDetailFromApi,
                          GetFirstTimePopup getFirstTimePopup,
                          GetCartCount getCartCount) {
    super(getAppVersion, internalStorageManager);
    viewState = ProfileViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .build();
    this.getInviteFriendDescription = getInviteFriendDescription;
    this.getUserDetail = getUserDetail;
    this.clearUserData = clearUserData;
    this.uploadUserAvatar = uploadUserAvatar;
    this.getUserDetailFromApi = getUserDetailFromApi;
    this.getCartCount = getCartCount;
    this.getFirstTimePopup = getFirstTimePopup;
    attachLoading(getUserDetail);
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
    viewState = ProfileViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .build();
    getView().render(viewState);

    Subscription subscription = getUserDetail.execute(new BaseSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse response) {

        viewState = ProfileViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(response)
            .build();

        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        viewState = ProfileViewState.builder().error(new Exception("Failed to load user detail"))
            .isLoading(false)
            .isSuccess(false)
            .message(viewState.message())
            .build();
        getView().render(viewState);
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getUserDetailFromApi() {
    viewState = ProfileViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .txtLoading("profile")
        .build();
    getView().render(viewState);

    Subscription subscription =
        getUserDetailFromApi.execute(new BaseSubscriber<UserDetailResponse>() {
          @Override
          public void onSuccess(UserDetailResponse response) {

            viewState = ProfileViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message(response)
                .build();

            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            viewState = ProfileViewState.builder()
                .error(new Exception("Failed to load user detail"))
                .isLoading(false)
                .isSuccess(false)
                .message(viewState.message())
                .build();
            getView().render(viewState);
          }
        } );

    compositeSubscription.add(subscription);
  }

  public void onLogout() {

    Subscription subscription = clearUserData.execute(new BaseSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {

      }
    } );
    compositeSubscription.add(subscription);
  }

  void uploadImageToServer(String uploadImageUrl, String imageType, String imageName) {
    viewState = ProfileViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .txtLoading("profile")
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
            Timber.e(error);
            viewState = ProfileViewState.builder().error(new Exception("Failed to upload image"))
                .isLoading(false)
                .isSuccess(false)
                .message(viewState.message())
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

  public void getReferralCodeAndDescription() {
    String referralCode = viewState.message() != null ? viewState.message().getReferralCode() : "";

    getView().showLoading();
    add(getInviteFriendDescription.execute(new SingleSubscriber<InviteFriendDescription>() {
      @Override
      public void onSuccess(InviteFriendDescription value) {
        getView().hideLoading();

        //20200403 WIKI Toan Tran - update  image url
        getView().goToInviteFriend(referralCode, viewState.message().getSalutation(),
            viewState.message().getFirstname(), viewState.message().getLastname(),
            value.description,value.image);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
        getView().render(error);
      }
    } ));
  }

  public void getCartCount() {
    compositeSubscription.add(getCartCount.asObservable(new GetCartCount.Params(true))
        .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }

  public void getFristTimePopup(){
    getView().showLoading();
    add(getFirstTimePopup.execute(new SingleSubscriber<FirstTimePopup>() {
      @Override
      public void onSuccess(FirstTimePopup value) {

        getView().renderFirstTimePopup(value);
        getView().hideLoading();
      }

      @Override
      public void onError(Throwable error) {
        getView().renderFirstTimePopupFailed(error);
        getView().hideLoading();
      }
    } ));
  }

}
