package com.skypremiuminternational.app.push_notification;

import android.content.Context;

import com.google.gson.Gson;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.models.notification.AdditionData;
import com.skypremiuminternational.app.domain.models.notification.NotificationItem;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SkyNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {

  Context context;
  private AdditionData additionData;
  DataManager dataManager;
  ChangeNotificationListener changeNotificationListener;

  public  SkyNotificationReceivedHandler(Context context,
                                        DataManager dataManager, ChangeNotificationListener listener){
    this.context = context;
    this.dataManager = dataManager;
    this.changeNotificationListener = listener;
  }



  @Override
  public void notificationReceived(OSNotification notification) {
    notification.shown = false;
    if(changeNotificationListener!=null){
      changeNotificationListener.renderNumberNotification();
      changeNotificationListener.renderListNotification();
    }
     // notification.shown = false;

    //setupData(notification);

    //Disable old logic
    /*if(isDiscardNotification(AdditionData)){
      OneSignal.cancelNotification(notification.androidNotificationId);
    }*/
  }

  /*private boolean isDiscardNotification(AdditionData AdditionData) {
    if(AdditionData.isForceNotification()){
      return false;
    }else if(AdditionData.isEstoreDeals()
        &&(AdditionData.isEstoreDeals() == preferenceUtils.get(Constants.ESTORE_DEALS,false))){
      return false;
    }else if(AdditionData.isEventInvitesAndUpdate()
        &&(AdditionData.isEventInvitesAndUpdate() == preferenceUtils.get(Constants.EVENT_INVITES_AND_UP,false))){
      return false;
    }else if(AdditionData.isMarketingAlerts()
        &&(AdditionData.isMarketingAlerts() == preferenceUtils.get(Constants.MARKETING_ALERT,false))){
      return false;
    }else if(AdditionData.isTravelDeals()
        &&(AdditionData.isTravelDeals() == preferenceUtils.get(Constants.TRAVEL_DEALS,false))){
      return false;
    }else if(AdditionData.isWellnessDeals()
        &&(AdditionData.isWellnessDeals() == preferenceUtils.get(Constants.WELLNESS_DEALS,false))){
      return false;
    }else if(AdditionData.isWineAndDineDeals()
        &&(AdditionData.isWineAndDineDeals() == preferenceUtils.get(Constants.WINE_DINE_DEALS,false))){
      return false;
    }else if(AdditionData.isShoppingDeals()
        &&(AdditionData.isShoppingDeals() == preferenceUtils.get(Constants.SHOPPING_DEALS,false))){
      return false;
    }
    return true;
  }*/

  private void setupData(OSNotification notification) {
    additionData = new AdditionData();
    Gson gson = new Gson();
    NotificationItem NotificationItem = new NotificationItem();
    List<NotificationItem>  listNotification = null;
    try {
      listNotification = dataManager.getListNotification();
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

    NotificationItem.setBody(notification.payload.body);
    NotificationItem.setAdditionalData(additionData);
    NotificationItem.setTitle(notification.payload.title);
    NotificationItem.setLaunchUrl(notification.payload.launchURL);
    NotificationItem.setNotificationId(notification.payload.notificationID);
    NotificationItem.setReceiveTime(stringDate);

    if(listNotification==null){
      listNotification = new ArrayList<>();
    }
    listNotification.add(NotificationItem);

    dataManager.saveListNotification(listNotification);

    changeNotificationListener.renderNumberNotification();

  }



}
