package com.skypremiuminternational.app.app.features.splash;

import android.content.Context;
import android.util.Log;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.listener.MovePillarListener;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.category.GetCategoryFromApi;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodesFromAPI;
import com.skypremiuminternational.app.domain.interactor.nationality.GetNationalitiesFromAPI;
import com.skypremiuminternational.app.domain.interactor.notification.CheckFirstLogin;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotificationSetting;
import com.skypremiuminternational.app.domain.interactor.notification.ReadNotifications;
import com.skypremiuminternational.app.domain.interactor.notification.SendNotification;
import com.skypremiuminternational.app.domain.interactor.notification.UpdateMappVersion;
import com.skypremiuminternational.app.domain.interactor.notification.UpdateOneSignalDataToCrm;
import com.skypremiuminternational.app.domain.interactor.notification.one_signal.GetDeviceInfo;
import com.skypremiuminternational.app.domain.interactor.phone_code.GetPhoneCodesFromAPI;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetRatingOption;
import com.skypremiuminternational.app.domain.interactor.splash.CheckUserLoggedIn;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.nationality.Nationality;
import com.skypremiuminternational.app.domain.models.notification.AddtionalSettingData;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;
import com.skypremiuminternational.app.domain.models.notification.DataFromOneSignal;
import com.skypremiuminternational.app.domain.models.notification.OneSignalDeviceDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.one_signal.OSPlayer;
import com.skypremiuminternational.app.domain.models.phone_code.PhoneCode;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;
import com.skypremiuminternational.app.push_notification.SkyNotificationOpenHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class SplashPresenter extends BasePresenter<SplashView> {

  private final CheckUserLoggedIn checkUserLoggedIn;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  private SplashViewState viewState;

  private final GetCategoryFromApi getCategoryFromApi;

  private final GetPhoneCodesFromAPI getPhoneCodesFromAPI;

  private final GetNationalitiesFromAPI getNationalitiesFromAPI;

  private final GetCountryCodesFromAPI getCountryCodes;

  private final DataManager dataManager;

  private final GetRatingOption getRatingOption;

  private final SendNotification sendNotification;

  private final ReadNotifications readNotifications;

  private final CreateCrmToken createCrmToken;

  private final UpdateMappVersion updateMappVersion;

  private final GetDeviceInfo getDeviceInfo;

  private final GetNotificationSetting getNotificationSetting;

  private GetUserDetailFromApi getUserDataFromApi;

private CheckFirstLogin checkFirstLogin;

  private UpdateOneSignalDataToCrm updateOneSignalDataToCrm;

  private PreferenceUtils preferenceUtils;

  private  InternalStorageManager internalStorageManager;

  @Inject
  public SplashPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager, GetRatingOption getRatingOption,
                         CheckUserLoggedIn checkUserLoggedIn, GetCategoryFromApi getCategoryFromApi,
                         GetCountryCodesFromAPI getCountryCodes, GetPhoneCodesFromAPI getPhoneCodesFromAPI,
                         GetNationalitiesFromAPI getNationalitiesFromAPI, GetUserDetailFromApi getUserDataFromApi,
                         DataManager dataManager,PreferenceUtils preferenceUtils, SendNotification sendNotification,
                         CreateCrmToken createCrmToken , GetNotificationSetting getNotificationSetting,
                         UpdateMappVersion updateMappVersion, GetDeviceInfo getDeviceInfo,
                         UpdateOneSignalDataToCrm updateOneSignalDataToCrm, CheckFirstLogin checkFirstLogin,
                         ReadNotifications readNotifications) {
    super(getAppVersion, internalStorageManager);
    viewState = SplashViewState.builder()
        .isUserLoggedIn(false)
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .groupId(0)
        .build();

    this.checkUserLoggedIn = checkUserLoggedIn;
    this.getCategoryFromApi = getCategoryFromApi;
    this.getCountryCodes = getCountryCodes;
    this.getPhoneCodesFromAPI = getPhoneCodesFromAPI;
    this.getNationalitiesFromAPI = getNationalitiesFromAPI;
    this.getUserDataFromApi = getUserDataFromApi;
    this.getRatingOption = getRatingOption;
    this.dataManager = dataManager;
    this.preferenceUtils = preferenceUtils;
    this.internalStorageManager = internalStorageManager;
    this.sendNotification = sendNotification;
    this.createCrmToken = createCrmToken;
    this.getNotificationSetting = getNotificationSetting;
    this.getDeviceInfo = getDeviceInfo;
    this.updateMappVersion = updateMappVersion;
    this.updateOneSignalDataToCrm = updateOneSignalDataToCrm;
    this.checkFirstLogin = checkFirstLogin;
    this.readNotifications = readNotifications;

    attachLoading(checkUserLoggedIn);
  }

  public void checkUserLoggedIn() {
    Subscription subscription = checkUserLoggedIn.execute(new BaseSubscriber<Boolean>() {
      @Override
      public void onSuccess(Boolean aBoolean) {
        getUserDetailFromApi();
                /*viewState = SplashViewState.builder()
                        .isUserLoggedIn(aBoolean)
                        .error(null)
                        .isLoading(false)
                        .isSuccess(true)
                        .message(null)
                        .build();
                getView().render(viewState);*/
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = SplashViewState.builder()
            .isUserLoggedIn(false)
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .groupId(0)
            .build();
        getView().render(viewState);
      }
    } );
    compositeSubscription.add(subscription);
  }

  private void getUserDetailFromApi() {
    Subscription subscription =
        getUserDataFromApi.execute(new BaseSubscriber<UserDetailResponse>() {
          @Override
          public void onSuccess(UserDetailResponse response) {
            viewState = SplashViewState.builder()
                .isUserLoggedIn(true)
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message(null)
                .groupId(response.getGroupId())
                .build();
            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            super.onError(error);
            error.printStackTrace();
            viewState = SplashViewState.builder()
                .isUserLoggedIn(false)
                .error(error)
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .groupId(0)
                .build();
            getView().render(viewState);
          }
        } );

    compositeSubscription.add(subscription);
  }

  private String getgroupId(UserDetailResponse response) {
    String date = "";
    for (CustomAttribute customAttribute : response.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("member_days_expiring")) {
        return customAttribute.getValue();
      }
    }
    return date;
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

  public void getCategories() {

    Subscription subscription = getCategoryFromApi.asObservable()
        .flatMapIterable(CategoryResponse::getChildrenData)
        .doOnNext(this::updateCategoryIds)
        .toList()
        .subscribe(childDatas -> getCountryCodes(), throwable -> {
          viewState = SplashViewState.builder()
              .isUserLoggedIn(false)
              .error(throwable)
              .isLoading(false)
              .isSuccess(false)
              .message(null)
              .groupId(0)
              .build();
          getView().render(viewState);
        });

    compositeSubscription.add(subscription);
  }


  private void updateCategoryIds(ChildData childData) {
    switch (childData.getName()) {
      case Constants.E_STORE_NAME:
        Constants.E_STORE = String.valueOf(childData.getId());
        Constants.E_STORE_ID = childData.getId();
        break;
      case Constants.TRAVEL_NAME:
        Constants.TRAVEL = String.valueOf(childData.getId());
        Constants.TRAVEL_ID = childData.getId();
        break;
      case Constants.WELLNESS_NAME:
        Constants.WELLNESS = String.valueOf(childData.getId());
        Constants.WELLNESS_ID = childData.getId();
        break;
      case Constants.WINE_AND_DINE_NAME:
        Constants.WINE_AND_DINE = String.valueOf(childData.getId());
        Constants.WINE_AND_DINE_ID = childData.getId();
        break;
      case Constants.SHOPPING_NAME:
        Constants.SHOPPING = String.valueOf(childData.getId());
        Constants.SHOPPING_ID = childData.getId();
        break;
    }
  }

  public void getCountryCodes() {

    Subscription subscription = getCountryCodes.execute(new BaseSubscriber<List<CountryCode>>() {
      @Override
      public void onSuccess(List<CountryCode> response) {
        Timber.e(response.toString());
        getPhoneCodes();
      }

      @Override
      public void onError(Throwable error) {
       // super.onError(error);
        error.printStackTrace();
        viewState = SplashViewState.builder()
            .isUserLoggedIn(false)
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .groupId(0)
            .build();
        getView().render(viewState);
        getPhoneCodes();
      }
    } );

    compositeSubscription.add(subscription);
  }

  public void getPhoneCodes() {

    Subscription subscription = getPhoneCodesFromAPI.execute(new BaseSubscriber<PhoneCode>() {
      @Override
      public void onSuccess(PhoneCode response) {
        Timber.e(response.toString());
        getNationalities();
      }

      @Override
      public void onError(Throwable error) {
       // super.onError(error);
        error.printStackTrace();
        viewState = SplashViewState.builder()
            .isUserLoggedIn(false)
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .groupId(0)
            .build();
        getView().render(viewState);
        getNationalities();
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

  public void getNationalities() {

    Subscription subscription =
        getNationalitiesFromAPI.execute(new BaseSubscriber<List<Nationality>>() {
          @Override
          public void onSuccess(List<Nationality> response) {
            Timber.e(response.toString());
            checkUserLoggedIn();
          }

          @Override
          public void onError(Throwable error) {
           // super.onError(error);
            error.printStackTrace();
            viewState = SplashViewState.builder()
                .isUserLoggedIn(false)
                .error(error)
                .isLoading(false)
                .isSuccess(false)
                .message(null)
                .groupId(0)
                .build();
            getView().render(viewState);
            checkUserLoggedIn();
          }
        } );

    compositeSubscription.add(subscription);
  }


  public DataManager getDataManager() {
    return dataManager;
  }

  public PreferenceUtils getPreferenceUtils() {
    return preferenceUtils;
  }


  public void saveNotificarion(){

    App.isSendingNotification = true;
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        sendNotification(response.getAccessToken());
      }
      @Override
      public void onError(Throwable error) {
        App.isSendingNotification = false;
      }
    } ));
  }

  public void sendNotification(String auth){
    List<NotificationItem>  listNotification =  new ArrayList<>();
    try {
      listNotification = internalStorageManager.getListNotification();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    if(listNotification==null){
      return;
    }

    NotificationRequest request = new NotificationRequest();
    request.setDeviceId(OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
    request.setMemberId(internalStorageManager.getUserDetail().getMemberNumber());
    request.setNotifications(listNotification);

    SendNotification.Params params = new SendNotification.Params(auth,request);
    add(sendNotification.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {
        Log.d("TAG" , "SUCCESS"+value.toString());

        List<NotificationItem>  newNotifications =  new ArrayList<>();
        internalStorageManager.saveListNotification(newNotifications);
        App.isSendingNotification = false;
      }

      @Override
      public void onError(Throwable error) {
        Log.d("TAG" , ""+error.getMessage());
        App.isSendingNotification = false;
      }
    },params));
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
      }

      @Override
      public void onError(Throwable error) {
        Log.e("Update_Mapp_Version",""+error.getMessage());
      }
    },oAuth));
  }


  /**
   *  Get device information from OneSignal
   */
  public void getDeviceInfo(){


    String playerId = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();

    add(getDeviceInfo.execute(new BaseSubscriber<OSPlayer>() {
      @Override
      public void onSuccess(OSPlayer response) {

        DataFromOneSignal data = new DataFromOneSignal();


        data.setFirstSession(response.getCreatedAt());
        data.setLastSession(response.getLastActive());
        data.setSessionCount(response.getSessionCount());
        data.setTotalUsageDuration(response.getPlaytime());

        updateOneSignalDatatoCrm(data);



      }

      @Override
      public void onError(Throwable error) {
        Log.e("Get_Device_Info",""+error.getMessage());
      }
    },playerId));
  }

  /**
   *  Update One Signal data to CRM
   */
  public void updateOneSignalDatatoCrm(String oAuth,DataFromOneSignal data ){
    OneSignalDeviceDataRequest request = new OneSignalDeviceDataRequest();
    request.setDataFromOneSignal(data);
    UpdateOneSignalDataToCrm.Params params = new UpdateOneSignalDataToCrm.Params(oAuth ,request );
    add(updateOneSignalDataToCrm.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {
        checkFirstLogin();
      }

      @Override
      public void onError(Throwable error) {
        Log.e("Get_Device_Info",""+error.getMessage());
      }
    },params));
  }

  public void updateOneSignalDatatoCrm(DataFromOneSignal data){
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        updateOneSignalDatatoCrm(response.getAccessToken(),data);
      }

      @Override
      public void onError(Throwable error) {
        Log.e("Update_Mapp_Version",""+error.getMessage());
      }
    } ));
  }


  public void checkFirstLogin(){
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        checkFirstLogin(response.getAccessToken());
      }

      @Override
      public void onError(Throwable error) {
        getView().renderGotoLanding();
        Log.e("checkFirstLogin",""+error.getMessage());
      }
    } ));
  }

  public void checkFirstLogin(String oAuth){

    add(checkFirstLogin.execute(new BaseSubscriber<CheckLoginResponse>() {
      @Override
      public void onSuccess(CheckLoginResponse response) {
        getView().renderGotoLanding(response.getData().isFirstLoginMapp());
      }

      @Override
      public void onError(Throwable error) {
        getView().renderGotoLanding();
        Log.e("checkFirstLogin2",""+error.getMessage());
      }
    },oAuth));
  }

  public void sendExtenalUserId(){
    OneSignal.removeExternalUserId();
    OneSignal.setExternalUserId(getDataManager().getUserDetail().getMemberNumber());
  }

  public void intitOneSignal(Context context, MovePillarListener listener , ChangeNotificationListener changeNotificationListener) {
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
    OneSignal.startInit(context)
        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
        .unsubscribeWhenNotificationsAreDisabled(true)
        .setNotificationReceivedHandler(new SkyNotificationReceivedHandler(context, getDataManager(),changeNotificationListener))
        .setNotificationOpenedHandler(new SkyNotificationOpenHandler(context))
        .init();
  }


  boolean isOpenNotification(){
    return internalStorageManager.getOpenNotification();
  }

  void saveOpenNotification(boolean isOpen){
    internalStorageManager.saveOpenNotification(isOpen);
  }

}
