package com.skypremiuminternational.app.domain.models.notification;


import com.onesignal.OSNotificationPayload;

import java.util.List;

public interface PayloadBuilder {

  PayloadBuilder setMemberId(String memberId);

  PayloadBuilder setNotificationID(String notificationID);

  PayloadBuilder setTitle(String title);

  PayloadBuilder setBody(String body);

  PayloadBuilder setSubtitle(String subtitle);

  PayloadBuilder setLaunchURL(String launchURL);

  PayloadBuilder setAddtionalData(AdditionData addtionalData);

  PayloadBuilder setActionButtons(List<OSNotificationPayload.ActionButton> actionButtons);

  PayloadBuilder setRawPayload(String rawPayload);

  SkyPayload build();
}
