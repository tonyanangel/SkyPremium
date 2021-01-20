package com.skypremiuminternational.app.app.utils;

import com.skypremiuminternational.app.domain.models.notification.NotificationItem;
import com.skypremiuminternational.app.domain.models.notification.SkyPayload;

import java.util.ArrayList;
import java.util.List;

public class FlatUtils {

  public static List<NotificationItem> flatPayloadToNotification(List<SkyPayload> payloadList){
    List<NotificationItem> notificationList = new ArrayList<>();
    for(SkyPayload payload : payloadList ){
       NotificationItem notification  = new NotificationItem();
       notification.setAdditionalData(payload.getAddtionalData());
       notification.setLaunchUrl(payload.getLaunchURL());
       notification.setBody(payload.getBody());
       notification.setNotificationId(payload.getNotificationID());
       notification.setReceiveTime(payload.getReceiveDate());
       notification.setTitle(payload.getTitle());
    }




    return notificationList;
  }

}
