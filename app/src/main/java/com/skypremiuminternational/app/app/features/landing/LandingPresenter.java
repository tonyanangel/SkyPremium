package com.skypremiuminternational.app.app.features.landing;

import android.content.Context;
import android.util.Log;

import com.onesignal.OneSignal;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.listener.MovePillarListener;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.UpdateUserRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.ClearUserData;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.AddRenewalToCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetRenewalProductPrice;
import com.skypremiuminternational.app.domain.interactor.first_time_popup.GetFirstTimePopup;
import com.skypremiuminternational.app.domain.interactor.first_time_popup.SetFirstTime;
import com.skypremiuminternational.app.domain.interactor.notification.ChangeFirstTimeLogin;
import com.skypremiuminternational.app.domain.interactor.notification.CheckFirstLogin;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotification;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotificationSetting;
import com.skypremiuminternational.app.domain.interactor.notification.ReadNotifications;
import com.skypremiuminternational.app.domain.interactor.notification.SendNotification;
import com.skypremiuminternational.app.domain.interactor.notification.UpdateMappVersion;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetRatingOption;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.interactor.user.UpdateUserDetail;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.ean.Child;
import com.skypremiuminternational.app.domain.models.notification.AddtionalSettingData;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.domain.models.popup.FirstTimePopup;
import com.skypremiuminternational.app.domain.models.notification.FirstTimeToTrueRequest;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;
import com.skypremiuminternational.app.push_notification.SkyNotificationOpenHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class LandingPresenter extends BasePresenter<LandingView>  {

  private static String TEMPLATE_ID = BuildConfig.NOTIFICATION_TEMPLATE_ID;

  private final UpdateUserDetail updateUserDetail;
  private final GetFirstTimePopup getFirstTimePopup;
  private final SetFirstTime setFirstTime;
  private final AddRenewalToCart addRenewalToCart;
  private final GetRenewalProductPrice getRenewalProductPrice;
  private final DataManager dataManager;
  private final GetRatingOption getRatingOption;
  private LandingViewState viewState;
  private GetUserDetailFromApi getUserDetailFromApi;
  private ChangeFirstTimeLogin changeFirstTimeLogin;
  private ClearUserData clearUserData;
  private CreateCrmToken createCrmToken;
  private CheckFirstLogin checkFirstLogin;
  private GetNotification getNotification;
  private UpdateMappVersion updateMappVersion;
  private ReadNotifications readNotifications;
  private SendNotification sendNotification;
  private GetNotificationSetting getNotificationSetting;
  private PreferenceUtils preferenceUtils;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private int roomCount;
  private List<CalendarDay> selectedDays;
  private String areaOrHotel;
  private int adultCount;
  private List<Child> children;



  @Inject
  public LandingPresenter(GetRenewalProductPrice getRenewalProductPrice,
                          GetAppVersion getAppVersion, AddRenewalToCart addRenewalToCart,
                          InternalStorageManager internalStorageManager, GetUserDetailFromApi getUserDetailFromApi,
                          ClearUserData clearUserData, UpdateUserDetail updateUserDetail, GetRatingOption getRatingOption,
                          GetFirstTimePopup getFirstTimePopup, SetFirstTime setFirstTime,
                          DataManager dataManager , ChangeFirstTimeLogin changeFirstTimeLogin, CreateCrmToken createCrmToken,
                          GetNotification getNotification, SendNotification sendNotification, CheckFirstLogin checkFirstLogin,
                          PreferenceUtils preferenceUtils, UpdateMappVersion updateMappVersion, GetNotificationSetting getNotificationSetting) {
    super(getAppVersion, internalStorageManager);
    viewState = LandingViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .response(null)
        .membershipExpired(null)
        .build();
    this.addRenewalToCart = addRenewalToCart;
    this.updateUserDetail = updateUserDetail;
    this.clearUserData = clearUserData;
    this.getUserDetailFromApi = getUserDetailFromApi;
    this.getRenewalProductPrice = getRenewalProductPrice;
    this.getRatingOption = getRatingOption;
    this.getFirstTimePopup = getFirstTimePopup;
    this.setFirstTime = setFirstTime;
    this.dataManager = dataManager;
    this.changeFirstTimeLogin = changeFirstTimeLogin;
    this.sendNotification = sendNotification;
    this.createCrmToken = createCrmToken;
    this.getNotification = getNotification;
    this.updateMappVersion = updateMappVersion;
    this.preferenceUtils = preferenceUtils;
    this.getNotificationSetting = getNotificationSetting;
    this.checkFirstLogin = checkFirstLogin;
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

  public void getUserDetailFromApi() {

    viewState = LandingViewState.builder()
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
            viewState = LandingViewState.builder()
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
            viewState = LandingViewState.builder()
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

  void onLogout() {
    Subscription subscription = clearUserData.execute(new BaseSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean value) {

      }
    } );
    compositeSubscription.add(subscription);
  }


  public void getRatingOption() {
    add(getRatingOption.asObservable()
        .subscribe(s -> {
          dataManager.saveRatingOption(s);
        }, Timber::e));
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

  public void addRenewalToCart(boolean usePoints) {
    viewState = LandingViewState.builder()
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
        viewState = LandingViewState.builder()
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

  public void getRenewalPrice() {
    viewState = LandingViewState.builder()
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
        viewState = LandingViewState.builder()
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
        viewState = LandingViewState.builder()
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

        LandingActivity.pricevalue = renewalPrice;
      }

      @Override
      public void onError(Throwable error) {

        Log.d("getpricevalueerror",error.toString());
      }
    } ));
  }

  public void setSearchValues(String areaOrHotel, List<CalendarDay> selectedDays, int roomCount,
                              int adultCount, List<Child> children) {
    this.areaOrHotel = areaOrHotel;
    this.selectedDays = selectedDays;
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.children = children;

    String checkInDate = "";
    String checkOutDate = "";

    if (selectedDays != null && !selectedDays.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT,
          Locale.getDefault());
      if (selectedDays.size() > 1) {
        CalendarDay startDay = selectedDays.get(0);
        checkInDate = dateFormat.format(startDay.getDate());
        CalendarDay endDay = selectedDays.get(selectedDays.size() - 1);
        checkOutDate = dateFormat.format(endDay.getDate());
      } else {
        checkInDate =
            dateFormat.format(selectedDays.get(0).getDate());
      }
    }

    getView().render(areaOrHotel, checkInDate, checkOutDate, roomCount, adultCount, children);
  }

  public void collectSearchValues() {
    getView().showSearchHotelDialog(areaOrHotel, selectedDays, roomCount, adultCount, children);
  }


  public void getFristTimePopup(){

    add(getFirstTimePopup.execute(new SingleSubscriber<FirstTimePopup>() {
      @Override
      public void onSuccess(FirstTimePopup value) {

        getView().renderFirstTimePopup(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().renderFirstTimePopupFailed(error);
      }
    } ));
  }

  public void setFirstTimePopup() {
    add(setFirstTime.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {

      }

      @Override
      public void onError(Throwable error) {

      }
    } ));
  }

  public void sendFirstTimeNotification(){

    String UUID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
    try {
      OneSignal
          .postNotification(new JSONObject(
                  "{\n" +
                      "  \"include_player_ids\": [\""+UUID+"\"],\n" +
                      "  \"template_id\": \""+TEMPLATE_ID+"\" \n" +
                      "}"
              ),
              new OneSignal.PostNotificationResponseHandler() {
                @Override
                public void onSuccess(JSONObject response) {
                  Log.i("OneSignalExample", "postNotification Success: " + response.toString());
                }

                @Override
                public void onFailure(JSONObject response) {
                  Log.e("OneSignalExample", "postNotification Failure: " + response.toString());
                }
              });
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void disableFisrtTime(){
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        changeFirstTimeLogin(response.getAccessToken());

      }
      @Override
      public void onError(Throwable error) {

      }
    } ));
  }

  public void changeFirstTimeLogin(String auth){
    FirstTimeToTrueRequest request = new FirstTimeToTrueRequest();
    request.setIsLogin(false);
    ChangeFirstTimeLogin.Params params = new ChangeFirstTimeLogin.Params(auth, request);
    add(changeFirstTimeLogin.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {


        Log.d("STATE_NOTIFI","changeFirstTimeLogin  ");
      }
      @Override
      public void onError(Throwable error) {

      }
    },params));
  }
  public void intitOneSignal(Context context,
                             MovePillarListener listener ,
                             ChangeNotificationListener notificationListener) {
    OneSignal
        .getCurrentOrNewInitBuilder()
        .setNotificationOpenedHandler(new SkyNotificationOpenHandler(context))
        .setNotificationReceivedHandler(new SkyNotificationReceivedHandler(App.getAppContext(),dataManager,notificationListener));
    OneSignal.setSubscription(true);

  }

  public void getSettingFromAPI() {
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        getNotificationSetting(response.getAccessToken());

      }

      @Override
      public void onError(Throwable error) {

      }
    } ));
  }

  public void getNotificationSetting(String  auth) {


    add(getNotificationSetting.execute(new SingleSubscriber<SettingNotificationResponse>() {
      @Override
      public void onSuccess(SettingNotificationResponse value) {
        AddtionalSettingData additionData = new AddtionalSettingData();
        additionData = value.getData().toAdditionData();


        changeSetting(Constants.ESTORE_DEALS,additionData.isEstoreDeals());
        changeSetting(Constants.MARKETING_ALERT,additionData.isMarketingAlerts());
        changeSetting(Constants.SHOPPING_DEALS,additionData.isShoppingDeals());
        changeSetting(Constants.TRAVEL_DEALS,additionData.isTravelDeals());
        changeSetting(Constants.WELLNESS_DEALS,additionData.isWellnessDeals());
        changeSetting(Constants.WINE_DINE_DEALS,additionData.isWineAndDineDeals());
        changeSetting(Constants.EVENT_INVITES_AND_UP,additionData.isEventInvitesAndUpdate());
      }

      @Override
      public void onError(Throwable error) {

      }
    },auth));
  }

  public void changeSetting(String setting, boolean value) {
    preferenceUtils.save(setting, value);
  }

  public void updateMappVersion(){
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        sendMappVersion(response.getAccessToken());
      }

      @Override
      public void onError(Throwable error) {
        Log.e("Update_Mapp_Version",""+error.getMessage());
      }
    } ));
  }


  public void sendMappVersion(String oAuth){
    add(updateMappVersion.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {

        Log.d("STATE_NOTIFI","sendMappVersion");
        getNotificationSetting(oAuth);
      }

      @Override
      public void onError(Throwable error) {
        Log.e("Update_Mapp_Version",""+error.getMessage());
      }
    },oAuth));
  }






  public void checkFirstLogin(){
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        checkFirstLogin(response.getAccessToken());
      }

      @Override
      public void onError(Throwable error) {
        Log.e("checkFirstLogin",""+error.getMessage());
      }
    } ));
  }

  public void checkFirstLogin(String oAuth){

    add(checkFirstLogin.execute(new BaseSubscriber<CheckLoginResponse>() {
      @Override
      public void onSuccess(CheckLoginResponse response) {
        getView().sendFirstTimeMessage(response.getData().isFirstLoginMapp());
        sendMappVersion(oAuth);
      }

      @Override
      public void onError(Throwable error) {
        Log.e("checkFirstLogin2",""+error.getMessage());
      }
    },oAuth));
  }


}
