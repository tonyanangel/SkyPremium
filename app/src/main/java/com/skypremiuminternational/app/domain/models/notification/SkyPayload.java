package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;
import com.onesignal.OSNotificationPayload;

import java.util.List;

public class SkyPayload {

  /*
  0.  member_id
  1.  notificationID
  2.  title
  3.  body
  4.  subtitle
  5.  launchURL
  6.  addtionalData
  7.  actionBittons
  8.  rawPayload
  */

  /**
   * @param memberId
   * @param notificationId
   * @param title
   * @param body
   * @param subtitle
   * @param launchURL
   * @param addtionalData
   * @param actionButtons
   * @param rawPayload
   */
  public SkyPayload(String memberId, String notificationId, String title, String body, String subtitle, String launchURL, AdditionData addtionalData, List<SkyActionButton> actionButtons, String rawPayload) {
    this.memberId = memberId;
    this.notificationId = notificationId;
    this.title = title;
    this.body = body;
    this.subtitle = subtitle;
    this.launchURL = launchURL;
    this.addtionalData = addtionalData;
    this.actionButtons = actionButtons;
    this.rawPayload = rawPayload;
  }

  @SerializedName("member_id")
  private String memberId = "";

  @SerializedName("notification_id")
  private String notificationId = "";

  @SerializedName("title")
  private String title = "";

  @SerializedName("body")
  private String body = "";

  @SerializedName("subtitle")
  private String subtitle = "";

  @SerializedName("launch_url")
  private String launchURL = "";

  @SerializedName("addtional_data")
  private AdditionData addtionalData;

  @SerializedName("action_buttons")
  private List<SkyActionButton> actionButtons;

  @SerializedName("raw_pay_load")
  private String rawPayload = "";

  @SerializedName("raw_pay_load")
  private String receiveDate;


  private boolean isChecked;


  public String getReceiveDate() {
    return receiveDate;
  }

  public void setReceiveDate(String receiveDate) {
    this.receiveDate = receiveDate;
  }

  public boolean isChecked(){
    return isChecked;
  }

  public void setChecked(boolean isChecked){
    this.isChecked = isChecked;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getNotificationID() {
    return notificationId;
  }

  public void setNotificationID(String notificationId) {
    this.notificationId = notificationId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getLaunchURL() {
    return launchURL;
  }

  public void setLaunchURL(String launchURL) {
    this.launchURL = launchURL;
  }

  public AdditionData getAddtionalData() {
    return addtionalData;
  }

  public void setAddtionalData(AdditionData addtionalData) {
    this.addtionalData = addtionalData;
  }

  public List<SkyActionButton> getActionButtons() {
    return actionButtons;
  }

  public void setActionButtons(List<SkyActionButton> actionButtons) {
    this.actionButtons = actionButtons;
  }

  public String getRawPayload() {
    return rawPayload;
  }

  public void setRawPayload(String rawPayload) {
    this.rawPayload = rawPayload;
  }
}
