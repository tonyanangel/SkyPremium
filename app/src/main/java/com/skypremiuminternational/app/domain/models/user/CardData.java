package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardData {


  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("object")
  @Expose
  private String object;

  @SerializedName("billing_details")
  @Expose
  private BillingDetails billingDetails;

  @SerializedName("card")
  @Expose
  private Card card;

  @SerializedName("created")
  @Expose
  private long code;

  @SerializedName("customer")
  @Expose
  private String customer;

  @SerializedName("livemode")
  @Expose
  private boolean livemode;

  @SerializedName("metadata")
  @Expose
  private List<String> metadata;

  @SerializedName("type")
  @Expose
  private String type;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getObject() {
    return object;
  }

  public void setObject(String object) {
    this.object = object;
  }

  public BillingDetails getBillingDetails() {
    return billingDetails;
  }

  public void setBillingDetails(BillingDetails billingDetails) {
    this.billingDetails = billingDetails;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public boolean isLivemode() {
    return livemode;
  }

  public void setLivemode(boolean livemode) {
    this.livemode = livemode;
  }

  public List<String> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<String> metadata) {
    this.metadata = metadata;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
