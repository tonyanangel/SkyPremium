package com.skypremiuminternational.app.app.features.notifications;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.onesignal.OneSignal;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.details.DetailItemSimple;
import com.skypremiuminternational.app.domain.interactor.invite.GetInviteFriendDescription;
import com.skypremiuminternational.app.domain.interactor.notification.DeleteNotifications;
import com.skypremiuminternational.app.domain.interactor.notification.GetNotification;
import com.skypremiuminternational.app.domain.interactor.notification.ReadNotifications;
import com.skypremiuminternational.app.domain.interactor.notification.SendNotification;
import com.skypremiuminternational.app.domain.models.InviteFriendDescription;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationDeleteRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.NotificationResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationUpdateRequest;
import com.skypremiuminternational.app.domain.models.notification.UpdateDelItem;
import com.skypremiuminternational.app.domain.models.notification.UpdateReadItem;
import com.skypremiuminternational.app.domain.models.user.UserDetailResponse;
import com.skypremiuminternational.app.push_notification.ChangeNotificationListener;
import com.skypremiuminternational.app.push_notification.SkyNotificationOpenHandler;
import com.skypremiuminternational.app.push_notification.SkyNotificationReceivedHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscription;


public class NotificationPresenter extends BasePresenter<NotificationView> {

  InternalStorageManager internalStorageManager;
  DataManager dataManager;

  CreateCrmToken createCrmToken;
  GetNotification getNotification;
  DeleteNotifications deleteNotifications;
  ReadNotifications readNotifications;
  SendNotification sendNotification;
  DetailItemSimple detailItemSimple;
  GetInviteFriendDescription getInviteFriendDescription;

  @Inject
  public NotificationPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                               DataManager dataManager, CreateCrmToken createCrmToken, GetNotification getNotification,
                               ReadNotifications readNotifications, DeleteNotifications deleteNotifications,
                               SendNotification sendNotification, DetailItemSimple detailItemSimple,
                               GetInviteFriendDescription getInviteFriendDescription) {
    super(getAppVersion, internalStorageManager);
    this.internalStorageManager = internalStorageManager;
    this.createCrmToken = createCrmToken;
    this.getNotification = getNotification;
    this.dataManager = dataManager;
    this.deleteNotifications = deleteNotifications;
    this.readNotifications = readNotifications;
    this.sendNotification = sendNotification;
    this.detailItemSimple = detailItemSimple;
    this.getInviteFriendDescription = getInviteFriendDescription;
  }

  @Override
  public void add(Subscription subscription) {

  }


  public DataManager getDataManager() {
    return dataManager;
  }

  public void getNotification(){

    /**
     *  create new token form CRM server to get Access token -> Call CRM sever with that token - > notification list
     */
    getView().showDialogLoading();
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        fetchNotification(response.getAccessToken());

      }
      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    } ));
  }


  /**
   * Wiki Toan Tran - create new token to connect to CRM
   */
  public void  createCrmToken(){
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {

      }
      @Override
      public void onError(Throwable error) {

      }
    } ));
  }


  /**
   * WIki Toan Tran - fetch data from CRM server
   * @param auth
   */
  public void fetchNotification(String auth){
    getView().showDialogLoading();
    add(getNotification.execute(new SingleSubscriber<NotificationResponse>() {
      @Override
      public void onSuccess(NotificationResponse value) {
          getView().renderListNotification(value.getDatas());
      }

      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    }, auth));
  }


  public void makeReadNotifications(List<UpdateReadItem> list){
    getView().showDialogLoading();
    NotificationUpdateRequest request = new NotificationUpdateRequest();
    request.setUpdateItem(list);

    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        readNotifications(response.getAccessToken(),request);
      }
      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    } ));
  }
  public void makeReadSingle(List<UpdateReadItem> list, NotificationItem item){
    getView().showDialogLoading();
    NotificationUpdateRequest request = new NotificationUpdateRequest();
    request.setUpdateItem(list);

    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        readNotificationSingle(response.getAccessToken(),request, item);
      }
      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    } ));
  }

  public void makeDeleteNotifications(List<UpdateDelItem> list){
    NotificationDeleteRequest request = new NotificationDeleteRequest();
    request.setUpdateItem(list);
    getView().showDialogLoading();
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        deleteNotifications(response.getAccessToken(),request);
      }
      @Override
      public void onError(Throwable error) {
        getView().showDialogLoading();
      }
    } ));
  }

  public void readNotifications(String auth, NotificationUpdateRequest request){
    ReadNotifications.Params params = new ReadNotifications.Params(auth, request);
    add(readNotifications.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {
          //getView().renderListNotification(value);
        getView().hideDialogLoading();
      }

      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    }, params));
  }

  public void readNotificationSingle(String auth, NotificationUpdateRequest request,NotificationItem item ){
    ReadNotifications.Params params = new ReadNotifications.Params(auth, request);
    add(readNotifications.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {
          //getView().renderListNotification(value);
        getView().hideDialogLoading();
        getView().renderGoToDetails(item);

      }

      @Override
      public void onError(Throwable error) {
        getView().hideDialogLoading();
      }
    }, params));
  }
  public void deleteNotifications(String auth, NotificationDeleteRequest request){
    DeleteNotifications.Params params = new DeleteNotifications.Params(auth, request);

    add(deleteNotifications.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {
         // getView().renderListNotification(value);

        Log.d("TAG" , "SUCCESS"+value.toString());
        getView().hideDialogLoading();
      }

      @Override
      public void onError(Throwable error) {
        Log.d("TAG" , ""+error.getMessage());
        getView().hideDialogLoading();
      }
    }, params));
  }

  public void saveNotificarion(){
    getView().showDialogLoading();
    App.isSendingNotification = true;
    add(createCrmToken.execute(new BaseSubscriber<CrmTokenResponse>() {
      @Override
      public void onSuccess(CrmTokenResponse response) {
        sendNotification(response.getAccessToken());
      }
      @Override
      public void onError(Throwable error) {
        App.isSendingNotification = false;

        getView().hideDialogLoading();
      }
    } ));
  }

  public void initNotificationLocal(){
    List<NotificationItem> list = new ArrayList<>();
    try {
      list = internalStorageManager.getListNotificationByMemberNumber();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    getView().renderListNotification(list);
  }
  public void changeNotificationLocal(){
    List<NotificationItem> list = new ArrayList<>();
    try {
      list = internalStorageManager.getListNotificationByMemberNumber();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    getView().renderChangeListNotification(list);
  }


  public void sendNotification(String auth){
    getView().showDialogLoading();
    List<NotificationItem>  listNotification = new ArrayList<>();
    try {
      listNotification = internalStorageManager.getListNotification();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    NotificationRequest request = new NotificationRequest();
    request.setDeviceId(OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
    request.setMemberId(internalStorageManager.getUserDetail().getMemberNumber());
    request.setNotifications(listNotification);

    SendNotification.Params params = new SendNotification.Params(auth,request);
    add(sendNotification.execute(new SingleSubscriber<ResponseBody>() {
      @Override
      public void onSuccess(ResponseBody value) {
        getView().hideDialogLoading();
        Log.d("TAG" , "SUCCESS"+value.toString());

        List<NotificationItem>  newNotifications =  new ArrayList<>();
        internalStorageManager.saveListNotification(newNotifications);
        App.isSendingNotification = false;
        getNotification();
      }

      @Override
      public void onError(Throwable error) {
        Log.d("TAG" , ""+error.getMessage());
        App.isSendingNotification = false;
        getView().hideDialogLoading();
      }
    },params));
  }


  void makeAsReadOneLocal(NotificationItem item ){
    List<NotificationItem> list = null;

    try{
      list = internalStorageManager.getListNotificationByMemberNumber();
    }catch (FileNotFoundException ex){}

    if(list==null){

      return;
    }

    for(NotificationItem notificationItem : list){
      if(notificationItem.getNotificationId().equalsIgnoreCase(item.getNotificationId())){
        notificationItem.setIsRead(true);
        break;
      }
    }

    internalStorageManager.saveListNotification(list);

  }

  void makeAsReadMutilLocal(List<NotificationItem> listNotificationItems ){
    for (NotificationItem item : listNotificationItems) {
      if (item.isChecked()) {
        item.setIsRead(true);
      }
    }
    internalStorageManager.saveListNotification(listNotificationItems);
  }

  void saveNotificationLocal(List<NotificationItem> listNotificationItems ){
    internalStorageManager.saveListNotification(listNotificationItems);
  }


  void checkDetailItemSimple(String redirect,String sku,String filter,String sortBy,String page){
    getView().showDialogLoading();
    add(detailItemSimple.execute(new SingleSubscriber<DetailsItem>() {
      @Override
      public void onSuccess(DetailsItem value) {
        if(value.getStatus()!=null && value.getStatus() == 1){
          getView().onExistEstoreProduct(redirect,sku);
        }else {
          getView().onNonExistEstoreProduct(redirect,filter,sortBy,page);
        }
        getView().hideDialogLoading();
      }

      @Override
      public void onError(Throwable error) {
        getView().onNonExistEstoreProduct(redirect,filter,sortBy,page);
        getView().hideDialogLoading();
      }
    },sku));
  }

  void goToInviteFriend(){
    UserDetailResponse userDetail = getDataManager().getUserDetail();
    String referralCode = userDetail != null ?  userDetail.getReferralCode() : "";

    getView().showLoading();
    add(getInviteFriendDescription.execute(new SingleSubscriber<InviteFriendDescription>() {
      @Override
      public void onSuccess(InviteFriendDescription value) {
        getView().hideLoading();

        getView().goToInviteFriend(referralCode, userDetail.getSalutation(),
            userDetail.getFirstname(),userDetail.getLastname(),
            value.description,value.image);
      }

      @Override
      public void onError(Throwable error) {
        getView().hideLoading();
      }
    } ));
  }

  void initNotification(Context context , ChangeNotificationListener notificationListener){
    OneSignal.setSubscription(true);
    OneSignal
        .getCurrentOrNewInitBuilder()
        .setNotificationOpenedHandler(new SkyNotificationOpenHandler(context))
        .setNotificationReceivedHandler(new SkyNotificationReceivedHandler(App.getAppContext(),dataManager,notificationListener));


  }

  void checkLogin(){
    PreferenceUtils preferenceUtils = new PreferenceUtils(App.getAppContext());
    DataUtils dataUtils = new DataUtils(App.getAppContext());
    InternalStorageManager internalStorageManager = new InternalStorageManagerImpl(preferenceUtils,dataUtils);
    if(TextUtils.isEmpty(internalStorageManager.getUserAuthToken())){
      getView().forceLogin();
    }
  }
}
