package com.skypremiuminternational.app.data.model.ean.availability;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Occupancy {

  @SerializedName("nightly")
  private List<List<NightlyItem>> nightly;

  @SerializedName("fees")
  private FeesItem fees;

  @SerializedName("totals")
  private Totals totals;

  public void setNightly(List<List<NightlyItem>> nightly) {
    this.nightly = nightly;
  }

  public List<List<NightlyItem>> getNightly() {
    return nightly;
  }

  public void setTotals(Totals totals) {
    this.totals = totals;
  }

  public Totals getTotals() {
    return totals;
  }

  public FeesItem getFees() {
    return fees;
  }

  public void setFees(FeesItem fees) {
    this.fees = fees;
  }
}