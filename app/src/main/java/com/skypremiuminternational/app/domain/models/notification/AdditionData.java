package com.skypremiuminternational.app.domain.models.notification;

import com.google.gson.annotations.SerializedName;

public class AdditionData {


  public static final  String MARKETING     = "marketing_alerts";
  public static final  String EVENTS        = "event_invites_and_update";
  public static final  String TRAVEL        = "travel_deals";
  public static final  String WINE_DINE     = "wine_and_dine_deals";
  public static final  String SHOPPING      = "shopping_deals";
  public static final  String ESTORE        = "estore_deals";
  public static final  String WELLNESS      = "wellness_deals";
  public static final  String FORCE         = "force_notification";
  /*
  {
			"marketing_alerts" : "",
			"event_invites_and_update" : "",
  		"travel_deals" : "",
			"wine_and_dine_deals" : "",
			"shopping_deals" : "",
			"estore_deals" : "",
			"wellness_deals" : "",
			"status_notification" : 0
	}
	*/

 /* @SerializedName("marketing_alerts")
  boolean marketingAlerts = false;

  @SerializedName("event_invites_and_update")
  boolean eventInvitesAndUpdate = false;

  @SerializedName("travel_deals")
  boolean travelDeals = false;

  @SerializedName("wine_and_dine_deals")
  boolean wineAndDineDeals = false;

  @SerializedName("estore_deals")
  boolean estoreDeals = false;

  @SerializedName("wellness_deals")
  boolean wellnessDeals = false;

  @SerializedName("type_of_notification")
  String typeOfNotification ;

  @SerializedName("shopping_deals")
  boolean shoppingDeals = false;

  @SerializedName("force_notification")
  boolean forceNotification = false;*/

  @SerializedName("sku")
  String sku = "";

  @SerializedName("redirect")
  String redirect = "";

  @SerializedName("filter")
  String filter = "";

  @SerializedName("keyword")
  String keyword = "";

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }

  /*public String getTypeOfNotification() {
    return typeOfNotification;
  }

  public void setTypeOfNotification(String typeOfNotification) {
    this.typeOfNotification = typeOfNotification;
  }

  public boolean isShoppingDeals() {
    return shoppingDeals;
  }

  public void setShoppingDeals(boolean shoppingDeals) {
    this.shoppingDeals = shoppingDeals;
  }

  public boolean isMarketingAlerts() {
    return marketingAlerts;
  }

  public void setMarketingAlerts(boolean marketingAlerts) {
    this.marketingAlerts = marketingAlerts;
  }

  public boolean isEventInvitesAndUpdate() {
    return eventInvitesAndUpdate;
  }

  public void setEventInvitesAndUpdate(boolean eventInvitesAndUpdate) {
    this.eventInvitesAndUpdate = eventInvitesAndUpdate;
  }

  public boolean isTravelDeals() {
    return travelDeals;
  }

  public void setTravelDeals(boolean travelDeals) {
    this.travelDeals = travelDeals;
  }

  public boolean isWineAndDineDeals() {
    return wineAndDineDeals;
  }

  public void setWineAndDineDeals(boolean wineAndDineDeals) {
    this.wineAndDineDeals = wineAndDineDeals;
  }

  public boolean isEstoreDeals() {
    return estoreDeals;
  }

  public void setEstoreDeals(boolean estoreDeals) {
    this.estoreDeals = estoreDeals;
  }

  public boolean isWellnessDeals() {
    return wellnessDeals;
  }

  public void setWellnessDeals(boolean wellnessDeals) {
    this.wellnessDeals = wellnessDeals;
  }

  public boolean isForceNotification() {
    return forceNotification;
  }

  public void setForceNotification(boolean forceNotification) {
    this.forceNotification = forceNotification;
  }*/
}
