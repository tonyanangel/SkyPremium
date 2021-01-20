package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class SettingData {

  @SerializedName("estore")
  boolean estore;
  @SerializedName("marketing")
  boolean marketing;
  @SerializedName("event")
  boolean event;
  @SerializedName("travel")
  boolean travel;
  @SerializedName("shopping")
  boolean shopping;
  @SerializedName("wellness")
  boolean wellness;
  @SerializedName("winedine")
  boolean winedine;


  public boolean isEstore() {
    return estore;
  }

  public void setEstore(boolean estore) {
    this.estore = estore;
  }

  public boolean isMarketing() {
    return marketing;
  }

  public void setMarketing(boolean marketing) {
    this.marketing = marketing;
  }

  public boolean isEvent() {
    return event;
  }

  public void setEvent(boolean event) {
    this.event = event;
  }

  public boolean isTravel() {
    return travel;
  }

  public void setTravel(boolean travel) {
    this.travel = travel;
  }

  public boolean isShopping() {
    return shopping;
  }

  public void setShopping(boolean shopping) {
    this.shopping = shopping;
  }

  public boolean isWellness() {
    return wellness;
  }

  public void setWellness(boolean wellness) {
    this.wellness = wellness;
  }

  public boolean isWinedine() {
    return winedine;
  }

  public void setWinedine(boolean winedine) {
    this.winedine = winedine;
  }

  public AddtionalSettingData toAdditionData(){

    AddtionalSettingData additionData = new AddtionalSettingData();
    additionData.setEstoreDeals(this.estore);
    additionData.setEventInvitesAndUpdate(this.event);
    additionData.setMarketingAlerts(this.marketing);
    additionData.setShoppingDeals(this.shopping);
    additionData.setTravelDeals(this.travel);
    additionData.setWellnessDeals(this.wellness);
    additionData.setWineAndDineDeals(this.winedine);



    return additionData;
  }
}
