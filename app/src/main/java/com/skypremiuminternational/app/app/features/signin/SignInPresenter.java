package com.skypremiuminternational.app.app.features.signin;

import android.util.Log;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.AdminSignInRequest;
import com.skypremiuminternational.app.data.network.request.SignInRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.category.GetCategoryFromApi;
import com.skypremiuminternational.app.domain.interactor.country_code.GetCountryCodesFromAPI;
import com.skypremiuminternational.app.domain.interactor.nationality.GetNationalitiesFromAPI;
import com.skypremiuminternational.app.domain.interactor.notification.CheckFirstLogin;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotificationSetting;
import com.skypremiuminternational.app.domain.interactor.notification.UpdateMappVersion;
import com.skypremiuminternational.app.domain.interactor.notification.UpdateOneSignalDataToCrm;
import com.skypremiuminternational.app.domain.interactor.notification.one_signal.GetDeviceInfo;
import com.skypremiuminternational.app.domain.interactor.signin.AdminSignIn;
import com.skypremiuminternational.app.domain.interactor.signin.SignIn;
import com.skypremiuminternational.app.domain.interactor.user.GetUserDetailFromApi;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.notification.AddtionalSettingData;
import com.skypremiuminternational.app.domain.models.notification.CheckLoginResponse;
import com.skypremiuminternational.app.domain.models.notification.DataFromOneSignal;
import com.skypremiuminternational.app.domain.models.notification.OneSignalDeviceDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.one_signal.OSPlayer;
import com.skypremiuminternational.app.domain.models.user.CustomAttribute;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johnsonmaung on 9/19/17.
 */

public class SignInPresenter extends BasePresenter<SignInView> {

  private final SignIn signIn;

  private final AdminSignIn adminSignIn;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  private final GetCategoryFromApi getCategoryFromApi;

  private final GetNationalitiesFromAPI getNationalitiesFromAPI;

  private final GetCountryCodesFromAPI getCountryCodes;

  private GetUserDetailFromApi getUserDetailFromApi;

  private SignInViewState viewState;

  private final CreateCrmToken createCrmToken;

  private final GetNotificationSetting getNotificationSetting;

  private final UpdateMappVersion updateMappVersion;

  private final GetDeviceInfo getDeviceInfo;

  private final CheckFirstLogin checkFirstLogin;

  private final UpdateOneSignalDataToCrm updateOneSignalDataToCrm;

  private final DataManager dataManager;


  private PreferenceUtils preferenceUtils;

  @Inject
  public SignInPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                         DataManager dataManager,
                         SignIn signIn, AdminSignIn adminSignIn, GetCategoryFromApi getCategoryFromApi,
                         GetCountryCodesFromAPI getCountryCodes, GetNationalitiesFromAPI getNationalitiesFromAPI,
                         GetUserDetailFromApi getUserDetailFromApi , CreateCrmToken createCrmToken,
                         GetNotificationSetting getNotificationSetting, PreferenceUtils preferenceUtils,
                         UpdateMappVersion updateMappVersion, GetDeviceInfo getDeviceInfo,
                         CheckFirstLogin checkFirstLogin, UpdateOneSignalDataToCrm updateOneSignalDataToCrm) {
    super(getAppVersion, internalStorageManager);
    viewState = SignInViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .message(null)
        .groupId(0)
        .build();
    this.signIn = signIn;
    this.adminSignIn = adminSignIn;
    this.getCategoryFromApi = getCategoryFromApi;
    this.getCountryCodes = getCountryCodes;
    this.getNationalitiesFromAPI = getNationalitiesFromAPI;
    this.getUserDetailFromApi = getUserDetailFromApi;
    this.getNotificationSetting = getNotificationSetting;
    this.createCrmToken = createCrmToken;
    this.preferenceUtils = preferenceUtils;
    this.updateMappVersion = updateMappVersion;
    this.getDeviceInfo = getDeviceInfo;
    this.checkFirstLogin = checkFirstLogin;
    this.updateOneSignalDataToCrm = updateOneSignalDataToCrm;
    this.dataManager = dataManager;
    attachLoading(signIn, getCategoryFromApi);
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

  public void onSignIn(final String username, final String password) {

    viewState = SignInViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .groupId(0)
        .build();
    getView().render(viewState);

    SignInRequest signInRequest = new SignInRequest(username, password);

    Subscription subscription = signIn.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {
        getUserDatasFromApi();
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = SignInViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .groupId(0)
            .build();
        getView().render(viewState);
      }
    }, signInRequest);

    compositeSubscription.add(subscription);
  }

  public void onAdminSignIn(final String username, final String password) {

    AdminSignInRequest signInRequest = new AdminSignInRequest(username, password);

    Subscription subscription = adminSignIn.execute(new BaseSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody response) {
        //try {
        //  viewState = SignInViewState.builder()
        //      .error(null)
        //      .isLoading(false)
        //      .isSuccess(true)
        //      .message(response.string())
        //      .build();
        //} catch (IOException e) {
        //  e.printStackTrace();
        //}
        //getView().render(viewState);
        //getCategories();
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        error.printStackTrace();
        viewState = SignInViewState.builder()
            .error(error)
            .isLoading(false)
            .isSuccess(false)
            .message(null)
            .groupId(0)
            .build();
        getView().render(viewState);
      }
    }, signInRequest);

    compositeSubscription.add(subscription);
  }

  public void getUserDatasFromApi() {

    Subscription subscription =
        getUserDetailFromApi.execute(new BaseSubscriber<UserDetailResponse>() {
          @Override
          public void onSuccess(UserDetailResponse response) {
            viewState = SignInViewState.builder()
                .error(null)
                .isLoading(false)
                .isSuccess(true)
                .message("groupId")
                .groupId(response.getGroupId())
                .build();
            getView().render(viewState);
          }

          @Override
          public void onError(Throwable error) {
            viewState = SignInViewState.builder()
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

  private String parsegroupId(UserDetailResponse response) {
    String date = "";
    for (CustomAttribute customAttribute : response.getCustomAttributes()) {
      if (customAttribute.getAttributeCode().equalsIgnoreCase("member_expiry_date")) {
        return customAttribute.getValue();
      }
    }
    return date;
  }


  public void getSettingFromAPI() {
    //getView().showDialogLoading("Getting setting ...");
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        getNotificationSetting(response.getAccessToken());
      }

      @Override
      public void onError(Throwable error) {

        //getView().renderGotoLanding();
      }
    } ));
  }

  public void getNotificationSetting(String  auth) {


    add(getNotificationSetting.execute(new SingleSubscriber<SettingNotificationResponse>() {
      @Override
      public void onSuccess(SettingNotificationResponse value) {

        Log.d("STATE_NOTIFI","getNotificationSetting");
        AddtionalSettingData additionData = new AddtionalSettingData();
        additionData = value.getData().toAdditionData();


        changeSetting(Constants.ESTORE_DEALS,additionData.isEstoreDeals());
        changeSetting(Constants.MARKETING_ALERT,additionData.isMarketingAlerts());
        changeSetting(Constants.SHOPPING_DEALS,additionData.isShoppingDeals());
        changeSetting(Constants.TRAVEL_DEALS,additionData.isTravelDeals());
        changeSetting(Constants.WELLNESS_DEALS,additionData.isWellnessDeals());
        changeSetting(Constants.WINE_DINE_DEALS,additionData.isWineAndDineDeals());
        changeSetting(Constants.EVENT_INVITES_AND_UP,additionData.isEventInvitesAndUpdate());
        //getView().renderSetting(value);
      }

      @Override
      public void onError(Throwable error) {
        //getView().renderGotoLanding();
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

        Log.d("STATE_NOTIFI","updateOneSignalDatatoCrm");
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
  public DataManager getDataManager() {
    return dataManager;
  }

  public void sendExtenalUserId(){
    OneSignal.removeExternalUserId();
    OneSignal.setExternalUserId(getDataManager().getUserDetail().getMemberNumber());
  }
}
