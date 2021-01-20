package com.skypremiuminternational.app.push_notification;

import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.onesignal.OSDevice;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.notifications.NotificationActivity;
import com.skypremiuminternational.app.app.features.search.SearchActivity;
import com.skypremiuminternational.app.app.features.splash.SplashActivity;
import com.skypremiuminternational.app.app.features.travel.detail.TravelDetailActivity;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.app.utils.listener.MovePillarListener;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.data.network.RestAdapter;
import com.skypremiuminternational.app.data.network.request.NotificationRequest;
import com.skypremiuminternational.app.data.network.service.AuthService;
import com.skypremiuminternational.app.data.network.service.NotificationService;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.CreateCrmToken;
import com.skypremiuminternational.app.domain.interactor.notification.ReadNotifications;
import com.skypremiuminternational.app.domain.interactor.notification.SendNotification;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenRequest;
import com.skypremiuminternational.app.domain.models.crm.CrmTokenResponse;
import com.skypremiuminternational.app.domain.models.notification.NotificationUpdateRequest;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.UpdateDelItem;
import com.skypremiuminternational.app.domain.models.notification.UpdateReadItem;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.http.Query;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.skypremiuminternational.app.app.utils.CommonUtils.YYYY_MM_DD_HH_MM;
import static com.skypremiuminternational.app.app.utils.CommonUtils.getGMTZero;

public class SkyNotificationOpenHandler implements OneSignal.NotificationOpenedHandler {

  Context context;
  ChangeNotificationListener listener;



  private CompositeSubscription compositeSubscription = new CompositeSubscription();

 /* public SkyNotificationOpenHandler(Context context, MovePillarListener listener,
                                    ReadNotifications readNotifications, CreateCrmToken createCrmToken,
                                    SendNotification sendNotification) {
    this.context = context;
    this.listener = listener;
    this.readNotifications = readNotifications;
    this.sendNotification = sendNotification;
    this.createCrmToken = createCrmToken;
  }*/
  public SkyNotificationOpenHandler(Context context) {
    this.context = context;
  }

  @Override
  public void notificationOpened(OSNotificationOpenResult result) {


    //gotoNotification();



      /*
      // make as read to crm

      createCrmToken.execute(new SingleSubscriber<CrmTokenResponse>() {
       @Override
        public void onSuccess(CrmTokenResponse value) {
       Log.d("CLICK_NOTIFICATION", "Success 1");
       NotificationUpdateRequest request2 = new NotificationUpdateRequest();
       List<UpdateReadItem> readItems = new ArrayList<>();
       UpdateReadItem item = new UpdateReadItem();
       String stringDate = getGMTZero(YYYY_MM_DD_HH_MM);
       item.setReadTime(stringDate);
       item.setId(result.notification.payload.notificationID);
       readItems.add(item);
       request2.setUpdateItem(readItems);

       PreferenceUtils preferenceUtils = new PreferenceUtils(App.getAppContext());
       DataUtils dataUtils = new DataUtils(App.getAppContext());
       InternalStorageManager internalStorageManager = new InternalStorageManagerImpl(preferenceUtils, dataUtils);
       NotificationRequest re = new NotificationRequest();
       NotificationItem clickNotification = new NotificationItem();
       List<NotificationItem> localList = new ArrayList<>();
       try {
         localList = internalStorageManager.getListNotification();
       } catch (FileNotFoundException e) {
         e.printStackTrace();
       }
       if(localList!=null){
         for (NotificationItem NotificationItem : localList) {
           if (NotificationItem.getNotificationId().equals(result.notification.payload.notificationID)) {
             clickNotification = NotificationItem;
             NotificationItem.setRead(true);
             internalStorageManager.saveListNotification(localList);
             break;
           }
         }
       }else {
         return;
       }
       List<NotificationItem> sendList = new ArrayList<>();
       sendList.add(clickNotification);
       re.setNotifications(sendList);
       re.setDeviceId(OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
       re.setMemberId(internalStorageManager.getUserDetail().getMemberNumber());
        SendNotification.Params params = new SendNotification.Params(value.getAccessToken(),re);
       sendNotification.execute(new SingleSubscriber<ResponseBody>() {
         @Override
         public void onSuccess(ResponseBody value) {
           Log.d("CLICK_NOTIFICATION", "Success 2");

         }

         @Override
         public void onError(Throwable error) {
           Log.d("CLICK_NOTIFICATION", "F 2" + error.getMessage());

         }
       },params);

     }
     @Override
     public void onError(Throwable error) {
       Log.d("CLICK_NOTIFICATION", "F 1"+ error.getMessage());

     }
   });*/

  }

  void makeAsReadOne(OSNotificationOpenResult result){
    InternalStorageManager internalStorageManager =
        new InternalStorageManagerImpl(new PreferenceUtils(context),new DataUtils(context));

    List<NotificationItem> items = null;

    try {
      items = internalStorageManager.getListNotification();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    if(items==null){
      items = new ArrayList<>();
    }
    for(NotificationItem item  : items){
      if(item.getNotificationId().equalsIgnoreCase(result.notification.payload.notificationID)){
        item.setIsRead(true);
        break;
      }
    }

    internalStorageManager.saveListNotification(items);
  }

  void gotoNotification(){
    if(App.isInApp){
      NotificationActivity.startMe(App.getAppContext(),"notification");
    }else {
      InternalStorageManager internalStorageManager =
          new InternalStorageManagerImpl(new PreferenceUtils(context),new DataUtils(context));
      internalStorageManager.saveOpenNotification(true);
    }
  }

 /* void dynamicNotification(OSNotificationOpenResult result){
    String launchUrl = result.notification.payload.launchURL.trim();

    String host = "";
    String query = "";
    Map<String,String> mapQuery = new HashMap<>();




    if(launchUrl!=null){
      URI uri = null;
      try {
        uri =  new URI(launchUrl);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }

      try{
        host = uri.getHost();
      }catch (NullPointerException ex){

      }

      try{
        query = uri.getQuery();
        String[] arrayQuery = query.split("&");
        for(String item :  arrayQuery){
          String[] arrayMap = item.split("=");
          if(arrayMap.length>=2){
            mapQuery.put(arrayMap[0],arrayMap[1]);
          }
        }
      }catch (NullPointerException ex){

      }


      if (!TextUtils.isEmpty(host)) {
        if (host.equalsIgnoreCase("estore")) {
          if (mapQuery.get("sku")!=null&&!TextUtils.isEmpty(mapQuery.get("sku"))) {
            EstoreDetailActivity.startMe(context, "notification", mapQuery.get("sku"));
          } else if (mapQuery.get("filter")!=null&&!TextUtils.isEmpty(mapQuery.get("filter"))) {
            EstoreActivity.startMe(context, mapQuery.get("filter"));
          } else {
            EstoreActivity.startMe(context);
          }
        }else if(host.equalsIgnoreCase("search")){
          if (mapQuery.get("keyword")!=null&&!TextUtils.isEmpty(mapQuery.get("keyword"))) {
            SearchActivity.startMeWithKey(context, mapQuery.get("keyword"));
          }else {
            SearchActivity.startMe(context);
          }
        } else {
          if (mapQuery.get("sku")!=null && !TextUtils.isEmpty(mapQuery.get("sku"))) {
            TravelDetailActivity.startMe(context, "notification", mapQuery.get("sku"));
          } else if ((mapQuery.get("filter")!=null && !TextUtils.isEmpty(mapQuery.get("filter")))
              ||(mapQuery.get("sortby")!=null && !TextUtils.isEmpty(mapQuery.get("sortby")))) {

            LandingActivity.startMe(context, "notification", host, mapQuery.get("filter"),mapQuery.get(("sortby")));
            listener.movePillar(host,mapQuery.get("filter"),"notification",mapQuery.get(("sortby")));

          }
        }
      }
    }
  }*/



}
