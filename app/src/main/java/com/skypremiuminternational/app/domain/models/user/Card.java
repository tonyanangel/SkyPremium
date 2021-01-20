package com.skypremiuminternational.app.domain.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {


  @SerializedName("brand")
  @Expose
  private String brand;
  @SerializedName("checks")
  @Expose
  private Checks checks;
  @SerializedName("country")
  @Expose
  private String country;
  @SerializedName("exp_month")
  @Expose
  private int expMonth;
  @SerializedName("exp_year")
  @Expose
  private int expYear;
  @SerializedName("fingerprint")
  @Expose
  private String fingerprint;
  @SerializedName("funding")
  @Expose
  private String funding;
  @SerializedName("generated_from")
  @Expose
  private String generated_from;
  @SerializedName("last4")
  @Expose
  private String last4;
  @SerializedName("networks")
  @Expose
  private Networks networks;
  @SerializedName("three_d_secure_usage")
  @Expose
  private ThreeDSecureUsage threeDSecureUsage;
  @SerializedName("wallet")
  @Expose
  private String wallet;

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Checks getChecks() {
    return checks;
  }

  public void setChecks(Checks checks) {
    this.checks = checks;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public int getExpMonth() {
    return expMonth;
  }

  public void setExpMonth(int expMonth) {
    this.expMonth = expMonth;
  }

  public int getExpYear() {
    return expYear;
  }

  public void setExpYear(int expYear) {
    this.expYear = expYear;
  }

  public String getFingerprint() {
    return fingerprint;
  }

  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  public String getFunding() {
    return funding;
  }

  public void setFunding(String funding) {
    this.funding = funding;
  }

  public String getGenerated_from() {
    return generated_from;
  }

  public void setGenerated_from(String generated_from) {
    this.generated_from = generated_from;
  }

  public String getLast4() {
    return last4;
  }

  public void setLast4(String last4) {
    this.last4 = last4;
  }

  public Networks getNetworks() {
    return networks;
  }

  public void setNetworks(Networks networks) {
    this.networks = networks;
  }

  public ThreeDSecureUsage getThreeDSecureUsage() {
    return threeDSecureUsage;
  }

  public void setThreeDSecureUsage(ThreeDSecureUsage threeDSecureUsage) {
    this.threeDSecureUsage = threeDSecureUsage;
  }

  public String getWallet() {
    return wallet;
  }

  public void setWallet(String wallet) {
    this.wallet = wallet;
  }
}
