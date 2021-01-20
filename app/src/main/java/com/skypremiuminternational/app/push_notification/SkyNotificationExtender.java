package com.skypremiuminternational.app.push_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OneSignal;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.notifications.NotificationActivity;
import com.skypremiuminternational.app.app.features.splash.SplashActivity;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.DataUtils;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.domain.models.notification.AdditionData;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SkyNotificationExtender extends NotificationExtenderService {

  AdditionData additionData;
  @Override
  public boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
    PreferenceUtils preferenceUtils = new PreferenceUtils(App.getAppContext());
    DataUtils aDataUtils = new DataUtils(App.getAppContext());
    InternalStorageManager internalStorageManager = new InternalStorageManagerImpl(preferenceUtils,aDataUtils);
    if (!ObjectUtil.isNull(notification)){
      setupData(notification,internalStorageManager);
    }


    return true;
  }

  private void setupData(OSNotificationReceivedResult notification,InternalStorageManager internalStorageManager) {
    additionData = new AdditionData();
    Gson gson = new Gson();
    NotificationItem notificationItem = new NotificationItem();
    List<NotificationItem>  listNotification = null;
    try {
      listNotification = internalStorageManager.getListNotification();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


    try{
      additionData = gson.fromJson(notification.payload.additionalData.toString(),AdditionData.class);

    }catch (NullPointerException ex){

    }
    Date nowDate = Calendar.getInstance().getTime();
    //2020-10-16 15:53
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String stringDate = dateFormat.format(nowDate);
    notificationItem.setMemberNumber(internalStorageManager.getUserDetail().getMemberNumber());
    notificationItem.setBody(notification.payload.body);
    notificationItem.setAdditionalData(additionData);
    notificationItem.setTitle(notification.payload.title);
    notificationItem.setLaunchUrl(notification.payload.launchURL);
    notificationItem.setNotificationId(notification.payload.notificationID);
    notificationItem.setReceiveTime(stringDate);

    if(listNotification==null){
      listNotification = new ArrayList<>();
    }

    boolean found =false;
    if(listNotification.size()>0){
      for(NotificationItem item : listNotification){
        if(item.getNotificationId().equalsIgnoreCase(notificationItem.getNotificationId())){
          found = true;
          break;
        }
      }
    }
    if(!found){
      listNotification.add(0,notificationItem);
      internalStorageManager.saveListNotification(listNotification);
    }

    Log.d("OS_Subscribed", "state : "+OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getSubscribed());
    if(!OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getSubscribed()){
      return;
    }

    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


    Intent intent;

    intent = NotificationActivity.startMePush(getApplicationContext(),"notification");


    if (intent != null){
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel mChannel = new NotificationChannel("sky_channel_01", "sky_channel",NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(mChannel);
      }

      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "sky_channel_01");
      notificationBuilder.setSmallIcon(R.drawable.ic_logo_app);
      notificationBuilder.setContentTitle(notification.payload.title);
      notificationBuilder.setContentText(notification.payload.body);
      notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
      notificationBuilder.setContentIntent(pendingIntent);
      int selectID = 1;

      while (isExistNotifiId(selectID , notificationManager)){
        selectID =  CommonUtils.getRandomId(0,999999999);
      }


      notificationManager.notify(notification.payload.body, selectID, notificationBuilder.build());
    }

  }

  boolean isExistNotifiId(int selectID, NotificationManager notificationManager ){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      StatusBarNotification[] statusBarNotifications =  notificationManager.getActiveNotifications();
      for(StatusBarNotification item : statusBarNotifications){
        if(selectID == item.getId()){
          return true;
        }
      }
    }
    return false;
  }



}
