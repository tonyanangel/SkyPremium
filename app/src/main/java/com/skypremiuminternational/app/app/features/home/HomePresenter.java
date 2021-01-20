package com.skypremiuminternational.app.app.features.home;

import android.util.Log;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.ClearUserData;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.cart.AddRenewalToCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.cart.GetRenewalProductPrice;
import com.skypremiuminternational.app.domain.interactor.home.GetHomeCategoryFromApi;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotification;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.home.HomeCategoryResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class HomePresenter extends BaseFragmentPresenter<HomeView> {

  private HomeViewState viewState;
  private final GetHomeCategoryFromApi getCategory;
  private final GetCartCount getCartCount;
  private final UpdateUserDetail updateUserDetail;
  private final AddRenewalToCart addRenewalToCart;
  private final GetRenewalProductPrice getRenewalProductPrice;
  private GetUserDetailFromApi getUserDetailFromApi;
  private CreateCrmToken createCrmToken;
  private GetNotification getNotification;
  private ClearUserData clearUserData;
  private InternalStorageManager internalStorageManager;
  private DataManager  dataManager;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public HomePresenter(GetHomeCategoryFromApi getCategory,
                       GetCartCount getCartCount, GetUserDetailFromApi getUserDetailFromApi,
                       AddRenewalToCart addRenewalToCart, UpdateUserDetail updateUserDetail,
                       GetRenewalProductPrice getRenewalProductPrice, ClearUserData clearUserData,
                       CreateCrmToken createCrmToken, GetNotification getNotification,
                       InternalStorageManager internalStorageManager, DataManager dataManager) {
    this.getCartCount = getCartCount;
    this.getUserDetailFromApi = getUserDetailFromApi;
    viewState = HomeViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .dataList(null)
        .build();
    this.getCategory = getCategory;
    this.addRenewalToCart = addRenewalToCart;
    this.updateUserDetail = updateUserDetail;
    this.getRenewalProductPrice = getRenewalProductPrice;
    this.getNotification = getNotification;
    this.createCrmToken = createCrmToken;
    this.clearUserData = clearUserData;
    this.dataManager = dataManager;
    this.internalStorageManager = internalStorageManager;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  //@Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }


  public void getHomeCategories() {

    onShowLoading();

    Subscription subscription =
        getCategory.execute(new BaseSubscriber<HomeCategoryResponse>() {
          @Override
          public void onSuccess(HomeCategoryResponse response) {

            sortBanner(response);

            viewState = HomeViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message("1")
                .dataList(response)
                .build();
            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            super.onError(error);
            error.printStackTrace();
            viewState = HomeViewState.builder()
                .error(new Exception("Failed to load categories"))
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .dataList(null)
                .build();
            getView().render(viewState);
          }
        } );

    compositeSubscription.add(subscription);
  }

  public void onShowLoading() {
    viewState = HomeViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .dataList(null)
        .build();
    getView().render(viewState);
  }

  private void sortBanner(HomeCategoryResponse response) {
    Collections.sort(response.getBanners(), (o1, o2) -> {
      Integer bannerOrder1 = Integer.valueOf(o1.getOrderBanner());
      Integer bannerOrder2 = Integer.valueOf(o2.getOrderBanner());
      return bannerOrder1.compareTo(bannerOrder2);
    });
  }

  public void getCartCount() {
    compositeSubscription.add(getCartCount.asObservable(new GetCartCount.Params(true))
        .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }

  /**
   * <<START>> 20200317- Nhat Nguyen  only show the Renewal Membership popup on Home page code <<START>>
   **/

  public void getUserDetailFromApi() {

    viewState = HomeViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .response(null)
        .membershipExpired(null)
        .build();
    getView().render(viewState);

    Subscription subscription =
        getUserDetailFromApi.execute(new BaseSubscriber<UserDetailResponse>() {
          @Override
          public void onSuccess(UserDetailResponse response) {
            String date = null;
            if (response.getCustomAttributes().size() > 0) {
              for (CustomAttribute customAttribute : response.getCustomAttributes()) {
                if (customAttribute.getAttributeCode()
                    .equalsIgnoreCase(Constants.MEMBERSHIP_EXPIRED)) {
                  date = customAttribute.getValue();
                }
              }
            }
            viewState = HomeViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message("Success")
                .response(response)
                .membershipExpired(date)
                .build();

            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            viewState = HomeViewState.builder()
                .error(new Exception("Failed to load user detail"))
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .response(null)
                .membershipExpired(null)
                .build();
            getView().render(viewState);
          }
        } );

    compositeSubscription.add(subscription);
  }

  public void addRenewalToCart(boolean usePoints) {
    viewState = HomeViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .response(viewState.response())
        .membershipExpired(viewState.membershipExpired())
        .build();
    getView().render(viewState);

    add(addRenewalToCart.execute(new SingleSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean ignored) {
        getView().goToShoppingCart();
      }

      @Override
      public void onError(Throwable error) {
        viewState = HomeViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(viewState.message())
            .response(viewState.response())
            .membershipExpired(viewState.membershipExpired())
            .build();
        getView().render(viewState);
      }
    }, new AddRenewalToCart.Params(usePoints)));
  }


  public void updateUserNotToShowPrompt() {

    add(updateUserDetail.execute(new SingleSubscriber<UserDetailResponse>() {
      @Override
      public void onSuccess(UserDetailResponse value) {
        Timber.e("Updated user" + value);
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    }, new UpdateUserDetail.Params(buildUpdateUserRequest(), false)));
  }

  private UpdateUserRequest buildUpdateUserRequest() {
    UserDetailResponse userDetailResponse = viewState.response();
    assert userDetailResponse != null;

    List<CustomAttribute> customAttributeList = new ArrayList<>();
    customAttributeList.add(new CustomAttribute("member_expiry_prompt", "0"));
    String member_number = "";

    for (CustomAttribute customAttribute : userDetailResponse.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equals("language")) {
        customAttributeList.add(new CustomAttribute("language", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("currency")) {
        customAttributeList.add(new CustomAttribute("currency", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equals("member_number")) {
        member_number = customAttribute.getValue();
        customAttributeList.add(new CustomAttribute("member_number", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contract_status")) {
        customAttributeList.add(new CustomAttribute("contract_status", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("nationality")) {
        customAttributeList.add(new CustomAttribute("nationality", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contact_country_code")) {
        customAttributeList.add(
            new CustomAttribute("contact_country_code", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("address_country")) {
        customAttributeList.add(new CustomAttribute("address_country", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("salutation")) {
        customAttributeList.add(new CustomAttribute("salutation", customAttribute.getValue()));
      } else if (customAttribute.getAttributeCode().equalsIgnoreCase("contact_number")) {
        customAttributeList.add(new CustomAttribute("contact_number", customAttribute.getValue()));
      }
    }
    UpdateUserRequest.Customer customer = new UpdateUserRequest.Customer(userDetailResponse.getId(),
        userDetailResponse.getWebsiteId(), userDetailResponse.getFirstname(),
        userDetailResponse.getLastname(), userDetailResponse.getEmail(), customAttributeList);
    return new UpdateUserRequest(customer, member_number);
  }

  public void getRenewalPrice() {
    viewState = HomeViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(viewState.message())
        .response(viewState.response())
        .membershipExpired(viewState.membershipExpired())
        .build();
    getView().render(viewState);

    add(getRenewalProductPrice.execute(new SingleSubscriber<Double>() {
      @Override
      public void onSuccess(Double renewalPrice) {
        viewState = HomeViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(viewState.message())
            .response(viewState.response())
            .renewalPrice(renewalPrice)
            .membershipExpired(viewState.membershipExpired())
            .build();
        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        viewState = HomeViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(viewState.message())
            .response(viewState.response())
            .membershipExpired(viewState.membershipExpired())
            .build();
        getView().render(viewState);
      }
    } ));
  }

  public void getRenewalPricevalue() {


    add(getRenewalProductPrice.execute(new SingleSubscriber<Double>() {
      @Override
      public void onSuccess(Double renewalPrice) {

        getView().render(renewalPrice);
      }

      @Override
      public void onError(Throwable error) {

        Log.d("getpricevalueerror", error.toString());
      }
    } ));
  }

  void onLogout() {
    Subscription subscription = clearUserData.execute(new BaseSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {

      }
    } );
    compositeSubscription.add(subscription);
  }

  /**
   * <<END>> 20200317- Nhat Nguyen  only show the Renewal Membership popup on Home page code <<END>>
   **/


  public void getNotificationCount() {

    /**
     *  create new token form CRM server to get Access token -> Call CRM sever with that token - > notification list
     */
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        fetchNotification(response.getAccessToken());

      }

      @Override
      public void onError(Throwable error){
      }
    } ));
  }

  public void fetchNotification(String auth) {
    add(getNotification.execute(new SingleSubscriber<NotificationResponse>() {
      @Override
      public void onSuccess(NotificationResponse value) {
        int numberNotification = 0;
        for (NotificationItem item : value.getDatas()) {
          if (!item.getIsRead()) {
            numberNotification++;
          }
        }


        getView().renderNumberUnread(numberNotification);
      }

      @Override
      public void onError(Throwable error) {

      }
    }, auth));
  }

  public int getNotificationCountLocal() throws FileNotFoundException {
    int numberUnread = 0;

    try {
      for(NotificationItem NotificationItem  : internalStorageManager.getListNotificationByMemberNumber()){
        if(!NotificationItem.getIsRead()){
          numberUnread++;
        }
      }
    }catch (NullPointerException ex){
      return 0;
    }
    return numberUnread;
  }

  DataManager getDataManager(){
    return this.dataManager;
  }

}
