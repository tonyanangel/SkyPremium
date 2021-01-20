package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;
import com.onesignal.OSNotificationPayload;

import java.util.ArrayList;
import java.util.List;

public class SkyPayloadBuilder implements PayloadBuilder {

  @SerializedName("member_id")
  private String memberId;

  @SerializedName("notification_iD")
  private String notificationID;

  @SerializedName("title")
  private String title;

  @SerializedName("body")
  private String body;

  @SerializedName("subtitle")
  private String subtitle;

  @SerializedName("launch_url")
  private String launchURL;

  @SerializedName("addtional_data")
  private AdditionData addtionalData;

  @SerializedName("action_buttons")
  private List<SkyActionButton> actionButtons;

  @SerializedName("raw_pay_load")
  private String rawPayload;

  @Override
  public PayloadBuilder setMemberId(String memberId) {
    this.memberId = memberId;
    return this;
  }

  @Override
  public PayloadBuilder setNotificationID(String notificationID) {
    this.notificationID = notificationID;
    return this;
  }

  @Override
  public PayloadBuilder setTitle(String title) {

    this.title = title;
    return this;
  }

  @Override
  public PayloadBuilder setBody(String body) {
    this.body = body;
    return this;
  }

  @Override
  public PayloadBuilder setSubtitle(String subtitle) {
    this.subtitle = subtitle;
    return this;
  }

  @Override
  public PayloadBuilder setLaunchURL(String launchURL) {
    this.launchURL = launchURL;
    return this;
  }

  @Override
  public PayloadBuilder setAddtionalData(AdditionData addtionalData) {
    this.addtionalData = addtionalData;
    return this;
  }

  @Override
  public PayloadBuilder setActionButtons(List<OSNotificationPayload.ActionButton> actionButtons) {
    List<SkyActionButton> listSkyActionButton = new ArrayList<>();

    for(SkyActionButton actionButton :  listSkyActionButton){
      listSkyActionButton.add(new SkyActionButton(actionButton.id,actionButton.text,actionButton.icon));

    }


    this.actionButtons = listSkyActionButton;
    return this;
  }

  @Override
  public PayloadBuilder setRawPayload(String rawPayload) {
    this.rawPayload = rawPayload;
    return this;
  }

  public SkyPayload build() {
    return new SkyPayload(memberId, notificationID, title, body, subtitle,
        launchURL, addtionalData, actionButtons, rawPayload);
  }

}
