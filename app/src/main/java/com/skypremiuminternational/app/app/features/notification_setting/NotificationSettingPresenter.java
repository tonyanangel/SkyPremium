package com.skypremiuminternational.app.app.features.notification_setting;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotificationSetting;
import com.skypremiuminternational.app.domain.interactor.notification.UpdateSettingNotification;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.notification.SettingDataRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationRequest;
import com.skypremiuminternational.app.domain.models.notification.SettingNotificationResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static com.skypremiuminternational.app.app.utils.Constants.*;

public class NotificationSettingPresenter extends BasePresenter<NotificationSettingView> {

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  InternalStorageManager internalStorageManager;
  PreferenceUtils preferenceUtils;
  DataManager dataManager;
  GetCartCount getCartCount;
  GetNotificationSetting getNotificationSetting;
  UpdateSettingNotification updateNotification;
  CreateCrmToken createCrmToken;

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  @Inject
  public NotificationSettingPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                                      PreferenceUtils preferenceUtils, DataManager dataManager,
                                      GetCartCount getCartCount, GetNotificationSetting getNotificationSetting,
                                      UpdateSettingNotification updateNotification, CreateCrmToken createCrmToken) {
    super(getAppVersion, internalStorageManager);
    this.internalStorageManager = internalStorageManager;
    this.preferenceUtils = preferenceUtils;
    this.dataManager = dataManager;
    this.getCartCount = getCartCount;
    this.updateNotification = updateNotification;
    this.getNotificationSetting = getNotificationSetting;
    this.createCrmToken = createCrmToken;

  }


  public InternalStorageManager getInternalStorageManager() {
    return internalStorageManager;
  }

  public PreferenceUtils getPreferenceUtils() {
    return preferenceUtils;
  }

  public DataManager getDataManager() {
    return dataManager;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }


  public void changeSetting(String setting, boolean value) {
    preferenceUtils.save(setting, value);
  }

  public Map<String, Boolean> getSetting() {
    Map<String, Boolean> mapSetting = new HashMap<>();
    for (String key : getMapSetting().keySet()) {
      mapSetting.put(key, preferenceUtils.get(key, false));
    }
    return mapSetting;
  }


  public Map<String, Boolean> getMapSetting() {
    Map<String, Boolean> map = new HashMap<>();

    map.put(MARKETING_ALERT, false);
    map.put(EVENT_INVITES_AND_UP, false);
    map.put(TRAVEL_DEALS, false);
    map.put(WINE_DINE_DEALS, false);
    map.put(SHOPPING_DEALS, false);
    map.put(ESTORE_DEALS, false);
    map.put(WELLNESS_DEALS, false);
    return map;
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }


  public void getSettingFromAPI() {
    getView().showDialogLoading();
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        getNotificationSetting(response.getAccessToken());
      }

      @Override
      public void onError(Throwable error) {
        getView().showDialogLoading();
      }
    } ));
  }

  public void getNotificationSetting(String  auth) {


    add(getNotificationSetting.execute(new SingleSubscriber<SettingNotificationResponse>() {
      @Override
      public void onSuccess(SettingNotificationResponse value) {
        getView().hideDialogLoading();
        getView().renderSetting(value);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    },auth));
  }


  public void updateNotification() {
    getView().showDialogLoading();
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        sendSettingNotification(response.getAccessToken());
      }

      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    } ));
  }

  public void sendSettingNotification(String auth){
    SettingNotificationRequest request = new SettingNotificationRequest();
    SettingDataRequest dataRequest = new SettingDataRequest();

    dataRequest = getSettingRequest();

    request.setMemberId(internalStorageManager.getUserDetail().getMemberNumber());
    request.setSettings(dataRequest);

    UpdateSettingNotification.Params params = new UpdateSettingNotification.Params(auth, request);
    add(updateNotification.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {
        getView().hideDialogLoading();
        getView().renderSaveSetting();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
        getView().renderSaveSetting();
      }
    },params));
  }

  public SettingDataRequest getSettingRequest() {
    SettingDataRequest request = new SettingDataRequest();
    Map<String, Boolean> mapSetting = getSetting();

    for(String key : mapSetting.keySet()){
      switch (key) {
        case Constants.ESTORE_DEALS     :
          request.setEstore(mapSetting.get(key));
          break;
        case Constants.MARKETING_ALERT  :
          request.setMarketing(mapSetting.get(key));
          break;
        case Constants.SHOPPING_DEALS  :
          request.setShopping(mapSetting.get(key));
          break;
        case Constants.TRAVEL_DEALS     :
          request.setTravel(mapSetting.get(key));
          break;
        case Constants.WELLNESS_DEALS     :
          request.setWellness(mapSetting.get(key));
          break;
        case Constants.WINE_DINE_DEALS      :
          request.setWinedine(mapSetting.get(key));
          break;
        case Constants.EVENT_INVITES_AND_UP :
          request.setEvent(mapSetting.get(key));
          break;
      }
    }

    return request;
  }
}
